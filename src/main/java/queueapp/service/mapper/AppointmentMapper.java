package queueapp.service.mapper;

import org.springframework.stereotype.Component;
import queueapp.domain.appointment.Appointment;
import queueapp.domain.appointment.ReadAppointmentResponse;
import queueapp.domain.user.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentMapper {

    public List<ReadAppointmentResponse> mapToReadAppointmentResponseList(List<Appointment> appointments) {
        return appointments.stream()
                       .map(this::mapToReadAppointmentResponse)
                       .collect(Collectors.toList());
    }

    private ReadAppointmentResponse mapToReadAppointmentResponse(Appointment appointment) {
        return new ReadAppointmentResponse(
                appointment.getQueueId(),
                appointment.getAppointmentId(),
                mapToUserResponse(appointment.getClientId()),
                appointment.getDateTimeFrom(),
                appointment.getDateTimeTo(),
                appointment.getStatus()
        );
    }

    private UserResponse mapToUserResponse(String clientId) {
        return null;// ?????
    }
}
