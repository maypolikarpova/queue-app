package queueapp.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import queueapp.domain.appointment.AppointmentStatus;
import queueapp.domain.queue.Range;
import queueapp.service.AppointmentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(value = "v1/", description = "Appointment Controller")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @ApiOperation(value = "Request appointment", nickname = "requestAppointment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "v1/appointment/{appointment-id}/client/{client-id}/status/requested",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<Void> requestAppointment(@PathVariable("appointment-id") String appointmentId,
                                                   @PathVariable("client-id") String clientId) {
        return appointmentService.requestAppointmentFromClient(appointmentId, clientId)
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Request appointment", nickname = "cancelAppointment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "v1/appointment/{appointment-id}/status/cancelled",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<String> cancelAppointment(@PathVariable("appointment-id") String appointmentId) {
        return appointmentService.updateAppointmentStatus(appointmentId, AppointmentStatus.CANCELLED)
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Approve appointment", nickname = "approveAppointment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "v1/appointment/{appointment-id}/provider/{provider-id}/status/approved",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<String> approveAppointment(@PathVariable("appointment-id") String appointmentId) {
        return appointmentService.updateAppointmentStatus(appointmentId, AppointmentStatus.APPROVED)
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Delete appointment", nickname = "deleteAppointment")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "v1/appointment/{appointment-id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAppointment(@PathVariable("appointment-id") String appointmentId) {
        return appointmentService.deleteAppointment(appointmentId)
                       ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Create new appointment", nickname = "createRegistry")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "v1/appointment/queue/{queue-id}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<List<String>> createAppointments(@PathVariable("queue-id") String queueId,
                                                           @RequestBody List<Range> ranges) {
        List<String> appointmentsIds = appointmentService.createAppointments(queueId, ranges);

        return CollectionUtils.isEmpty(appointmentsIds)
                       ? ResponseEntity.notFound().build()
                       : ResponseEntity.ok(appointmentsIds);
    }
}
