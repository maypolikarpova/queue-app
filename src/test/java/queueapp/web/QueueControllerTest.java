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
import queueapp.domain.appointment.ReadAppointmentResponse;
import queueapp.domain.queue.CreateQueueRequest;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.queue.Range;
import queueapp.domain.queue.UpdateQueueRequest;
import queueapp.domain.user.UserResponse;
import queueapp.service.AppointmentService;
import queueapp.service.QueueService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QueueControllerTest {

    private static final String QUEUE_ID = "queue id";
    private static final String PROVIDER_ID = "provider id";
    private static final String QUEUE_NAME = "queue name";
    private static final String DESCRIPTION = "description";
    private static final String ADDRESS = "address";
    private static final String PHONE_NUMBER = "phone number";
    private static final String APPOINTMENT_ID = "appointment id";
    private static final String CLIENT_ID = "client id";
    private static final LocalDateTime DATE_TIME = LocalDateTime.parse("2018-12-16T13:15:00.000");

    private static final String WRONG_QUEUE_ID = "wrong queue id";


    @InjectMocks
    private QueueController queueController;

    @Mock
    private QueueService queueService;
    @Mock
    private AppointmentService appointmentService;

    @Before
    public void setUp() {
        UpdateQueueRequest updateQueueRequest = buildUpdateQueueRequest();
        QueueResponse queueResponse = buildQueueResponse();
        List<ReadAppointmentResponse> appointmentResponses = buildAppointmentResponses();
        List<Range> ranges = buildRanges();
        List<String> appointmentsIds = buildAppointmentsIds();

        when(queueService.updateQueue(QUEUE_ID, updateQueueRequest))
                .thenReturn(Optional.of(queueResponse));
        when(queueService.getAppointmentsByQueueId(QUEUE_ID))
                .thenReturn(appointmentResponses);
        when(queueService.readQueueByQueueId(QUEUE_ID))
                .thenReturn(Optional.of(queueResponse));
        when(queueService.createQueue(any(CreateQueueRequest.class)))
                .thenReturn(Optional.of(QUEUE_ID));
        when(queueService.deleteQueue(QUEUE_ID))
                .thenReturn(true);

        when(queueService.updateQueue(WRONG_QUEUE_ID, updateQueueRequest))
                .thenReturn(Optional.empty());
        when(queueService.getAppointmentsByQueueId(WRONG_QUEUE_ID))
                .thenReturn(Collections.emptyList());
        when(queueService.readQueueByQueueId(WRONG_QUEUE_ID))
                .thenReturn(Optional.empty());
        when(queueService.deleteQueue(WRONG_QUEUE_ID))
                .thenReturn(false);

        when(appointmentService.createAppointments(QUEUE_ID, ranges))
                .thenReturn(appointmentsIds);
        when(appointmentService.createAppointments(WRONG_QUEUE_ID, ranges))
                .thenReturn(Collections.emptyList());
    }

    @Test
    public void createQueue() {
        //Given
        CreateQueueRequest request = buildCreateQueueRequest();

        //When
        ResponseEntity<String> response = queueController.createQueue(request);

        //Then
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(QUEUE_ID);
    }

    @Test
    public void readQueueById_Success() {
        //Given
        QueueResponse expectedResponse = buildQueueResponse();

        //When
        ResponseEntity<QueueResponse> response = queueController.readQueueById(QUEUE_ID);

        //Then
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    public void readQueueById_Fail_WrongQueueId() {
        //When
        ResponseEntity<QueueResponse> response = queueController.readQueueById(WRONG_QUEUE_ID);

        //Then
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateQueue_Success() {
        //Given
        QueueResponse expectedQueueResponse = buildQueueResponse();

        //When
        ResponseEntity<QueueResponse> response = queueController.updateQueue(QUEUE_ID, buildUpdateQueueRequest());

        //Then
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedQueueResponse);
    }

    @Test
    public void updateQueue_Fail_WrongQueueId() {
        //When
        ResponseEntity<QueueResponse> response = queueController.updateQueue(WRONG_QUEUE_ID, buildUpdateQueueRequest());

        //Then
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void deleteQueue_Success() {
        //When
        ResponseEntity<Void> response = queueController.deleteQueue(QUEUE_ID);

        //Then
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void deleteQueue_Fail_WrongQueueId() {
        //When
        ResponseEntity<Void> response = queueController.deleteQueue(WRONG_QUEUE_ID);

        //Then
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void createAppointments_Success() {
        //Given
        List<Range> ranges = buildRanges();
        List<String> expectedResponse = buildAppointmentsIds();

        //When
        ResponseEntity<List<String>> response = queueController.createAppointments(QUEUE_ID, ranges);

        //Then
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    public void createAppointments_Fail_WrongQueueId() {
        //Given
        List<Range> ranges = buildRanges();

        //When
        ResponseEntity<List<String>> response = queueController.createAppointments(WRONG_QUEUE_ID, ranges);

        //Then
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getAppointmentsByQueueId_Success() {
        //Given
        List<ReadAppointmentResponse> expectedReadAppointmentResponses = buildAppointmentResponses();

        //When
        ResponseEntity<List<ReadAppointmentResponse>> response = queueController.getAppointmentsByQueueId(QUEUE_ID);

        //Then
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedReadAppointmentResponses);
    }

    private CreateQueueRequest buildCreateQueueRequest() {
        return new CreateQueueRequest(
                PROVIDER_ID,
                QUEUE_NAME,
                DESCRIPTION,
                ADDRESS,
                PHONE_NUMBER,
                null,
                null
        );
    }

    private QueueResponse buildQueueResponse() {
        return new QueueResponse(
                QUEUE_ID,
                PROVIDER_ID,
                QUEUE_NAME,
                DESCRIPTION,
                ADDRESS,
                PHONE_NUMBER,
                false,
                0,
                null,
                null,
                null
        );
    }

    private UpdateQueueRequest buildUpdateQueueRequest() {
        UpdateQueueRequest updateQueueRequest = new UpdateQueueRequest();
        updateQueueRequest.setName(QUEUE_NAME);
        updateQueueRequest.setDescription(DESCRIPTION);
        updateQueueRequest.setAddress(ADDRESS);
        updateQueueRequest.setClosed(false);
        updateQueueRequest.setPhoneNumber(PHONE_NUMBER);

        return updateQueueRequest;
    }

    private List<ReadAppointmentResponse> buildAppointmentResponses() {
        UserResponse userResponse = buildUserResponse();

        return Collections.singletonList(new ReadAppointmentResponse(
                QUEUE_ID,
                APPOINTMENT_ID,
                userResponse,
                DATE_TIME,
                DATE_TIME,
                AppointmentStatus.CREATED
        ));
    }

    private UserResponse buildUserResponse() {
        return new UserResponse(
                CLIENT_ID,
                null,
                PHONE_NUMBER,
                CLIENT_ID,
                null,
                ADDRESS
        );
    }

    private List<Range> buildRanges() {
        Range range = new Range();
        range.setDateTimeFrom(DATE_TIME);
        range.setDateTimeTo(DATE_TIME);

        return Collections.singletonList(range);
    }

    private List<String> buildAppointmentsIds() {
        return Collections.singletonList(APPOINTMENT_ID);
    }

}