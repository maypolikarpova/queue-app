package queueapp.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.util.List;

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
    private List<String> occupation;
    private byte[] photo; //TODO save pics in mongo repo in a proper format
    private String address;
}
