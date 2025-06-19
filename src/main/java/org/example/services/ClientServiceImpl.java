package org.example.services;

import org.example.entity.Client;
import org.example.repository.ClientRepository;
import org.example.services.impl.ClientService;

import java.util.List;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository = new ClientRepository();

    @Override
    public Client findClient(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void updateClient(Client client) {
        clientRepository.update(client);
    }

    @Override
    public void removeClient(Client client) {
        clientRepository.delete(client);
    }

    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client findClientByPhone(String phone) {
        return clientRepository.findByPhoneNumber(phone).orElseThrow(() -> new IllegalArgumentException("Клиент не найден"));
    }
}
