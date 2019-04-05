package ar.com.vendepor.vendepor48.repository;

import ar.com.vendepor.vendepor48.domain.Client;

import java.util.Optional;

public interface ClientRepository extends UserRepository<Client> {

    @Override
    Optional<Client> findByEmail(String email);

    @Override
    Optional<Client> findByUsernameOrEmail(String username, String email);

}
