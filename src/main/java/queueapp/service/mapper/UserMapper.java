package queueapp.service.mapper;

import org.springframework.stereotype.Component;
import queueapp.domain.user.CreateUserRequest;
import queueapp.domain.user.User;
import queueapp.domain.user.UserResponse;

import java.util.Optional;

@Component
public class UserMapper {

    public Optional<User> mapToUser(CreateUserRequest request) {
        return Optional.ofNullable(request)
                       .map(r -> {
                           User user = new User();
                           user.setName(r.getName());
                           user.setEmail(r.getEmail());
                           user.setPassword(r.getPassword()); //TODO encryption
                           user.setPhoneNumber(r.getPhoneNumber());
                           user.setPhoto(r.getPhoto()); //TODO pics

                           return user;
                       });
    }

    public Optional<UserResponse> mapToUserResponse(User user) {
        return Optional.ofNullable(user)
                       .map(u -> new UserResponse(
                               u.getUserId(),
                               u.getEmail(),
                               u.getPassword(),
                               u.getPhoneNumber(),
                               u.getName(),
                               u.getAddress()
                       ));
    }
}
