package queueapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueapp.domain.user.LogInUserRequest;
import queueapp.domain.user.User;
import queueapp.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> createUser(User user) {
        return Optional.ofNullable(userRepository.save(user));
    }

    public Optional<User> readUser(String userId) {
        return userRepository.findById(userId);
    }

    public boolean updateUserInfo(String userId, User user) {
        if (userRepository.existsById(userId)) {
            user.setUserId(userId);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean updateUserPhoto(String userId, byte[] photo) {
        //TODO implement
        return true;
    }

    public boolean updateUserPassword(String userId, String oldPassword, String newPassword) {
        return userRepository.findById(userId)
                       .filter(c -> validatePassword(c.getPassword(), oldPassword))
                       .map(c -> updatePassword(c, newPassword))
                       .orElse(false);

    }

    public boolean deleteUser(String userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public Optional<User> identifyUser(LogInUserRequest logInUserRequest) {
        return userRepository.findByEmail(logInUserRequest.getEmail())
                       .filter(user -> user.getPassword().equals(logInUserRequest.getPassword())); //TODO if logInUserRequest password in db is hashed or encrypted - decrypt!
    }

    private boolean validatePassword(String userPassword, String oldPassword) {
        return userPassword.equals(oldPassword); //TODO if user password in db is hashed or encrypted - decrypt!
    }

    private boolean updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return createUser(user).isPresent();
    }
}
