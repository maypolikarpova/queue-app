package queueapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import queueapp.domain.appointment.Appointment;
import queueapp.domain.appointment.AppointmentStatus;
import queueapp.domain.appointment.ReadAppointmentResponse;
import queueapp.domain.queue.Range;
import queueapp.repository.AppointmentRepository;
import queueapp.service.mapper.AppointmentMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    public List<String> createAppointments(String queueId, List<Range> ranges) {
        List<String> appointmentsIds = new ArrayList<>();

        for (Range range : ranges) {
            Appointment appointment = new Appointment();
            appointment.setDateTimeFrom(range.getDateTimeFrom());
            appointment.setDateTimeTo(range.getDateTimeTo());
            appointment.setStatus(AppointmentStatus.CREATED);
            appointment.setQueueId(queueId);

            Optional.ofNullable(appointmentRepository.save(appointment))
                    .map(a -> appointmentsIds.add(a.getClientId()));
        }

        return appointmentsIds;
    }

    public List<ReadAppointmentResponse> getAppointmentsByQueueIdAndStatus(String queueId, AppointmentStatus status) {
        return appointmentMapper.mapToReadAppointmentResponseList(
                appointmentRepository.findByQueueIdAndStatus(queueId, status));
    }

    public boolean requestAppointmentFromClient(String appointmentId, String clientId) {
        return Optional.ofNullable(appointmentRepository.findOne(appointmentId))
                       .filter(a -> a.getClientId().equals(clientId))
                       .map(a -> updateAppointmentStatusAndSave(a, AppointmentStatus.REQUESTED))
                       .orElse(false);
    }

    public boolean updateAppointmentStatus(String appointmentId, AppointmentStatus status) {
        return Optional.ofNullable(appointmentRepository.findOne(appointmentId))
                       .map(a -> updateAppointmentStatusAndSave(a, status))
                       .orElse(false);
    }

    public boolean deleteAppointment(String registryId) {
        if (appointmentRepository.exists(registryId)) {
            appointmentRepository.delete(registryId);
            return true;
        }
        return false;
    }

    public int getFutureAppointmentsAmount(String queueId) {
        List<Appointment> appointments = appointmentRepository.findByQueueIdAndStatus(queueId, AppointmentStatus.APPROVED);
        if (!CollectionUtils.isEmpty(appointments)) {
            return appointments.stream()
                           .filter(Appointment::isFutureAppointment)
                           .collect(Collectors.toList())
                           .size();
        }
        return 0;
    }

    public LocalDateTime getNextApprovedAppointmentDate(String queueId) {
        List<Appointment> appointments = appointmentRepository.findByQueueIdAndStatus(queueId, AppointmentStatus.APPROVED);

        return appointments.stream()
                       .sorted()// TODO Wrong logic?
                       .findFirst()
                       .map(Appointment::getDateTimeFrom)
                       .orElseGet(() -> null);
    }

    public LocalDateTime getNextAvailableAppointmentDate(String queueId) {
        List<Appointment> appointments = appointmentRepository.findByQueueIdAndStatus(queueId, AppointmentStatus.CREATED);

        return appointments.stream()
                       .sorted()// TODO Wrong logic?
                       .findFirst()
                       .map(Appointment::getDateTimeFrom)
                       .orElseGet(() -> null);
    }

    private boolean updateAppointmentStatusAndSave(Appointment appointment, AppointmentStatus status) {
        appointment.setStatus(status);
        return Optional.ofNullable(appointmentRepository.save(appointment))
                       .isPresent();
    }

}
