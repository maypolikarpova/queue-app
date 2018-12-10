package queueapp.service.mapper;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import queueapp.domain.appointment.Appointment;
import queueapp.domain.appointment.ReadAppointmentByClientIdResponse;
import queueapp.domain.appointment.ReadAppointmentResponse;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.user.UserResponse;
import queueapp.repository.QueueRepository;
import queueapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppointmentMapper {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final QueueRepository queueRepository;
    private final QueueMapper queueMapper;

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
        if (StringUtils.isNotBlank(clientId)) {
            return Optional.ofNullable(userRepository.findOne(clientId))
                           .flatMap(userMapper::mapToUserResponse)
                           .orElse(null);
        }

        return null;
    }

    public List<ReadAppointmentByClientIdResponse> mapToReadAppointmentByClientIdResponseList(List<Appointment> appointments) {
        return appointments.stream()
                       .map(this::mapToReadAppointmentByClientIdResponse)
                       .collect(Collectors.toList());
    }

    private ReadAppointmentByClientIdResponse mapToReadAppointmentByClientIdResponse(Appointment appointment) {
        return new ReadAppointmentByClientIdResponse(
                appointment.getAppointmentId(),
                getQueueAndMapToQueueResponse(appointment.getQueueId()),
                appointment.getDateTimeFrom(),
                appointment.getDateTimeTo(),
                appointment.getStatus()
        );
    }

    private QueueResponse getQueueAndMapToQueueResponse(String queueId) {
        return Optional.ofNullable(queueRepository.findOne(queueId))
                       .flatMap(queueMapper::mapToQueueResponse)
                       .orElse(null);
    }
}