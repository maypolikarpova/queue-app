package queueapp.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import queueapp.domain.queue.appointment.Appointment;
import queueapp.service.AppointmentService;

@RestController
@RequiredArgsConstructor
@Api(value = "v1/appointment", description = "Appointment Controller")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @ApiOperation(value = "Request appointment", nickname = "requestAppointment", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{appointment-id}/client/{client-id}/status/requested",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<String> requestAppointment(@PathVariable("registry-id") String registryId,
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
    @RequestMapping(value = "/{appointment-id}/status/cancelled",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<String> cancelAppointment(@PathVariable("registry-id") String registryId,
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
    public ResponseEntity<Void> deleteAppointment(@PathVariable("appointment-id") String registryId) {
        return appointmentService.deleteAppointment(registryId)
                       ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
