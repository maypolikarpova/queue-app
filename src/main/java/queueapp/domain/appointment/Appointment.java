package queueapp.domain.appointment;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Appointment {
    @Id
    private String appointmentId;
    private String clientId;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;
    private AppointmentStatus status;
    private String queueId;
    private String providerId;

    public boolean isFutureAppointment() {
        return dateTimeFrom.isAfter(LocalDateTime.now());
    }
}