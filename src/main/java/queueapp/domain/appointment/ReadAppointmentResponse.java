package queueapp.domain.appointment;

import lombok.Value;
import queueapp.domain.user.UserResponse;

import java.time.LocalDateTime;

@Value
public class ReadAppointmentResponse {
    private String queueId;
    private String appointmentId;
    private UserResponse client;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;
    private AppointmentStatus status;
}
