package queueapp.domain.queue.appointment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Appointment {
    private String appointmentId;
    private String clientId;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;
    private AppointmentStatus status;
    private String queueId;

    public boolean isFutureAppointment() {
        return dateTimeFrom.isAfter(LocalDateTime.now());
    }
}