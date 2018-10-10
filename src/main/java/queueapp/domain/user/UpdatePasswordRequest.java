package queueapp.domain.user;

import lombok.NonNull;
import lombok.Value;

@Value
public class UpdatePasswordRequest {
    @NonNull
    private String oldPassword;
    @NonNull
    private String newPassword;
}
