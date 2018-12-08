package queueapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueapp.domain.queue.Queue;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.user.User;
import queueapp.domain.user.UserResponse;
import queueapp.repository.QueueRepository;
import queueapp.repository.UserRepository;
import queueapp.service.mapper.QueueMapper;
import queueapp.service.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;
    private final QueueRepository queueRepository;
    private final QueueMapper queueMapper;
    private final UserMapper userMapper;

    public List<QueueResponse> searchQueueByQueryAndLocation(String query, String location) {
        List<Queue> queues = new ArrayList<>();

        if (query != null && location != null) {
            queues = findQueuesByQueryOrLocation(query, location);
        } else if (query != null) {
            queues = findQueuesByQuery(query);
        } else {
            queues = findQueuesByLocation(location);
        }

        return queueMapper.mapToQueueResponses(queues);
    }

    public List<UserResponse> searchProviderByNameAndLocation(String name, String location) {
        List<User> users = new ArrayList<>();

        if (name != null && location != null) {
            users = findUsersByNameOrLocation(name, location);
        } else if (name != null) {
            users = findUsersByName(name);
        } else {
            users = findUsersByLocation(location);
        }

        return userMapper.mapToUserResponses(users);
    }

    private List<Queue> findQueuesByQueryOrLocation(String query, String location) {
        return queueRepository.findAll().stream()
                       .filter(q -> q.getName().contains(query)
                                            || q.getDescription().contains(query)
                                            || q.getAddress().contains(location))
                       .collect(Collectors.toList());
    }

    private List<Queue> findQueuesByQuery(String query) {
        return queueRepository.findAll().stream()
                       .filter(q -> q.getName().contains(query)
                                            || q.getDescription().contains(query))
                       .collect(Collectors.toList());
    }

    private List<Queue> findQueuesByLocation(String location) {
        return queueRepository.findAll().stream()
                       .filter(q -> q.getAddress().contains(location))
                       .collect(Collectors.toList());
    }

    private List<User> findUsersByNameOrLocation(String name, String location) {
        return userRepository.findAll().stream()
                       .filter(u -> u.getName().contains(name)
                                            || u.getAddress().contains(location))
                       .collect(Collectors.toList());
    }

    private List<User> findUsersByName(String name) {
        return userRepository.findAll().stream()
                       .filter(u -> u.getName().contains(name))
                       .collect(Collectors.toList());
    }

    private List<User> findUsersByLocation(String location) {
        return userRepository.findAll().stream()
                       .filter(u -> u.getAddress().contains(location))
                       .collect(Collectors.toList());
    }

}
