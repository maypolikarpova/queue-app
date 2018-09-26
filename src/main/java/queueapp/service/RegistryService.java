package queueapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueapp.domain.Registry;
import queueapp.repository.RegistryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistryService {

    private final RegistryRepository registryRepository;

    public String createRegistry(Registry registry) {
        return registryRepository.save(registry).getRegistryId();
    }

    public Optional<Registry> readRegistry(String registryId) {
        return registryRepository.findById(registryId);
    }

    public boolean updateRegistry(String registryId, Registry registry) {
        if (registryRepository.existsById(registryId)) {
            registryRepository.save(registry);
            return true;
        }
        return false;
    }

    public boolean deleteRegistry(String registryId) {
        if (registryRepository.existsById(registryId)) {
            registryRepository.deleteById(registryId);
            return true;
        }
        return false;
    }
}
