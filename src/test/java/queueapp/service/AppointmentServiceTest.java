package queueapp.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import queueapp.domain.appointment.Appointment;
import queueapp.domain.appointment.AppointmentStatus;
import queueapp.domain.queue.Range;
import queueapp.repository.AppointmentRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentServiceTest {

    private static final String QUEUE_ID = "queue id";
    private static final String APPOINTMENT_ID = "appointment id";
    private static final String CLIENT_ID = "client id";

    private static final String WRONG_QUEUE_ID = "wrong queue id";
    private static final String WRONG_APPOINTMENT_ID = "wrong appointment id";

    private static final LocalDateTime APPOINTMENT_DATE_TIME_FROM = LocalDateTime.parse("2019-10-10T10:00:00");
    private static final LocalDateTime APPOINTMENT_DATE_TIME_TO = APPOINTMENT_DATE_TIME_FROM.plusHours(2);
    private static final int EXPECTED_AMOUNT = 1;

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Before
    public void setUp() {
        Appointment createdAppointment = buildAppointment(AppointmentStatus.CREATED);
        List<Appointment> appointments = buildAppointments();

        when(appointmentRepository.save(any(Appointment.class)))
                .thenReturn(createdAppointment);

        when(appointmentRepository.findOne(APPOINTMENT_ID))
                .thenReturn(createdAppointment);
        when(appointmentRepository.findOne(WRONG_APPOINTMENT_ID))
                .thenReturn(null);

        when(appointmentRepository.exists(APPOINTMENT_ID))
                .thenReturn(true);
        when(appointmentRepository.exists(WRONG_APPOINTMENT_ID))
                .thenReturn(false);

        doNothing().when(appointmentRepository).delete(anyString());

        when(appointmentRepository.findByQueueIdAndStatus(QUEUE_ID, AppointmentStatus.APPROVED))
                .thenReturn(appointments);
        when(appointmentRepository.findByQueueIdAndStatus(WRONG_QUEUE_ID, AppointmentStatus.APPROVED))
                .thenReturn(Collections.emptyList());
        when(appointmentRepository.findByQueueIdAndStatus(QUEUE_ID, AppointmentStatus.CREATED))
                .thenReturn(appointments);
    }

    @Test
    public void createAppointments_Success() {
        //Given
        List<Range> expectedRanges = buildRanges();
        List<String> expectedAppointments = buildAppointmentsIds();

        //When
        List<String> actualAppointments = appointmentService.createAppointments(QUEUE_ID, expectedRanges);

        //Then
        assertThat(actualAppointments).isNotEqualTo(Collections.emptyList());
        assertThat(actualAppointments).isEqualTo(expectedAppointments);
    }

    @Test
    public void requestAppointmentFromClient_Success() {
        //When
        boolean actualResponse = appointmentService.requestAppointmentFromClient(APPOINTMENT_ID, CLIENT_ID);

        //Then
        assertThat(actualResponse).isTrue();
    }

    @Test
    public void requestAppointmentFromClient_Fail_WrongAppointmentId() {
        //When
        boolean actualResponse = appointmentService.requestAppointmentFromClient(WRONG_APPOINTMENT_ID, CLIENT_ID);

        //Then
        assertThat(actualResponse).isFalse();
    }

    @Test
    public void updateAppointmentStatus_Success() {
        //When
        boolean actualResponse = appointmentService.updateAppointmentStatus(APPOINTMENT_ID, AppointmentStatus.APPROVED);

        //Then
        assertThat(actualResponse).isTrue();
    }

    @Test
    public void updateAppointmentStatus_Fail_WrongAppointmentId() {
        //When
        boolean actualResponse = appointmentService.updateAppointmentStatus(WRONG_APPOINTMENT_ID, AppointmentStatus.APPROVED);

        //Then
        assertThat(actualResponse).isFalse();
    }

    @Test
    public void deleteAppointment_Success() {
        //When
        boolean actualResponse = appointmentService.deleteAppointment(APPOINTMENT_ID);

        //Then
        assertThat(actualResponse).isTrue();
    }

    @Test
    public void deleteAppointment_Fail_WrongAppointmentId() {
        //When
        boolean actualResponse = appointmentService.deleteAppointment(WRONG_APPOINTMENT_ID);

        //Then
        assertThat(actualResponse).isFalse();
    }

    @Test
    public void getFutureAppointmentsAmount_Success() {
        //When
        int actualAmount = appointmentService.getFutureAppointmentsAmount(QUEUE_ID);

        //Then
        assertThat(actualAmount).isEqualTo(EXPECTED_AMOUNT);
    }

    @Test
    public void getFutureAppointmentsAmount_Fail_WrongQueueId() {
        //When
        int actualAmount = appointmentService.getFutureAppointmentsAmount(WRONG_QUEUE_ID);

        //Then
        assertThat(actualAmount).isNotEqualTo(EXPECTED_AMOUNT);
    }

    @Test
    public void getNextApprovedAppointmentDate_Success() {
        //When
        LocalDateTime actualDateTime = appointmentService.getNextApprovedAppointmentDate(QUEUE_ID);

        //Then
        assertThat(actualDateTime).isNotNull();
        assertThat(actualDateTime).isEqualByComparingTo(APPOINTMENT_DATE_TIME_FROM);
    }

    @Test
    public void getNextApprovedAppointmentDate_Fail_WrongQueueId() {
        //When
        LocalDateTime actualDateTime = appointmentService.getNextApprovedAppointmentDate(WRONG_QUEUE_ID);

        //Then
        assertThat(actualDateTime).isNull();
    }

    @Test
    public void getNextAvailableAppointmentDate_Success() {
        //When
        LocalDateTime actualDateTime = appointmentService.getNextAvailableAppointmentDate(QUEUE_ID);

        //Then
        assertThat(actualDateTime).isNotNull();
        assertThat(actualDateTime).isEqualByComparingTo(APPOINTMENT_DATE_TIME_FROM);
    }

    @Test
    public void getNextAvailableAppointmentDate_Fail_WrongQueueId() {
        //When
        LocalDateTime actualDateTime = appointmentService.getNextAvailableAppointmentDate(WRONG_QUEUE_ID);

        //Then
        assertThat(actualDateTime).isNull();
    }

    private Appointment buildAppointment(AppointmentStatus appointmentStatus) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(APPOINTMENT_ID);
        appointment.setDateTimeFrom(APPOINTMENT_DATE_TIME_FROM);
        appointment.setDateTimeTo(APPOINTMENT_DATE_TIME_TO);
        appointment.setStatus(appointmentStatus);
        appointment.setQueueId(QUEUE_ID);

        return appointment;
    }

    private List<Appointment> buildAppointments() {
        Appointment appointment = buildAppointment(AppointmentStatus.APPROVED);
        return Collections.singletonList(appointment);
    }

    private List<Range> buildRanges() {
        Range range = new Range();
        range.setDateTimeFrom(APPOINTMENT_DATE_TIME_FROM);
        range.setDateTimeTo(APPOINTMENT_DATE_TIME_TO);

        return Collections.singletonList(range);
    }

    private List<String> buildAppointmentsIds() {
        return Collections.singletonList(APPOINTMENT_ID);
    }

}