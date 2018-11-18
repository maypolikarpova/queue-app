package queueapp.domain.queue;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class QueueResponse {
    private String queueId;
    private String providerId;
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private boolean closed;
    private int futureAppointmentsAmount;
    private LocalDateTime nextApprovedAppointmentDate;
    private LocalDateTime nextAvailableAppointmentDate;
    private List<String> tags;
}
