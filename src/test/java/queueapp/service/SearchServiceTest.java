package queueapp.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import queueapp.domain.queue.Queue;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.user.User;
import queueapp.domain.user.UserResponse;
import queueapp.repository.QueueRepository;
import queueapp.repository.UserRepository;
import queueapp.service.mapper.QueueMapper;
import queueapp.service.mapper.UserMapper;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceTest {

    private static final String QUERY = "query";
    private static final String LOCATION = "location";
    private static final String NAME = "name";

    private static final String WRONG_QUERY = "wrong query";
    private static final String WRONG_LOCATION = "wrong location";
    private static final String WRONG_NAME = "wrong name";

    private static final String PROVIDER_ID = "provider id";
    private static final String QUEUE_ID = "queue id";
    private static final String DESCRIPTION = "query";
    private static final String ADDRESS = "location";
    private static final String PHONE_NUMBER = "phone number";
    private static final String USER_ID = "user id";
    private static final String EMAIL = "email";

    @InjectMocks
    private SearchService searchService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private QueueRepository queueRepository;
    @Mock
    private QueueMapper queueMapper;
    @Mock
    private UserMapper userMapper;

    @Before
    public void setUp() {
        List<QueueResponse> queueResponses = buildQueueResponses();
        List<UserResponse> userResponses = buildUserResponses();
        List<Queue> queues = buildQueues();
        List<User> users = buildUsers();

        when(queueMapper.mapToQueueResponses(queues))
                .thenReturn(queueResponses);
        when(userMapper.mapToUserResponses(users))
                .thenReturn(userResponses);
        when(queueRepository.findAll())
                .thenReturn(queues);
        when(userRepository.findAll())
                .thenReturn(users);
    }

    @Test
    public void searchQueueByQueryAndLocation_Success() {
        //Given
        List<QueueResponse> expectedResponse = buildQueueResponses();

        //When
        List<QueueResponse> response = searchService.searchQueueByQueryAndLocation(QUERY, LOCATION);

        //Then
        assertThat(response).isNotEmpty();
        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void searchQueueByQueryAndLocation_Fail() {
        //When
        List<QueueResponse> response = searchService.searchQueueByQueryAndLocation(WRONG_QUERY, WRONG_LOCATION);

        //Then
        assertThat(response).isEmpty();
    }


    @Test
    public void searchProviderByNameAndLocation_Success() {
        //Given
        List<UserResponse> expectedResponse = buildUserResponses();

        //When
        List<UserResponse> response = searchService.searchProviderByNameAndLocation(NAME, LOCATION);

        //Then
        assertThat(response).isNotEmpty();
        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void searchProviderByNameAndLocation_Fail() {
        //When
        List<UserResponse> response = searchService.searchProviderByNameAndLocation(WRONG_NAME, WRONG_LOCATION);

        //Then
        assertThat(response).isEmpty();
    }

    private List<QueueResponse> buildQueueResponses() {
        QueueResponse queueResponse = new QueueResponse(
                QUEUE_ID,
                PROVIDER_ID,
                QUEUE_ID,
                DESCRIPTION,
                ADDRESS,
                PHONE_NUMBER,
                false,
                0,
                null,
                null,
                null
        );

        return Collections.singletonList(queueResponse);
    }

    private List<UserResponse> buildUserResponses() {
        UserResponse userResponse = new UserResponse(
                USER_ID,
                EMAIL,
                PHONE_NUMBER,
                USER_ID,
                null,
                ADDRESS
        );

        return Collections.singletonList(userResponse);
    }

    private List<Queue> buildQueues() {
        Queue queue = new Queue();

        queue.setQueueId(QUEUE_ID);
        queue.setClosed(false);
        queue.setAddress(ADDRESS);
        queue.setProviderId(PROVIDER_ID);
        queue.setPhoneNumber(PHONE_NUMBER);
        queue.setDescription(DESCRIPTION);
        queue.setName(QUEUE_ID);

        return Collections.singletonList(queue);
    }

    private List<User> buildUsers(){
        User user = new User();
        user.setPhoneNumber(PHONE_NUMBER);
        user.setUserId(USER_ID);
        user.setAddress(ADDRESS);
        user.setEmail(EMAIL);
        user.setName(NAME);
        user.setPassword(USER_ID);

        return Collections.singletonList(user);
    }
}