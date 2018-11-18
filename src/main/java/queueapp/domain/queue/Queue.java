package queueapp.domain.queue;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import queueapp.domain.queue.appointment.Appointment;

import java.util.List;

@Data
public class Queue {
    @Id
    private String queueId;
    @NonNull
    private String providerId;
    private String name;
    private boolean closed;
    private String description;
    private List<Appointment> appointments;
    private List<String> tags;
    private String address;
}
