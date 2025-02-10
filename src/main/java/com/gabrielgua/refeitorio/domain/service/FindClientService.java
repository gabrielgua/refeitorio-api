package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.api.exception.BusinessException;
import com.gabrielgua.refeitorio.api.model.ConsultClientResponse;
import com.gabrielgua.refeitorio.domain.exception.UserNotFoundException;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindClientService {


    private final RestTemplate restTemplate;
    private final ClientRepository repository;
    private static final String BENNER_API_ENDPOINT = "http://172.16.1.92:8080/usuarios/{credential}";

    @Transactional(readOnly = true)
    public Client findByCredential(String credential) {
        return repository
                .findByCredential(credential)
                .orElse(consultAndSaveFromBenner(credential));
    }

    private Client consultAndSaveFromBenner(String credential) {
        try {
            ResponseEntity<ConsultClientResponse> response = restTemplate.exchange(
                    BENNER_API_ENDPOINT,
                    HttpMethod.GET,
                    null,
                    ConsultClientResponse.class,
                    credential
            );

            var clientResponse = Objects.requireNonNull(response.getBody());

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.debug(String.valueOf(clientResponse));
                throw new BusinessException("Non 200 response from Benner API");
            }

            return repository.save(fromConsultClientResponse(clientResponse));

        } catch (HttpClientErrorException.NotFound ex) {
            log.warn("User with credential {} not found in external API", credential);
            throw new UserNotFoundException("User with credential " + credential + " not found in external API");
        } catch (HttpClientErrorException ex) {
            log.error("Client error while fetching user: {}", ex.getResponseBodyAsString());
            throw new BusinessException("Client error while fetching user: " + ex.getResponseBodyAsString());
        } catch (ResourceAccessException ex) {
            log.error("Timeout or network issue while fetching user: {}", ex.getMessage());
            throw new BusinessException("Timeout or network issue while fetching user: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error while fetching user", ex);
            throw new BusinessException("Unexpected error while fetching user: " + ex.getMessage());
        }
    }

    private Client fromConsultClientResponse(ConsultClientResponse consultClientResponse) {
        var client = new Client();
        client.setCredential(consultClientResponse.getCredential());
        client.setName(consultClientResponse.getName());
        client.setSalary(consultClientResponse.getSalary());
        return client;
    }
}