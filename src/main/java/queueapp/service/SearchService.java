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
        List<Queue> queues = queueRepository.findAll().stream()
                                     .filter(q -> q.getName().contains(query)
                                                          || q.getDescription().contains(query)
                                                          || q.getAddress().contains(location))
                                     .collect(Collectors.toList());

        return queueMapper.mapToQueueResponses(queues);
    }

    public List<UserResponse> searchProviderByNameAndLocation(String name, String location) {
        List<User> users = userRepository.findAll().stream()
                                     .filter(u -> u.getName().contains(name)
                                                          || u.getAddress().contains(location))
                                     .collect(Collectors.toList());

        return userMapper.mapToUserResponses(users);
    }

}
