package queueapp.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInUserRequest {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
