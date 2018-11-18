package queueapp.domain.user;

import lombok.Value;

@Value
public class UpdateUserRequest {
    private String email;
    private String name;
    private String phoneNumber;
    private byte[] photo;
}
