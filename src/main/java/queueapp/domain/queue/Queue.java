package queueapp.domain.queue;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
public class Queue {
    @Id
    private String queueId;
    @NonNull
    private String providerId;
    private String name;
    private boolean closed;
    private String description;
    private List<String> tags;
    private String address;
    private String phoneNumber;
}
