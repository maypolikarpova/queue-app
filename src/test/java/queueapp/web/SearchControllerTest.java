package queueapp.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.user.UserResponse;
import queueapp.service.SearchService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchControllerTest {

    private static final String QUERY = "query";
    private static final String LOCATION = "location";
    private static final String NAME = "name";

    private static final String WRONG_QUERY = "wrong query";
    private static final String WRONG_LOCATION = "wrong location";
    private static final String WRONG_NAME = "wrong name";

    @InjectMocks
    private SearchController searchController;

    @Mock
    private SearchService searchService;

    @Before
    public void setUp() {
        List<QueueResponse> queueResponses = buildQueueResponses();
        List<UserResponse> userResponses = buildUserResponses();

        when(searchService.searchQueueByQueryAndLocation(QUERY,LOCATION))
                .thenReturn(queueResponses);
        when(searchService.searchQueueByQueryAndLocation(WRONG_QUERY,WRONG_LOCATION))
                .thenReturn(Collections.emptyList());
        when(searchService.searchProviderByNameAndLocation(NAME,LOCATION))
                .thenReturn(userResponses);
        when(searchService.searchProviderByNameAndLocation(WRONG_NAME,WRONG_LOCATION))
                .thenReturn(Collections.emptyList());
    }

    @Test
    public void searchQueueByQueryAndLocation_Success() {
        //Given
        List<QueueResponse> expectedResponse = buildQueueResponses();

        //When
        ResponseEntity<List<QueueResponse>> response = searchController.searchQueueByQueryAndLocation(QUERY, LOCATION);

        //Then
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    public void searchQueueByQueryAndLocation_Fail() {
        //When
        ResponseEntity<List<QueueResponse>> response = searchController.searchQueueByQueryAndLocation(WRONG_QUERY, WRONG_LOCATION);

        //Then
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void searchProviderByNameAndLocation_Success() {
        //Given
        List<UserResponse> expectedResponse = buildUserResponses();

        //When
        ResponseEntity<List<UserResponse>> response = searchController.searchProviderByNameAndLocation(NAME, LOCATION);

        //Then
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    public void searchProviderByNameAndLocation_Fail() {
        //When
        ResponseEntity<List<UserResponse>> response = searchController.searchProviderByNameAndLocation(WRONG_NAME, WRONG_LOCATION);

        //Then
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }

    private List<QueueResponse> buildQueueResponses() {
        QueueResponse queueResponse = new QueueResponse(
                QUERY,
                QUERY,
                QUERY,
                QUERY,
                LOCATION,
                QUERY,
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
                NAME,
                NAME,
                NAME,
                NAME,
                null,
                LOCATION
        );

        return Collections.singletonList(userResponse);
    }
}