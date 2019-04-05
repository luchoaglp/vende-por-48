package ar.com.vendepor.vendepor48.service;

import ar.com.vendepor.vendepor48.domain.Client;

public interface ClientService {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    Client save(Client client);

}
