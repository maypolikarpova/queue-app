package queueapp.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import queueapp.domain.appointment.Appointment;
import queueapp.domain.appointment.AppointmentStatus;
import queueapp.domain.appointment.ReadAppointmentResponse;
import queueapp.domain.queue.CreateQueueRequest;
import queueapp.domain.queue.Queue;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.queue.UpdateQueueRequest;
import queueapp.domain.user.UserResponse;
import queueapp.repository.AppointmentRepository;
import queueapp.repository.QueueRepository;
import queueapp.service.mapper.AppointmentMapper;
import queueapp.service.mapper.QueueMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QueueServiceTest {

    private static final String QUEUE_ID = "queue id";
    private static final String PROVIDER_ID = "provider id";
    private static final String QUEUE_NAME = "queue name";
    private static final String DESCRIPTION = "description";
    private static final String ADDRESS = "address";
    private static final String PHONE_NUMBER = "phone number";
    private static final String APPOINTMENT_ID = "appointment id";
    private static final String CLIENT_ID = "client id";

    private static final String WRONG_QUEUE_ID = "wrong queue id";

    @InjectMocks
    private QueueService queueService;

    @Mock
    private QueueRepository queueRepository;
    @Mock
    private QueueMapper queueMapper;
    @Mock
    private AppointmentMapper appointmentMapper;
    @Mock
    private AppointmentRepository appointmentRepository;

    @Before
    public void setUp() {
        Queue expectedQueue = buildQueue();
        QueueResponse expectedQueueResponse = buildQueueResponse();
        List<ReadAppointmentResponse> readAppointmentResponses = buildAppointmentResponses();
        List<Appointment> appointments = buildAppointments();

        when(queueMapper.mapToQueue(any(CreateQueueRequest.class)))
                .thenReturn(Optional.of(expectedQueue));
        when(queueMapper.mapToQueueResponse(any(Queue.class)))
                .thenReturn(Optional.of(expectedQueueResponse));

        when(queueRepository.save(any(Queue.class)))
                .thenReturn(expectedQueue);
        when(queueRepository.findOne(QUEUE_ID))
                .thenReturn(expectedQueue);
        when(queueRepository.exists(QUEUE_ID))
                .thenReturn(true);
        doNothing().when(queueRepository).delete(anyString());

        when(queueRepository.findOne(WRONG_QUEUE_ID))
                .thenReturn(null);
        when(queueRepository.exists(WRONG_QUEUE_ID))
                .thenReturn(false);

        when(appointmentMapper.mapToReadAppointmentResponseList(appointments))
                .thenReturn(readAppointmentResponses);
        when(appointmentMapper.mapToReadAppointmentResponseList(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        when(appointmentRepository.findByQueueId(WRONG_QUEUE_ID))
                .thenReturn(Collections.emptyList());
        when(appointmentRepository.findByQueueId(QUEUE_ID))
                .thenReturn(appointments);

    }

    @Test
    public void createQueue_Success() {
        //When
        Optional<String> response = queueService.createQueue(buildCreateQueueRequest());

        //Then
        assertThat(response).isNotEqualTo(Optional.empty());
        assertThat(response.get()).isEqualTo(QUEUE_ID);
    }

    @Test
    public void readQueueByQueueId_Success() {
        //Given
        QueueResponse expectedQueueResponse = buildQueueResponse();

        //When
        Optional<QueueResponse> response = queueService.readQueueByQueueId(QUEUE_ID);

        //Then
        assertThat(response).isNotEqualTo(Optional.empty());
        assertThat(response.get()).isEqualTo(expectedQueueResponse);
    }

    @Test
    public void readQueueByQueueId_Fail_WrongQueueId() {
        //When
        Optional<QueueResponse> response = queueService.readQueueByQueueId(WRONG_QUEUE_ID);

        //Then
        assertThat(response).isEqualTo(Optional.empty());
    }

    @Test
    public void updateQueue_Success() {
        //Given
        QueueResponse expectedQueueResponse = buildQueueResponse();

        //When
        Optional<QueueResponse> response = queueService.updateQueue(QUEUE_ID, buildUpdateQueueRequest());

        //Then
        assertThat(response).isNotEqualTo(Optional.empty());
        assertThat(response.get()).isEqualTo(expectedQueueResponse);
    }

    @Test
    public void updateQueue_Fail_WrongQueueId() {
        //When
        Optional<QueueResponse> response = queueService.updateQueue(WRONG_QUEUE_ID, buildUpdateQueueRequest());

        //Then
        assertThat(response).isEqualTo(Optional.empty());
    }

    @Test
    public void getAppointmentsByQueueId_Success() {
        //Given
        List<ReadAppointmentResponse> expectedReadAppointmentResponses = buildAppointmentResponses();

        //When
        List<ReadAppointmentResponse> response = queueService.getAppointmentsByQueueId(QUEUE_ID);

        //Then
        assertThat(response).isNotEqualTo(Collections.emptyList());
        assertThat(response).isEqualTo(expectedReadAppointmentResponses);
    }

    @Test
    public void getAppointmentsByQueueId_Fail_WrongQueueId() {
        //When
        List<ReadAppointmentResponse> response = queueService.getAppointmentsByQueueId(WRONG_QUEUE_ID);

        //Then
        assertThat(response).isEqualTo(Collections.emptyList());
    }

    @Test
    public void deleteQueue_Success() {
        //When
        boolean response = queueService.deleteQueue(QUEUE_ID);

        //Then
        assertThat(response).isTrue();
    }

    @Test
    public void deleteQueue_Fail_WrongQueueId() {
        //When
        boolean response = queueService.deleteQueue(WRONG_QUEUE_ID);

        //Then
        assertThat(response).isFalse();
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

    private Queue buildQueue() {
        Queue queue = new Queue();
        queue.setQueueId(QUEUE_ID);
        queue.setClosed(false);
        queue.setAddress(ADDRESS);
        queue.setProviderId(PROVIDER_ID);
        queue.setPhoneNumber(PHONE_NUMBER);
        queue.setDescription(DESCRIPTION);

        return queue;
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
        updateQueueRequest.setPhoneNumber(PHONE_NUMBER);
        updateQueueRequest.setClosed(false);

        return updateQueueRequest;
    }

    private List<ReadAppointmentResponse> buildAppointmentResponses() {
        UserResponse userResponse = new UserResponse(
                CLIENT_ID,
                null,
                PHONE_NUMBER,
                CLIENT_ID,
                null,
                ADDRESS
        );

        return Collections.singletonList(new ReadAppointmentResponse(
                QUEUE_ID,
                APPOINTMENT_ID,
                userResponse,
                LocalDateTime.parse("2018-12-16T12:15:00.000"),
                LocalDateTime.parse("2018-12-16T13:15:00.000"),
                AppointmentStatus.CREATED
        ));
    }

    private List<Appointment> buildAppointments(){
        Appointment appointment = new Appointment();
        appointment.setStatus(AppointmentStatus.CREATED);
        appointment.setQueueId(QUEUE_ID);
        appointment.setAppointmentId(APPOINTMENT_ID);
        appointment.setClientId(CLIENT_ID);
        appointment.setDateTimeFrom(LocalDateTime.parse("2018-12-16T12:15:00.000"));
        appointment.setDateTimeTo(LocalDateTime.parse("2018-12-16T13:15:00.000"));

        return Collections.singletonList(appointment);
    }
}