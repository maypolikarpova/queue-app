package queueapp.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import queueapp.domain.queue.CreateQueueRequest;
import queueapp.domain.queue.Range;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.queue.UpdateQueueRequest;
import queueapp.domain.queue.appointment.ReadAppointmentResponse;
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
    @RequestMapping(produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<String> createQueue(@RequestBody CreateQueueRequest request) {
        return ResponseEntity.ok(queueService.createQueue(request));
    }

    @ApiOperation(value = "Read Queue information by queue id", nickname = "readQueueById", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = QueueResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{queue-id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<QueueResponse> readQueueById(@PathVariable("queue-id") String queueId) {
        return queueService.readQueueByQueueId(queueId)
                       .map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }


    @ApiOperation(value = "Update Queue", nickname = "updateQueue", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{queue-id}",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<QueueResponse> updateQueue(@PathVariable("queue-id") String queueId,
                                                     @RequestBody UpdateQueueRequest updateQueueRequest) {
        return queueService.updateQueue(queueId, queue)
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Update Queue", nickname = "updateQueue", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{queue-id}/closed",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<QueueResponse> switchQueueState(@PathVariable("queue-id") String queueId) {
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

    @ApiOperation(value = "Create new appointment", nickname = "createRegistry", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/{queue-id}/appointments",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<Void> createAppointments(@RequestBody List<Range> ranges) {
        return ResponseEntity.ok(appointmentService.createRegistry(ranges));
    }

    @ApiOperation(value = "Get appointment by queue id", nickname = "getAppoinmentsByQueueIdAndStatus", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created"),
            @ApiResponse(code = 404, message = "Bad Request")})
    @RequestMapping(value = "/{queue-id}/appointments/status/{status}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<ReadAppointmentResponse>> getAppoinmentsByQueueIdAndStatus() {
        return ResponseEntity.ok(appointmentService.createRegistry(ranges));
    }
}
