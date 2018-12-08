package queueapp.service.mapper;

import org.springframework.stereotype.Component;
import queueapp.domain.user.CreateUserRequest;
import queueapp.domain.user.User;
import queueapp.domain.user.UserResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public Optional<User> mapToUser(CreateUserRequest request) {
        return Optional.ofNullable(request)
                       .map(r -> {
                           User user = new User();
                           user.setName(r.getName());
                           user.setEmail(r.getEmail());
                           user.setPassword(r.getPassword());
                           user.setPhoneNumber(r.getPhoneNumber());
                           user.setPhoto(r.getPhoto());
                           user.setAddress(r.getAddress());

                           return user;
                       });
    }

    public Optional<UserResponse> mapToUserResponse(User user) {
        return Optional.ofNullable(user)
                       .map(u -> new UserResponse(
                               u.getUserId(),
                               u.getEmail(),
                               u.getPhoneNumber(),
                               u.getName(),
                               u.getPhoto(),
                               u.getAddress()
                       ));
    }

    public List<UserResponse> mapToUserResponses(List<User> users) {
        return users.stream()
                       .map(this::mapToUserResponse)
                       .filter(Optional::isPresent)
                       .map(Optional::get)
                       .collect(Collectors.toList());
    }
}
