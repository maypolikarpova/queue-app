package queueapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueapp.domain.Request;
import queueapp.repository.RequestRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    public String createRequest(Request request) {
        return requestRepository.save(request).getRequestId();
    }

    public Optional<Request> readRequest(String requestId) {
        return requestRepository.findById(requestId);
    }

    public boolean updateRequest(String requestId, Request request) {
        if (requestRepository.existsById(requestId)) {
            requestRepository.save(request);
            return true;
        }
        return false;
    }

    public boolean deleteRequest(String requestId) {
        if (requestRepository.existsById(requestId)) {
            requestRepository.deleteById(requestId);
            return true;
        }
        return false;
    }
}
