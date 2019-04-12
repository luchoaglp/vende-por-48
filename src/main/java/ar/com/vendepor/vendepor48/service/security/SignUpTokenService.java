package ar.com.vendepor.vendepor48.service.security;

import ar.com.vendepor.vendepor48.domain.security.SignUpToken;


public interface SignUpTokenService {

    SignUpToken findById(Long id);
    SignUpToken findByToken(String token);
    SignUpToken save(SignUpToken signUpToken);
}
