package queueapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueapp.domain.appointment.Appointment;
import queueapp.domain.queue.Queue;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.user.*;
import queueapp.repository.AppointmentRepository;
import queueapp.repository.QueueRepository;
import queueapp.repository.UserRepository;
import queueapp.service.mapper.QueueMapper;
import queueapp.service.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final QueueRepository queueRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserMapper userMapper;
    private final QueueMapper queueMapper;

    public Optional<UserResponse> createUser(CreateUserRequest request) {
        Optional<User> savedUser = userMapper.mapToUser(request)
                                           .map(userRepository::save);

        if (savedUser.isPresent()) {
            return userMapper.mapToUserResponse(savedUser.get());
        }

        return Optional.empty();
    }

    public Optional<UserResponse> readUser(String userId) {
        return Optional.ofNullable(userRepository.findOne(userId))
                       .flatMap(userMapper::mapToUserResponse);
    }

    public Optional<UserResponse> updateUser(String userId, UpdateUserRequest request) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findOne(userId));

        if (optionalUser.isPresent() && Optional.ofNullable(request).isPresent()) {
            return userMapper.mapToUserResponse(updateUserInfo(optionalUser.get(), request));
        }

        return Optional.empty();
    }

    public boolean updateUserPassword(String userId, String oldPassword, String newPassword) {
        return Optional.ofNullable(userRepository.findOne(userId))
                       .filter(c -> validatePassword(c.getPassword(), oldPassword))
                       .map(c -> updatePassword(c, newPassword))
                       .orElse(false);

    }

    public boolean deleteUser(String userId) {
        if (userRepository.exists(userId)) {
            userRepository.delete(userId);
            return true;
        }
        return false;
    }

    public Optional<User> identifyUser(LogInUserRequest logInUserRequest) {
        return userRepository.findByEmail(logInUserRequest.getEmail())
                       .filter(user -> user.getPassword().equals(logInUserRequest.getPassword())); //TODO if logInUserRequest password in db is hashed or encrypted - decrypt!
    }

    public List<QueueResponse> readQueueByProviderId(String providerId) {
        return queueMapper.mapToQueueResponses(queueRepository.findByProviderId(providerId));
    }

    public List<QueueResponse> readQueuesByClientId(String clientId) {
        List<String> queueIds = appointmentRepository.findByClientId(clientId)
                                        .stream()
                                        .map(Appointment::getQueueId)
                                        .collect(Collectors.toList());
        List<Queue> queues = new ArrayList<>();

        for (String id : queueIds) {
            Optional.ofNullable(queueRepository.findOne(id))
                    .map(queues::add);
        }

        return queueMapper.mapToQueueResponses(queues);
    }

    private boolean validatePassword(String userPassword, String oldPassword) {
        return userPassword.equals(oldPassword);
    }

    private boolean updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return Optional.ofNullable(userRepository.save(user))
                       .isPresent();
    }

    private User updateUserInfo(User user, UpdateUserRequest request) {
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getPhoto() != null) {
            user.setPhoto(request.getPhoto());
        }

        return userRepository.save(user);
    }

}
