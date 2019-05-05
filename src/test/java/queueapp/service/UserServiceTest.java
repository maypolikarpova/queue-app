package queueapp.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import queueapp.domain.appointment.Appointment;
import queueapp.domain.appointment.AppointmentStatus;
import queueapp.domain.appointment.ReadAppointmentByClientIdResponse;
import queueapp.domain.queue.Queue;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.user.*;
import queueapp.repository.AppointmentRepository;
import queueapp.repository.QueueRepository;
import queueapp.repository.UserRepository;
import queueapp.service.mapper.AppointmentMapper;
import queueapp.service.mapper.QueueMapper;
import queueapp.service.mapper.UserMapper;

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
public class UserServiceTest {

    private static final String USER_ID = "user id";
    private static final String WRONG_USER_ID = "wrong user id";

    private static final String PASSWORD = "password";
    private static final String WRONG_PASSWORD = "wrong password";

    private static final String PROVIDER_ID = "provider id";
    private static final String WRONG_PROVIDER_ID = "wrong provider id";

    private static final String CLIENT_ID = "client id";
    private static final String WRONG_CLIENT_ID = "wrong client id";

    private static final String ADDRESS = "location";
    private static final String PHONE_NUMBER = "phone number";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String QUEUE_ID = "queue id";
    private static final String APPOINTMENT_ID = "appointment id";
    private static final String DESCRIPTION = "description";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private QueueRepository queueRepository;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private QueueMapper queueMapper;
    @Mock
    private AppointmentMapper appointmentMapper;

