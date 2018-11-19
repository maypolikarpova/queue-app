package queueapp.domain.queue;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.util.CollectionUtils;
import queueapp.domain.queue.appointment.Appointment;
import queueapp.domain.queue.appointment.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class Queue {
    @Id
    private String queueId;
    @NonNull
    private String providerId;
    private String name;
    private boolean closed;
    private String description;
    private List<Appointment> appointments = new ArrayList<>();
    private List<String> tags;
    private String address;
    private List<Range> ranges;
    private String phoneNumber;

    public int getFutureAppointmentsAmount() {
        if (!CollectionUtils.isEmpty(appointments)) {
            return appointments.stream()
                           .filter(Appointment::isFutureAppointment)
                           .collect(Collectors.toList())
                           .size();
        }
        return 0;
    }

    public LocalDateTime getNextApprovedAppointmentDate() {
        return appointments.stream()
                       .filter(a -> a.isFutureAppointment() && a.getStatus() == AppointmentStatus.APPROVED)
                       .findFirst()
                       .map(Appointment::getDateTimeFrom)
                       .orElseGet(() -> null);
    }

    public LocalDateTime getNextAvailableAppointmentDate() {
        return appointments.stream()
                       .filter(a -> a.isFutureAppointment() && a.getStatus() == AppointmentStatus.CREATED)
                       .findFirst()
                       .map(Appointment::getDateTimeFrom)
                       .orElseGet(() -> null);
    }

}
