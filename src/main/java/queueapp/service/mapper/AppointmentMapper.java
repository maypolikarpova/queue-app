package queueapp.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import queueapp.domain.appointment.Appointment;
import queueapp.domain.appointment.ReadAppointmentResponse;
import queueapp.domain.user.UserResponse;
import queueapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppointmentMapper {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
        return Optional.ofNullable(userRepository.findOne(clientId))
                .flatMap(userMapper::mapToUserResponse)
                .orElse(null);
    }
}