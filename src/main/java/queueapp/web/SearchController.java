package queueapp.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.user.UserResponse;
import queueapp.service.SearchService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(value = "v1/search", description = "Search Controller")
public class SearchController {

    private final SearchService searchService;

    @ApiOperation(value = "Search queue by query and location", nickname = "searchQueueByQueryAndLocation", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "/queue/{query}/{location}", //query params look in description tags name of the queue
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<QueueResponse>> searchQueueByQueryAndLocation(@PathVariable("query") String query,
                                                                  @PathVariable("location") String location) {
        List<QueueResponse> responses = searchService.searchQueueByQueryAndLocation(query, location);

        return CollectionUtils.isEmpty(responses)
                       ? ResponseEntity.notFound().build()
                       : ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "Search provider by name and location", nickname = "searchProviderByNameAndLocation", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "/provider/{name}/{location}", // provider = person with at least one queue !
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<UserResponse>> searchProviderByNameAndLocation(@PathVariable("name") String name,
                                                                @PathVariable("location") String location) {
        List<UserResponse> responses = searchService.searchProviderByNameAndLocation(name, location);
        return CollectionUtils.isEmpty(responses)
                       ? ResponseEntity.notFound().build()
                       : ResponseEntity.ok(responses);
    }
}
