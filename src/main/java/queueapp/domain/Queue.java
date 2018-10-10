package queueapp.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Queue {
    @Id
    private String queueId;
    private String providerId;
    private String name;
    private List<String> clientIds;
}
