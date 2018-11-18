package queueapp.domain.queue.appointment;

import lombok.Value;
import queueapp.domain.user.UserResponse;

import java.time.LocalDateTime;

@Value
public class ReadAppointmentResponse {
    private String appointmentId;
    private UserResponse client;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;
    private AppointmentStatus status;
}
