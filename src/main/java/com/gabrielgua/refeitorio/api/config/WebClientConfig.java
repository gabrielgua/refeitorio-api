package com.gabrielgua.refeitorio.api.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    @Value("${benner.api.endpoint}")
    private String bennerApiEndpoint;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(bennerApiEndpoint)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                        .responseTimeout(Duration.ofSeconds(5))
                        .doOnConnected(conn ->
                                conn.addHandlerLast(new ReadTimeoutHandler(5, TimeUnit.SECONDS))
                                        .addHandlerLast(new WriteTimeoutHandler(5, TimeUnit.SECONDS))
                        )))
                .filter(retryFilter())
                .build();
    }

    private ExchangeFilterFunction retryFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            if (response.statusCode().is5xxServerError()) {
                return response.createException()
                        .flatMap(Mono::error)
                        .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))
                                .filter(ex -> ex instanceof WebClientResponseException.ServiceUnavailable))
                        .thenReturn(response);
            }
            return Mono.just(response);
        });
    }
}