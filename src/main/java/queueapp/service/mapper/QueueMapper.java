package queueapp.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import queueapp.domain.queue.CreateQueueRequest;
import queueapp.domain.queue.Queue;
import queueapp.domain.queue.QueueResponse;
import queueapp.service.AppointmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QueueMapper {

    private final AppointmentService appointmentService;

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

                           return queue;
                       });
    }

    public List<QueueResponse> mapToQueueResponses(List<Queue> queues) {
        List<QueueResponse> responses = new ArrayList<>();
        for (Queue queue : queues) {

            Optional<QueueResponse> response = mapToQueueResponse(queue);
            response.ifPresent(responses::add);
        }

        return responses;
    }

    public Optional<QueueResponse> mapToQueueResponse(Queue queue) {
        String queueId = queue.getQueueId();

        return Optional.ofNullable(queue)
                       .map(q -> new QueueResponse(
                               q.getQueueId(),
                               q.getProviderId(),
                               q.getName(),
                               q.getDescription(),
                               q.getAddress(),
                               q.getPhoneNumber(),
                               q.isClosed(),
                               appointmentService.getFutureAppointmentsAmount(queueId),
                               appointmentService.getNextApprovedAppointmentDate(queueId),
                               appointmentService.getNextAvailableAppointmentDate(queueId),
                               q.getTags()
                       ));
    }
}
