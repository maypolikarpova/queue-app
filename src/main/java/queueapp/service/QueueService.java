package queueapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueapp.domain.Queue;
import queueapp.repository.QueueRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final QueueRepository queueRepository;

    public String createQueue(Queue queue) {
        return queueRepository.save(queue).getQueueId();
    }

    public Optional<Queue> readQueue(String queueId) {
        return queueRepository.findById(queueId);
    }

    public boolean updateQueue(String queueId, Queue queue) {
        if (queueRepository.existsById(queueId)) {
            queueRepository.save(queue);
            return true;
        }
        return false;
    }

    public boolean deleteQueue(String queueId) {
        if (queueRepository.existsById(queueId)) {
            queueRepository.deleteById(queueId);
            return true;
        }
        return false;
    }
}
