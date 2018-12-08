package queueapp.web;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.user.UserResponse;
import queueapp.service.SearchService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(value = "v1/", description = "Search Controller")
public class SearchController {

    private final SearchService searchService;

    @ApiOperation(value = "Search queue by query and location", nickname = "searchQueueByQueryAndLocation", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "v1/search/queue",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<QueueResponse>> searchQueueByQueryAndLocation(@RequestParam(name = "query", required = false) String query,
                                                                             @RequestParam(name = "location", required = false) String location) {
        List<QueueResponse> responses = searchService.searchQueueByQueryAndLocation(query, location);

        return CollectionUtils.isEmpty(responses)
                       ? ResponseEntity.notFound().build()
                       : ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "Search provider by name and location", nickname = "searchProviderByNameAndLocation", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "v1/search/provider",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<UserResponse>> searchProviderByNameAndLocation(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "location", required = false) String location) {
        List<UserResponse> responses = searchService.searchProviderByNameAndLocation(name, location);
        return CollectionUtils.isEmpty(responses)
                       ? ResponseEntity.notFound().build()
                       : ResponseEntity.ok(responses);
    }
}
