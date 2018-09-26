package queueapp.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import queueapp.domain.Request;
import queueapp.service.RequestService;

@RestController
@RequiredArgsConstructor
@Api(value = "v1/request", description = "Request Controller")
public class RequestController {

    private final RequestService requestService;

    @ApiOperation(value = "Create new request", nickname = "createRequest", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class)})
    @RequestMapping(value = "/create",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<String> createRequest(@RequestBody Request request) {
        return ResponseEntity.ok(requestService.createRequest(request));
    }

    @ApiOperation(value = "Read request information", nickname = "readRequest", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{request-id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
        public ResponseEntity<Request> readRequest(@PathVariable("request-id") String requestId) {
        return requestService.readRequest(requestId)
                       .map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Update request", nickname = "updateRequest", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{request-id}",
            produces = {"application/json"},
            method = RequestMethod.PUT)
    public ResponseEntity<String> updateRequest(@PathVariable("request-id") String requestId,
                                                 @RequestBody Request request) {
        return requestService.updateRequest(requestId, request)
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Delete request", nickname = "deleteRequest", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{request-id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteRequest(@PathVariable("request-id") String requestId) {
        return requestService.deleteRequest(requestId)
                       ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
