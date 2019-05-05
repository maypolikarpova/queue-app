package queueapp.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import queueapp.domain.appointment.AppointmentStatus;
import queueapp.domain.appointment.RequestAppointmentRequest;
import queueapp.service.AppointmentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentControllerTest {

    private static final String APPOINTMENT_ID = "appointment id";
    private static final String CLIENT_ID = "client id";
    private static final String WRONG_APPOINTMENT_ID = "wrong appointment id";

    @InjectMocks
    private AppointmentController appointmentController;

    @Mock
    private AppointmentService appointmentService;

    @Before
    public void setUp() {
        when(appointmentService.requestAppointmentFromClient(APPOINTMENT_ID, CLIENT_ID))
                .thenReturn(true);
        when(appointmentService.requestAppointmentFromClient(WRONG_APPOINTMENT_ID, CLIENT_ID))
                .thenReturn(false);
        when(appointmentService.deleteAppointment(APPOINTMENT_ID))
                .thenReturn(true);
        when(appointmentService.updateAppointmentStatus(APPOINTMENT_ID, AppointmentStatus.APPROVED))
                .thenReturn(true);
        when(appointmentService.updateAppointmentStatus(APPOINTMENT_ID, AppointmentStatus.CANCELLED))
                .thenReturn(true);

        when(appointmentService.deleteAppointment(WRONG_APPOINTMENT_ID))
                .thenReturn(false);
        when(appointmentService.updateAppointmentStatus(WRONG_APPOINTMENT_ID, AppointmentStatus.APPROVED))
                .thenReturn(false);
        when(appointmentService.updateAppointmentStatus(WRONG_APPOINTMENT_ID, AppointmentStatus.CANCELLED))
                .thenReturn(false);
    }

    @Test
    public void requestAppointmentFromClient_Success() {
        //Given
        RequestAppointmentRequest request = new RequestAppointmentRequest();
        request.setClientId(CLIENT_ID);

        //When
        ResponseEntity<Void> actualResponse = appointmentController.requestAppointment(APPOINTMENT_ID, request);

        //Then
        assertThat(actualResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

    @Test
    public void requestAppointmentFromClient_Fail_WrongAppointmentId() {
        //Given
        RequestAppointmentRequest request = new RequestAppointmentRequest();
        request.setClientId(CLIENT_ID);

        //When
        ResponseEntity<Void> actualResponse = appointmentController.requestAppointment(WRONG_APPOINTMENT_ID, request);

        //Then
        assertThat(actualResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void cancelAppointment_Success() {
        //When
        ResponseEntity<Void> actualResponse = appointmentController.cancelAppointment(APPOINTMENT_ID);

        //Then
        assertThat(actualResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

    @Test
    public void cancelAppointment_Fail() {
        //When
        ResponseEntity<Void> actualResponse = appointmentController.cancelAppointment(WRONG_APPOINTMENT_ID);

        //Then
        assertThat(actualResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void approveAppointment_Success() {
        //When
        ResponseEntity<Void> actualResponse = appointmentController.approveAppointment(APPOINTMENT_ID);

        //Then
        assertThat(actualResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

    }

    @Test
    public void approveAppointment_Fail() {
        //When
        ResponseEntity<Void> actualResponse = appointmentController.approveAppointment(WRONG_APPOINTMENT_ID);

        //Then
        assertThat(actualResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void deleteAppointment_Success() {
        //When
        ResponseEntity<Void> actualResponse = appointmentController.deleteAppointment(APPOINTMENT_ID);

        //Then
        assertThat(actualResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.NO_CONTENT);

    }

    @Test
    public void deleteAppointment_Fail() {
        //When
        ResponseEntity<Void> actualResponse = appointmentController.deleteAppointment(WRONG_APPOINTMENT_ID);

        //Then
        assertThat(actualResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);

    }

}