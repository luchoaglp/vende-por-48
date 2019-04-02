package ar.com.vendepor.vendepor48.repository;


import ar.com.vendepor.vendepor48.domain.security.SignUpToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SignUpTokenRepository extends CrudRepository<SignUpToken, Long> {

    Optional<SignUpToken> findByToken(String token);

}
