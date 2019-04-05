package ar.com.vendepor.vendepor48.service.impl;

import ar.com.vendepor.vendepor48.domain.Client;
import ar.com.vendepor.vendepor48.repository.ClientRepository;
import ar.com.vendepor.vendepor48.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean existsByUsername(String username) {
        return clientRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void saveAll(List<Client> clients) {
        clientRepository.saveAll(clients);
    }

}
