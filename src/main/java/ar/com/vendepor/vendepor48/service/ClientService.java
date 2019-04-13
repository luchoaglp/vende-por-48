package ar.com.vendepor.vendepor48.service;

import ar.com.vendepor.vendepor48.domain.Client;

import java.util.List;

public interface ClientService {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    Client save(Client client);
    void saveAll(List<Client> clients);

    Client getById(Long id);
}
