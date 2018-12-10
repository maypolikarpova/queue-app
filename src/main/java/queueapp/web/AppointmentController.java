package queueapp.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import queueapp.domain.appointment.AppointmentStatus;
import queueapp.domain.appointment.RequestAppointmentRequest;
import queueapp.service.AppointmentService;

import javax.validation.constraints.NotNull;

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
    @RequestMapping(value = "v1/appointment/{appointment-id}/request",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<Void> requestAppointment(@PathVariable("appointment-id") String appointmentId,
                                                   @RequestBody @NotNull RequestAppointmentRequest clientId) {
        return appointmentService.requestAppointmentFromClient(appointmentId, clientId.getClientId())
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Request appointment", nickname = "cancelAppointment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "v1/appointment/{appointment-id}/cancel",
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
    @RequestMapping(value = "v1/appointment/{appointment-id}/approve",
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
}
