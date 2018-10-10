package queueapp.domain.user;

import lombok.NonNull;
import lombok.Value;

@Value
public class LogInUserRequest {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
