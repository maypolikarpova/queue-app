package queueapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueapp.domain.appointment.Appointment;
import queueapp.domain.appointment.AppointmentStatus;
import queueapp.repository.AppointmentRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public boolean requestAppointmentFromClient(String appointmentId, String clientId) {
        return appointmentRepository.findById(appointmentId)
                       .filter(a -> a.getClientId().equals(clientId))
                       .map(a -> updateAppointmentStatusAndSave(a, AppointmentStatus.REQUESTED))
                       .orElse(false);
    }

    public boolean updateAppointmentStatus(String appointmentId, AppointmentStatus status) {
        return appointmentRepository.findById(appointmentId)
                       .map(a -> updateAppointmentStatusAndSave(a, status))
                       .orElse(false);
    }

    public boolean deleteAppointment(String registryId) {
        if (appointmentRepository.existsById(registryId)) {
            appointmentRepository.deleteById(registryId);
            return true;
        }
        return false;
    }

    private boolean updateAppointmentStatusAndSave(Appointment appointment, AppointmentStatus status) {
        appointment.setStatus(status);
        return Optional.ofNullable(appointmentRepository.save(appointment))
                       .isPresent();
    }

}
