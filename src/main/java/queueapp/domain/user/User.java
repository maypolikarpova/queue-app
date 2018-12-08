package queueapp.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class User {
    @Id
    private String userId;
    @NonNull
    private String name;
    private String phoneNumber;
    @NonNull
    private String email;
    private String password;
    private String photo;
    private String address;
}
