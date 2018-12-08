package queueapp.domain.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateQueueRequest {
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
}
