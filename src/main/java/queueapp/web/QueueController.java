package queueapp.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import queueapp.domain.Queue;
import queueapp.service.QueueService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(value = "v1/queue", description = "Queue Controller")
public class QueueController {

    private final QueueService queueService;

    @ApiOperation(value = "Create new Queue", nickname = "createQueue", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class)})
    @RequestMapping(value = "/create",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<String> createQueue(@RequestBody Queue queue) {
        return ResponseEntity.ok(queueService.createQueue(queue));
    }

    @ApiOperation(value = "Read Queue information by queue id", nickname = "readQueueById", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Queue.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{queue-id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Queue> readQueueById(@PathVariable("queue-id") String queueId) {
        return queueService.readQueueByQueueId(queueId)
                       .map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Read Queue information by provider id", nickname = "readQueueByProviderId", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Queue.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{provider-id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Queue> readQueueByProviderId(@PathVariable("provider-id") String queueId) {
        return queueService.readQueueByProviderId(queueId)
                       .map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Read information about all client queues", nickname = "readQueuesByClientId", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{client-id}/queues",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<Queue>> readQueuesByClientId(@PathVariable("client-id") String clientId) {
        List<Queue> queues = queueService.readQueuesByClientId(clientId);
        return CollectionUtils.isEmpty(queues)
                       ? ResponseEntity.notFound().build()
                       : ResponseEntity.ok(queues);
    }

    @ApiOperation(value = "Update Queue", nickname = "updateQueue", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{queue-id}",
            produces = {"application/json"},
            method = RequestMethod.PUT)
    public ResponseEntity<String> updateQueue(@PathVariable("queue-id") String queueId,
                                              @RequestBody Queue queue) {
        return queueService.updateQueue(queueId, queue)
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Delete Queue", nickname = "deleteQueue", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{queue-id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteQueue(@PathVariable("queue-id") String queueId) {
        return queueService.deleteQueue(queueId)
                       ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
