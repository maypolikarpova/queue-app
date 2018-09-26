package queueapp.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Provider {
    @Id
    private String providerId;
    private String name;
    private String occupation; //LIST?
    private String adress;
    private String phoneNumber;
    private String email;
    private List<String> createdQueues;


}
