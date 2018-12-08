package queueapp.domain.user;

import lombok.Value;

@Value
public class UserResponse {
    private String userId;
    private String email;
    private String password;
    private String phoneNumber;
    private String name;
    private String photo;
    private String address;
}
