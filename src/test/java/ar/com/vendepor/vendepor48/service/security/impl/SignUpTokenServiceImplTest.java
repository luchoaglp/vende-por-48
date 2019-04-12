package ar.com.vendepor.vendepor48.service.security.impl;

import ar.com.vendepor.vendepor48.domain.security.SignUpToken;
import ar.com.vendepor.vendepor48.repository.SignUpTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class SignUpTokenServiceImplTest {

    SignUpTokenServiceImpl signUpTokenService;

    @Mock
    SignUpTokenRepository signUpTokenRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        signUpTokenService = new SignUpTokenServiceImpl(signUpTokenRepository);
    }

    @Test
    void findByToken() {
        SignUpToken signUpToken = new SignUpToken();
        signUpToken.setToken("token");

        Optional<SignUpToken> signUpTokenOptional = Optional.of(signUpToken);

        when(signUpTokenRepository.findByToken(anyString())).thenReturn(signUpTokenOptional);

        SignUpToken signUpTokenReturned = signUpTokenService.findByToken("token");

        assertEquals(signUpTokenOptional.get().getToken(), signUpTokenReturned.getToken());
    }

    @Test
    void save() {
    }

    @Test
    void findById() {

    }
}