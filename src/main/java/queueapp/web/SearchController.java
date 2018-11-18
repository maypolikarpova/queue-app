package queueapp.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.queue.appointment.Appointment;
import queueapp.domain.user.UserResponse;
import queueapp.service.AppointmentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(value = "v1/search", description = "Search Controller")
public class SearchController {

    private final AppointmentService appointmentService;

    @ApiOperation(value = "Request appointment", nickname = "requestAppointment", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/queue/{query}/{location}", //query params look in description tags name of the queue
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<QueueResponse>> requestAppointment(@PathVariable("registry-id") String registryId,
                                                                  @RequestBody Appointment appointment) {
        return appointmentService.updateRegistry(registryId, appointment)
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Request appointment", nickname = "requestAppointment", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/provider/{name}/{location}", // provider = person with at least one queue !
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<List<UserResponse>> cancelAppointment(@PathVariable("registry-id") String registryId,
                                                                @RequestBody Appointment appointment) {
        return appointmentService.updateRegistry(registryId, appointment)
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Request appointment", nickname = "requestAppointment", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{appointment-id}/status/approved",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<String> approveAppointment(@PathVariable("registry-id") String registryId,
                                                    @RequestBody Appointment appointment) {
        return appointmentService.updateRegistry(registryId, appointment)
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Delete appointment", nickname = "deleteAppointment", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{appointment-id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAppointment(@PathVariable("appointment-id") String registryId) {
        return appointmentService.deleteAppointment(registryId)
                       ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
