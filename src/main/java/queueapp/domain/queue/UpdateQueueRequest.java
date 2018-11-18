package queueapp.domain.queue;

import lombok.Value;

@Value
public class UpdateQueueRequest {
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
}
