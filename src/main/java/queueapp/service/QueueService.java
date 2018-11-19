package queueapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueapp.domain.queue.*;
import queueapp.domain.queue.Queue;
import queueapp.domain.queue.appointment.Appointment;
import queueapp.domain.queue.appointment.AppointmentStatus;
import queueapp.domain.queue.appointment.ReadAppointmentResponse;
import queueapp.repository.QueueRepository;
import queueapp.service.mapper.QueueMapper;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final QueueRepository queueRepository;
    private final QueueMapper queueMapper;

    public Optional<String> createQueue(CreateQueueRequest request) {
        return queueMapper.mapToQueue(request)
                       .map(q -> queueRepository.save(q).getQueueId());
    }

    public Optional<QueueResponse> readQueueByQueueId(String queueId) {
        return queueRepository.findById(queueId)
                       .flatMap(queueMapper::mapToQueueResponse);
    }

    public Optional<QueueResponse> updateQueue(String queueId, UpdateQueueRequest request) {
        return queueRepository.findById(queueId)
                       .flatMap(q -> updateQueueFieldsAndSave(q, request));
    }

    public Optional<QueueResponse> toggleQueueState(String queueId) {
        return queueRepository.findById(queueId)
                       .flatMap(this::toggleQueueState);
    }

    public boolean deleteQueue(String queueId) {
        if (queueRepository.existsById(queueId)) {
            queueRepository.deleteById(queueId);
            return true;
        }
        return false;
    }

    public List<String> createAppointments(String queueId, List<Range> ranges) {
        return queueRepository.findById(queueId)
                       .map(q -> createAppointmentsFromRanges(q, ranges))
                       .orElseGet(Collections::emptyList);
    }

    public List<ReadAppointmentResponse> getAppoinmentsByQueueIdAndStatus(String queueId, AppointmentStatus status) {

        return queueRepository.findById(queueId)
                .filter(q -> q.getAppointments());
    }

    private Optional<QueueResponse> updateQueueFieldsAndSave(Queue queue, UpdateQueueRequest request) {
        queue.setPhoneNumber(request.getPhoneNumber());
        queue.setDescription(request.getDescription());
        queue.setName(request.getName());
        queue.setAddress(request.getAddress());

        return queueMapper.mapToQueueResponse(queueRepository.save(queue));
    }

    private Optional<QueueResponse> toggleQueueState(Queue queue) {
        queue.setClosed(!queue.isClosed());
        return queueMapper.mapToQueueResponse(queueRepository.save(queue));
    }

    private List<String> createAppointmentsFromRanges(Queue queue, List<Range> ranges) {
        List<Appointment> appointments = queue.getAppointments();
        List<String> appointmentsIds = new ArrayList<>();

        for (Range range : ranges) {
            String appointmentId = getId();

            Appointment appointment = new Appointment();
            appointment.setDateTimeFrom(range.getDateTimeFrom());
            appointment.setDateTimeTo(range.getDateTimeTo());
            appointment.setStatus(AppointmentStatus.CREATED);
            appointment.setQueueId(queue.getQueueId());
            appointment.setAppointmentId(appointmentId);

            appointments.add(appointment);
            appointmentsIds.add(appointmentId);
        }

        queue.setAppointments(appointments);
        queueRepository.save(queue);

        return appointmentsIds;
    }

    private String getId() {
        return UUID.randomUUID().toString();
    }
}
