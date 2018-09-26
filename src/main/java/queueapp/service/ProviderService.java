package queueapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueapp.domain.Provider;
import queueapp.repository.ProviderRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;

    public String createProvider(Provider provider) {
        return providerRepository.save(provider).getProviderId();
    }

    public Optional<Provider> readProvider(String providerId) {
        return providerRepository.findById(providerId);
    }

    public boolean updateProvider(String providerId, Provider provider) {
        if (providerRepository.existsById(providerId)) {
            providerRepository.save(provider);
            return true;
        }
        return false;
    }

    public boolean deleteProvider(String providerId) {
        if (providerRepository.existsById(providerId)) {
            providerRepository.deleteById(providerId);
            return true;
        }
        return false;
    }
}
