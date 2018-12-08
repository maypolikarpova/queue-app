package queueapp.domain.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateQueueRequest {
    private String providerId;
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private List<Range> ranges;
    private List<String> tags;
}
