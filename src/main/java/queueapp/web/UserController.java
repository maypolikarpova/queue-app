package queueapp.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import queueapp.domain.user.LogInUserRequest;
import queueapp.domain.user.UpdatePasswordRequest;
import queueapp.domain.user.User;
import queueapp.service.UserService;

@RestController
@RequiredArgsConstructor
@Api(value = "v1/user", description = "User Controller")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Sign up new user", nickname = "signUpUser", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = User.class),
            @ApiResponse(code = 400, message = "Bad request", response = String.class)})
    @RequestMapping(value = "/signup",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<User> signUpUser(@RequestBody User user) {
        return userService.createUser(user)
                       .map(ResponseEntity::ok)
                       .orElseGet(ResponseEntity.badRequest()::build);
    }

    @ApiOperation(value = "Read user information", nickname = "readUser", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = User.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{user-id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<User> readUser(@PathVariable("user-id") String userId) {
        return userService.readUser(userId)
                       .map(ResponseEntity::ok)
                       .orElseGet(ResponseEntity.notFound()::build);
    }

    @ApiOperation(value = "Update user information", nickname = "updateUserInfo", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class)})
    @RequestMapping(value = "/{user-id}",
            produces = {"application/json"},
            method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUserInfo(@PathVariable("user-id") String userId,
                                             @RequestBody User user) {
        return userService.updateUserInfo(userId, user)
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Update user photo", nickname = "updateUser", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{user-id}/photo",
            produces = {"application/json"},
            method = RequestMethod.PUT)
    //TODO implement
    public ResponseEntity<Void> updateUserPhoto(@PathVariable("user-id") String userId,
                                             @RequestBody byte[] photo) {
        return userService.updateUserPhoto(userId, photo)
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Update user password", nickname = "updateUser", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{user-id}/photo",
            produces = {"application/json"},
            method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUserPassword(@PathVariable("user-id") String userId,
                                                  @RequestBody UpdatePasswordRequest passwords) {
        return userService.updateUserPassword(userId, passwords.getOldPassword(), passwords.getNewPassword())
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Delete user", nickname = "deleteUser", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{user-id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable("user-id") String userId) {
        return userService.deleteUser(userId)
                       ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Log a user in", nickname = "logInUser", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = User.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/login",
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
    @RequestMapping(value = "/logout",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    //TODO implement
    public ResponseEntity<Void> logOutUser() {
        return ResponseEntity.ok().build();
    }
}
