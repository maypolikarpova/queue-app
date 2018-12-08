package queueapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.user.UserResponse;
import queueapp.repository.AppointmentRepository;
import queueapp.repository.QueueRepository;
import queueapp.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;
    private final QueueRepository queueRepository;
    private final AppointmentRepository appointmentRepository;

    public List<QueueResponse> searchQueueByQueryAndLocation(String query, String location) {
        return null;
    }

    public List<UserResponse> searchProviderByNameAndLocation(String name, String location) {
        return null;
    }

}
