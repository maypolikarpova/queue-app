package queueapp.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import queueapp.domain.appointment.ReadAppointmentByClientIdResponse;
import queueapp.domain.queue.Queue;
import queueapp.domain.queue.QueueResponse;
import queueapp.domain.user.*;
import queueapp.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(value = "v1/", description = "User Controller")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Sign up new user", nickname = "signUpUser", response = UserResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = UserResponse.class),
            @ApiResponse(code = 400, message = "Bad request")})
    @RequestMapping(value = "v1/user/signup",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<UserResponse> signUpUser(@RequestBody CreateUserRequest user) {
        return userService.createUser(user)
                       .map(ResponseEntity::ok)
                       .orElseGet(ResponseEntity.badRequest()::build);
    }

    @ApiOperation(value = "Read user information", nickname = "readUser", response = UserResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserResponse.class),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "v1/user/{user-id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<UserResponse> readUser(@PathVariable("user-id") String userId) {
        return userService.readUser(userId)
                       .map(ResponseEntity::ok)
                       .orElseGet(ResponseEntity.notFound()::build);
    }

    @ApiOperation(value = "Update user information", nickname = "updateUser", response = UserResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserResponse.class),
            @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "v1/user/{user-id}",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<UserResponse> updateUserInfo(@PathVariable("user-id") String userId,
                                                       @RequestBody UpdateUserRequest request) {
        return userService.updateUser(userId, request)
                       .map(ResponseEntity::ok)
                       .orElseGet(ResponseEntity.notFound()::build);
    }

    @ApiOperation(value = "Update user password", nickname = "updateUserPassword", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "v1/user/{user-id}/password",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<Void> updateUserPassword(@PathVariable("user-id") String userId,
                                                   @RequestBody UpdatePasswordRequest passwords) {
        return userService.updateUserPassword(userId, passwords.getOldPassword(), passwords.getNewPassword())
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Delete user", nickname = "deleteUser")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "v1/user/{user-id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("user-id") String userId) {
        return userService.deleteUser(userId)
                       ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Log a user in", nickname = "logInUser", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = User.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "v1/user/login",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<User> logInUser(@RequestBody LogInUserRequest logInUserRequest) {
        return userService.identifyUser(logInUserRequest)
                       .map(ResponseEntity::ok)
                       .orElseGet(ResponseEntity.notFound()::build);
    }

    @ApiOperation(value = "Log out a user", nickname = "logOutUser", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "v1/user/logout",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    //TODO implement
    public ResponseEntity<Void> logOutUser() {
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Read Queue information by provider id", nickname = "readQueueByProviderId", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Queue.class),
            @ApiResponse(code = 404, message = "Not Found", response = List.class)})
    @RequestMapping(value = "v1/user/{provider-id}/queues",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<QueueResponse>> readQueuesByProviderId(@PathVariable("provider-id") String queueId) {
        List<QueueResponse> responses = userService.readQueueByProviderId(queueId);
        return CollectionUtils.isEmpty(responses)
                       ? ResponseEntity.notFound().build()
                       : ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "Read Queue information by provider id", nickname = "readQueueByProviderId", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Queue.class),
            @ApiResponse(code = 404, message = "Not Found", response = List.class)})
    @RequestMapping(value = "v1/user/{client-id}/appointments",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<ReadAppointmentByClientIdResponse>> readQueuesByClientId(@PathVariable("client-id") String clientId) {
        List<ReadAppointmentByClientIdResponse> responses = userService.readQueuesByClientId(clientId);
        return CollectionUtils.isEmpty(responses)
                       ? ResponseEntity.notFound().build()
                       : ResponseEntity.ok(responses);
    }
}
