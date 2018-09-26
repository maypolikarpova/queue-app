package queueapp.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Client {
    @Id
    private String clientId;
    private String name;
    private String phoneNumber;
    private String email;

}
