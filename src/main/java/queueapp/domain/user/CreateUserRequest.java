package queueapp.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    private String email;
    private String password;
    private String phoneNumber;
    private String name;
    private String photo;

}
