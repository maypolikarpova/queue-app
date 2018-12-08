package queueapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueapp.domain.appointment.ReadAppointmentResponse;
import queueapp.domain.queue.CreateQueueRequest;
import queueapp.domain.queue.Queue;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.queue.UpdateQueueRequest;
import queueapp.repository.AppointmentRepository;
import queueapp.repository.QueueRepository;
import queueapp.service.mapper.AppointmentMapper;
import queueapp.service.mapper.QueueMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final QueueRepository queueRepository;
    private final QueueMapper queueMapper;
    private final AppointmentMapper appointmentMapper;
    private final AppointmentRepository appointmentRepository;

    public Optional<String> createQueue(CreateQueueRequest request) {
        return queueMapper.mapToQueue(request)
                       .map(q -> queueRepository.save(q).getQueueId());
    }

    public Optional<QueueResponse> readQueueByQueueId(String queueId) {
        return Optional.ofNullable(queueRepository.findOne(queueId))
                       .flatMap(queueMapper::mapToQueueResponse);
    }

    public Optional<QueueResponse> updateQueue(String queueId, UpdateQueueRequest request) {
        return Optional.ofNullable(queueRepository.findOne(queueId))
                       .flatMap(q -> updateQueueFieldsAndSave(q, request));
    }

    public boolean deleteQueue(String queueId) {
        if (queueRepository.exists(queueId)) {
            queueRepository.delete(queueId);
            return true;
        }
        return false;
    }

    public List<ReadAppointmentResponse> getAppointmentsByQueueId(String queueId) {
        return appointmentMapper.mapToReadAppointmentResponseList(appointmentRepository.findByQueueId(queueId));
    }

    private Optional<QueueResponse> updateQueueFieldsAndSave(Queue queue, UpdateQueueRequest request) {
        queue.setPhoneNumber(request.getPhoneNumber());
        queue.setDescription(request.getDescription());
        queue.setName(request.getName());
        queue.setAddress(request.getAddress());
        queue.setClosed(request.isClosed());

        return queueMapper.mapToQueueResponse(queueRepository.save(queue));
    }
}
