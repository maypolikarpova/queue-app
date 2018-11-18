package queueapp.domain.user;

import lombok.Value;

@Value
public class CreateUserRequest {

    private String email;
    private String password;
    private String phoneNumber;
    private String name;
    private byte[] photo; //TODO what the hell with pics
}
