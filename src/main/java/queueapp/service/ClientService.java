package queueapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueapp.domain.Client;
import queueapp.repository.ClientRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public String createClient(Client client) {
        return clientRepository.save(client).getClientId();
    }

    public Optional<Client> readClient(String clientId) {
        return clientRepository.findById(clientId);
    }

    public boolean updateClient(String clientId, Client client) {
        if (clientRepository.existsById(clientId)) {
            clientRepository.save(client);
            return true;
        }
        return false;
    }

    public boolean deleteClient(String clientId) {
        if (clientRepository.existsById(clientId)) {
            clientRepository.deleteById(clientId);
            return true;
        }
        return false;
    }
}