    @Before
    public void setUp() {
        User user = buildUser();
        UserResponse userResponse = buildUserResponse();
        CreateUserRequest createUserRequest = buildCreateUserRequest();

        List<Queue> queues = buildQueueList();
        List<QueueResponse> queueResponses = buildQueueResponseList();

        List<Appointment> appointments = buildAppointments();
        List<ReadAppointmentByClientIdResponse> responses = buildAppointmentList();

        when(userMapper.mapToUser(createUserRequest))
                .thenReturn(Optional.of(user));
        when(userMapper.mapToUserResponse(user))
                .thenReturn(Optional.of(userResponse));

        when(userRepository.save(any(User.class)))
                .thenReturn(user);
        when(userRepository.findOne(USER_ID))
                .thenReturn(user);
        when(userRepository.findOne(WRONG_USER_ID))
                .thenReturn(null);
        when(userRepository.exists(USER_ID))
                .thenReturn(true);
        when(userRepository.exists(WRONG_USER_ID))
                .thenReturn(false);
        doNothing().when(userRepository).delete(anyString());
        when(userRepository.findByEmail(EMAIL))
                .thenReturn(Optional.of(user));

        when(queueMapper.mapToQueueResponses(queues))
                .thenReturn(queueResponses);
        when(queueMapper.mapToQueueResponses(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        when(queueRepository.findByProviderId(PROVIDER_ID))
                .thenReturn(queues);

        when(appointmentRepository.findByClientId(CLIENT_ID))
                .thenReturn(appointments);

        when(appointmentMapper.mapToReadAppointmentByClientIdResponseList(appointments))
                .thenReturn(responses);
    }

    @Test
    public void createUser() {
        //Given
        CreateUserRequest request = buildCreateUserRequest();
        UserResponse expectedResult = buildUserResponse();

        //When
        Optional<UserResponse> response = userService.createUser(request);

        //Then
        assertThat(response).isNotEqualTo(Optional.empty());
        assertThat(response.get()).isEqualTo(expectedResult);
    }

    @Test
    public void readUser_Success() {
        //Given
        UserResponse expectedResult = buildUserResponse();

        //When
        Optional<UserResponse> response = userService.readUser(USER_ID);

        //Then
        assertThat(response).isNotEqualTo(Optional.empty());
        assertThat(response.get()).isEqualTo(expectedResult);
    }

    @Test
    public void readUser_Fail_WrongUserId() {
        //Given
        UserResponse expectedResult = buildUserResponse();

        //When
        Optional<UserResponse> response = userService.readUser(WRONG_USER_ID);

        //Then
        assertThat(response).isEqualTo(Optional.empty());
    }

    @Test
    public void updateUser_Success() {
        //Given
        UserResponse expectedResult = buildUserResponse();
        UpdateUserRequest request = buildUpdateUserRequest();

        //When
        Optional<UserResponse> response = userService.updateUser(USER_ID, request);

        //Then
        assertThat(response).isNotEqualTo(Optional.empty());
        assertThat(response.get()).isEqualTo(expectedResult);
    }

    @Test
    public void updateUser_Fail_WrongUserId() {
        //Given
        UpdateUserRequest request = buildUpdateUserRequest();

        //When
        Optional<UserResponse> response = userService.updateUser(WRONG_USER_ID, request);

        //Then
        assertThat(response).isEqualTo(Optional.empty());
    }

    @Test
    public void updateUserPassword_Success() {
        //When
        boolean response = userService.updateUserPassword(USER_ID, PASSWORD, PASSWORD);

        //Then
        assertThat(response).isTrue();
    }

    @Test
    public void updateUserPassword_Fail_WrongUserId() {
        //When
        boolean response = userService.updateUserPassword(WRONG_USER_ID, PASSWORD, PASSWORD);

        //Then
        assertThat(response).isFalse();
    }

    @Test
    public void updateUserPassword_Fail_WrongPassword() {
        //When
        boolean response = userService.updateUserPassword(WRONG_USER_ID, WRONG_PASSWORD, PASSWORD);

        //Then
        assertThat(response).isFalse();
    }

    @Test
    public void deleteUser_Success() {
        //When
        boolean response = userService.deleteUser(USER_ID);

        //Then
        assertThat(response).isTrue();
    }

    @Test
    public void deleteUser_Fail_WrongUserId() {
        //When
        boolean response = userService.deleteUser(WRONG_USER_ID);

        //Then
        assertThat(response).isFalse();
    }

    @Test
    public void identifyUser_Success() {
        //Given
        LogInUserRequest request = buildLogInUserRequest();
        UserResponse expectedResponse = buildUserResponse();

        //When
        Optional<UserResponse> response = userService.identifyUser(request);

        //Then
        assertThat(response).isNotEqualTo(Optional.empty());
        assertThat(response.get()).isEqualTo(expectedResponse);
    }

    @Test
    public void identifyUser_Fail_WrongRequest() {
        //Given
        LogInUserRequest request = buildWrongLogInUserRequest();

        //When
        Optional<UserResponse> response = userService.identifyUser(request);

        //Then
        assertThat(response).isEqualTo(Optional.empty());
    }

    @Test
    public void readQueueByProviderId_Success() {
        //Given
        List<QueueResponse> expectedResponse = buildQueueResponseList();

        //When
        List<QueueResponse> response = userService.readQueueByProviderId(PROVIDER_ID);

        //Then
        assertThat(response).isNotEmpty();
        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void readQueueByProviderId_Fail_WrongProviderId() {
        //When
        List<QueueResponse> response = userService.readQueueByProviderId(WRONG_PROVIDER_ID);

        //Then
        assertThat(response).isEmpty();
    }

    @Test
    public void readQueuesByClientId_Success() {
        //Given
        List<ReadAppointmentByClientIdResponse> expectedResponse = buildAppointmentList();

        //When
        List<ReadAppointmentByClientIdResponse> response = userService.readQueuesByClientId(CLIENT_ID);

        //Then
        assertThat(response).isNotEmpty();
        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void readQueuesByClientId_Fail_WrongClientId() {
        //When
        List<ReadAppointmentByClientIdResponse> response = userService.readQueuesByClientId(WRONG_CLIENT_ID);

        //Then
        assertThat(response).isEmpty();
    }

    private CreateUserRequest buildCreateUserRequest() {
        return new CreateUserRequest(
                EMAIL,
                PASSWORD,
                PHONE_NUMBER,
                NAME,
                ADDRESS,
                null
        );

    }

    private UserResponse buildUserResponse() {
        return new UserResponse(
                USER_ID,
                EMAIL,
                PHONE_NUMBER,
                USER_ID,
                null,
                ADDRESS
        );
    }

    private UpdateUserRequest buildUpdateUserRequest() {
        return new UpdateUserRequest(
               EMAIL,
               NAME,
               PHONE_NUMBER,
               null,
               ADDRESS
        );
    }

    private LogInUserRequest buildLogInUserRequest() {
        return new LogInUserRequest(
                EMAIL,
                PASSWORD
        );
    }

    private LogInUserRequest buildWrongLogInUserRequest() {
        return new LogInUserRequest(
                EMAIL,
                WRONG_PASSWORD
        );
    }

    private List<QueueResponse> buildQueueResponseList() {
        return Collections.singletonList(buildQueueResponse());

    }

    private List<ReadAppointmentByClientIdResponse> buildAppointmentList() {
        return Collections.singletonList( new ReadAppointmentByClientIdResponse(
                APPOINTMENT_ID,
                buildQueueResponse(),
                LocalDateTime.parse("2018-12-16T12:15:00.000"),
                LocalDateTime.parse("2018-12-16T12:15:00.000"),
                AppointmentStatus.APPROVED
        ));
    }

    private QueueResponse buildQueueResponse() {
        return new QueueResponse(
                QUEUE_ID,
                PROVIDER_ID,
                NAME,
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

    private List<Queue> buildQueueList() {
        Queue queue = new Queue();

        queue.setQueueId(QUEUE_ID);
        queue.setAddress(ADDRESS);
        queue.setProviderId(PROVIDER_ID);
        queue.setPhoneNumber(PHONE_NUMBER);
        queue.setDescription(DESCRIPTION);
        queue.setName(QUEUE_ID);
        queue.setClosed(false);

        return Collections.singletonList(queue);
    }

    private User buildUser(){
        User user = new User();
        user.setPhoneNumber(PHONE_NUMBER);
        user.setUserId(USER_ID);
        user.setAddress(ADDRESS);
        user.setEmail(EMAIL);
        user.setName(NAME);
        user.setPassword(PASSWORD);
        return user;
    }

    private List<Appointment> buildAppointments(){
        Appointment appointment = new Appointment();

        appointment.setQueueId(QUEUE_ID);
        appointment.setAppointmentId(APPOINTMENT_ID);
        appointment.setClientId(CLIENT_ID);
        appointment.setDateTimeFrom(LocalDateTime.parse("2018-12-16T12:15:00.000"));
        appointment.setDateTimeTo(LocalDateTime.parse("2018-12-16T13:15:00.000"));
        appointment.setStatus(AppointmentStatus.CREATED);

        return Collections.singletonList(appointment);
    }
}