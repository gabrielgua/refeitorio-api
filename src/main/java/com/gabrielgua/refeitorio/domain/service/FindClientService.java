package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.api.exception.BusinessException;
import com.gabrielgua.refeitorio.api.mapper.ClientMapper;
import com.gabrielgua.refeitorio.api.model.ConsultClientResponse;
import com.gabrielgua.refeitorio.domain.exception.UserNotFoundException;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FindClientService {

    private static final String BENNER_API_ENDPOINT = "/{credential}";

    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final ClientService clientService;
    private final WebClient webClient;

    @Transactional(noRollbackFor = BusinessException.class)
    public Client findByCredential(String credential) {
        return repository
                .findByCredential(credential)
                .orElseGet(() -> consultAndSaveFromBenner(credential));
    }

    private Client consultAndSaveFromBenner(String credential) {
        return webClient.get()
                .uri(BENNER_API_ENDPOINT, credential)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new UserNotFoundException(credential)))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new BusinessException("Server error: " + response.statusCode()))
                )
                .bodyToMono(ConsultClientResponse.class)
                .switchIfEmpty(Mono.error(new UserNotFoundException(credential))) // Handles empty responses
                .map(mapper::toEntity) // maps the response to Client class
                .map(clientService::save) // saves the Client class and maps it to its response
                .block();
    }
}