package queueapp.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import queueapp.domain.appointment.ReadAppointmentResponse;
import queueapp.domain.queue.CreateQueueRequest;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.queue.UpdateQueueRequest;
import queueapp.service.QueueService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(value = "v1/", description = "Queue Controller")
public class QueueController {

    private final QueueService queueService;

    @ApiOperation(value = "Create new Queue", nickname = "createQueue", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "v1/queue",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<String> createQueue(@RequestBody CreateQueueRequest request) {
        return queueService.createQueue(request)
                       .map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @ApiOperation(value = "Read Queue information by queue id", nickname = "readQueueById", response = QueueResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = QueueResponse.class),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "v1/queue/{queue-id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<QueueResponse> readQueueById(@PathVariable("queue-id") String queueId) {
        return queueService.readQueueByQueueId(queueId)
                       .map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Update Queue", nickname = "updateQueue", response = QueueResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = QueueResponse.class),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "v1/queue/{queue-id}",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<QueueResponse> updateQueue(@PathVariable("queue-id") String queueId,
                                                     @RequestBody UpdateQueueRequest updateQueueRequest) {
        return queueService.updateQueue(queueId, updateQueueRequest)
                       .map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Update Queue", nickname = "updateQueue", response = QueueResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request", response = QueueResponse.class),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "v1/queue/{queue-id}/closed",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<QueueResponse> toggleQueueState(@PathVariable("queue-id") String queueId) {
        return queueService.toggleQueueState(queueId)
                       .map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Delete Queue", nickname = "deleteQueue")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "v1/queue/{queue-id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteQueue(@PathVariable("queue-id") String queueId) {
        return queueService.deleteQueue(queueId)
                       ? ResponseEntity.notFound().build()
                       : ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Get appointment by queue id", nickname = "getAppoinmentsByQueueIdAndStatus", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created"),
            @ApiResponse(code = 404, message = "Bad Request")})
    @RequestMapping(value = "v1/queue/{queue-id}/appointments",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<ReadAppointmentResponse>> getAppoinmentsByQueueIdAndStatus(@PathVariable("queue-id") String queueId) {

        List<ReadAppointmentResponse> responses = queueService.getAppointmentsByQueueId(queueId);

        return CollectionUtils.isEmpty(responses)
                       ? ResponseEntity.notFound().build()
                       : ResponseEntity.ok(responses);
    }
}
