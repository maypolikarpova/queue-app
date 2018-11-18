package queueapp.domain.queue;

import lombok.Value;

import java.util.List;

@Value
public class CreateQueueRequest {
    private String providerId;
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private List<Range> ranges;
    private List<String> tags;
}
