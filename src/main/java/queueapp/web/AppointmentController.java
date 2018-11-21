package queueapp.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import queueapp.domain.appointment.AppointmentStatus;
import queueapp.service.AppointmentService;

@RestController
@RequiredArgsConstructor
@Api(value = "v1/appointment", description = "Appointment Controller")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @ApiOperation(value = "Request appointment", nickname = "requestAppointment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "/{appointment-id}/client/{client-id}/status/requested",
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
    @RequestMapping(value = "/{appointment-id}/status/cancelled",
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
    @RequestMapping(value = "/{appointment-id}/provider/{provider-id}/status/approved",
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
    @RequestMapping(value = "/{appointment-id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAppointment(@PathVariable("appointment-id") String appointmentId) {
        return appointmentService.deleteAppointment(appointmentId)
                       ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
