package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.CredentialRangeResponse;
import com.gabrielgua.refeitorio.domain.model.CredentialRange;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CredentialRangeMapper {

    public CredentialRangeResponse toResponse(CredentialRange credentialRange) {
        return CredentialRangeResponse.builder()
                .id(credentialRange.getId())
                .name(credentialRange.getName())
                .min(credentialRange.getMin())
                .max(credentialRange.getMax())
                .paymentType(credentialRange.getPaymentType())
                .createdAt(credentialRange.getCreatedAt())
                .build();
    }

    public CredentialRangeResponse toClientResponse(CredentialRange credentialRange) {
        return CredentialRangeResponse.builder()
                .id(credentialRange.getId())
                .name(credentialRange.getName())
                .paymentType(credentialRange.getPaymentType())
                .build();
    }

    public List<CredentialRangeResponse> toResponseList(List<CredentialRange> credentialRanges) {
        return credentialRanges.stream()
                .map(this::toResponse)
                .toList();
    }

    public List<CredentialRangeResponse> toCollectionResponse(List<CredentialRange> credentialRanges, Function<CredentialRange, CredentialRangeResponse> mapper) {
        return credentialRanges.stream()
                .map(mapper)
                .toList();
    }
}