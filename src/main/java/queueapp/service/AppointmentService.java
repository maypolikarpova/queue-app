package queueapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueapp.domain.queue.appointment.Appointment;
import queueapp.repository.AppointmentRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public String createRegistry(Appointment appointment) {
        return appointmentRepository.save(appointment).getAppointmentId();
    }

    public Optional<Appointment> readRegistry(String registryId) {
        return appointmentRepository.findById(registryId);
    }

    public boolean updateRegistry(String registryId, Appointment appointment) {
        if (appointmentRepository.existsById(registryId)) {
            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }

    public boolean deleteRegistry(String registryId) {
        if (appointmentRepository.existsById(registryId)) {
            appointmentRepository.deleteById(registryId);
            return true;
        }
        return false;
    }
}
