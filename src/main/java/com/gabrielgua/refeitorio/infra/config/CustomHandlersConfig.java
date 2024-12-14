package com.gabrielgua.refeitorio.infra.config;

import ch.qos.logback.classic.pattern.MessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gabrielgua.refeitorio.api.exception.RestAccessDeniedHandler;
import com.gabrielgua.refeitorio.api.exception.RestAuthenticationEntryPointHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;