package queueapp.domain.appointment;

import lombok.Value;
import queueapp.domain.queue.QueueResponse;

import java.time.LocalDateTime;

@Value
public class ReadAppointmentByClientIdResponse {
    private String appointmentId;
    private QueueResponse queue;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;
    private AppointmentStatus status;
}
