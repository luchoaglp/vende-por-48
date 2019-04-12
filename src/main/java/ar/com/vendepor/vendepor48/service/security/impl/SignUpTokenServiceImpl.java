package ar.com.vendepor.vendepor48.service.security.impl;

import ar.com.vendepor.vendepor48.domain.security.SignUpToken;
import ar.com.vendepor.vendepor48.repository.SignUpTokenRepository;
import ar.com.vendepor.vendepor48.service.security.SignUpTokenService;
import org.springframework.stereotype.Service;

@Service
public class SignUpTokenServiceImpl implements SignUpTokenService {

    private final SignUpTokenRepository signUpTokenRepository;

    public SignUpTokenServiceImpl(SignUpTokenRepository signUpTokenRepository) {
        this.signUpTokenRepository = signUpTokenRepository;
    }

    @Override
    public SignUpToken findByToken(String token) {
        return signUpTokenRepository.findByToken(token).orElse(null);
    }

    @Override
    public SignUpToken save(SignUpToken signUpToken) {
        return signUpTokenRepository.save(signUpToken);
    }

    @Override
    public SignUpToken findById(Long id) {
        return signUpTokenRepository.findById(id).orElse(null);
    }
}
