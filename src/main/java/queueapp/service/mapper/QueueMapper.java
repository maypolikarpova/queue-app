package queueapp.service.mapper;

import org.springframework.stereotype.Component;
import queueapp.domain.queue.CreateQueueRequest;
import queueapp.domain.queue.Queue;
import queueapp.domain.queue.QueueResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class QueueMapper {

    public Optional<Queue> mapToQueue(CreateQueueRequest request) {
        return Optional.ofNullable(request)
                       .map(r -> {
                           Queue queue = new Queue();
                           queue.setName(r.getName());
                           queue.setProviderId(r.getProviderId());
                           queue.setAddress(r.getAddress());
                           queue.setDescription(r.getDescription());
                           queue.setPhoneNumber(r.getPhoneNumber());
                           queue.setTags(r.getTags());
                           queue.setRanges(r.getRanges());

                           return queue;
                       });
    }

    public Optional<QueueResponse> mapToQueueResponse(Queue queue) {
        return Optional.ofNullable(queue)
                       .map(q -> {
                           return new QueueResponse(
                                   q.getQueueId(),
                                   q.getProviderId(),
                                   q.getName(),
                                   q.getDescription(),
                                   q.getAddress(),
                                   q.getPhoneNumber(),
                                   q.isClosed(),
                                   q.getFutureAppointmentsAmount(),
                                   q.getNextApprovedAppointmentDate(),
                                   q.getNextAvailableAppointmentDate(),
                                   q.getTags()
                           );

                       });
    }

    public List<QueueResponse> mapToQueueResponses(List<Queue> queues) {
        List<QueueResponse> responses = new ArrayList<>();
        for(Queue queue: queues) {
            Optional<QueueResponse> response = mapToQueueResponse(queue);
            response.ifPresent(responses::add);
        }

        return responses;
    }
}
