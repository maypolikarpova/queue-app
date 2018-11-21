package queueapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueapp.domain.queue.CreateQueueRequest;
import queueapp.domain.queue.Queue;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.queue.UpdateQueueRequest;
import queueapp.repository.QueueRepository;
import queueapp.service.mapper.QueueMapper;

import java.util.Optional;

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
