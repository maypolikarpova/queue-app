package queueapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueapp.domain.queue.*;
import queueapp.domain.queue.Queue;
import queueapp.domain.appointment.Appointment;
import queueapp.domain.appointment.AppointmentStatus;
import queueapp.domain.appointment.ReadAppointmentResponse;
import queueapp.repository.AppointmentRepository;
import queueapp.repository.QueueRepository;
import queueapp.service.mapper.AppointmentMapper;
import queueapp.service.mapper.QueueMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final QueueRepository queueRepository;
    private final AppointmentRepository appointmentRepository;
    private final QueueMapper queueMapper;
    private final AppointmentMapper appointmentMapper;

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
        List<String> appointmentsIds = new ArrayList<>();

        for (Range range : ranges) {
            Appointment appointment = new Appointment();
            appointment.setDateTimeFrom(range.getDateTimeFrom());
            appointment.setDateTimeTo(range.getDateTimeTo());
            appointment.setStatus(AppointmentStatus.CREATED);
            appointment.setQueueId(queueId);

            Optional.ofNullable(appointmentRepository.save(appointment))
                    .map(a -> appointmentsIds.add(a.getClientId()));
            appointmentsIds.add(appointmentId);

        }
    }

    public List<ReadAppointmentResponse> getAppointmentsByQueueIdAndStatus(String queueId, AppointmentStatus status) {

        List<Appointment> appointments = queueRepository.findById(queueId)
                                         .map(Queue::getAppointments)
                                         .map(l -> l.stream()
                                                           .filter(a -> a.getStatus() == status)
                                                           .collect(Collectors.toList()))
                                         .orElseGet(Collections::emptyList);

        return appointmentMapper.mapToReadAppointmentResponseList(appointments);
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
}
