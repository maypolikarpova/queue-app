package queueapp.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import queueapp.domain.Client;

@RestController
@Api(value = "v1", description = "Client Controller")
public class ClientController {

    @ApiOperation(value = "Create new client", nickname = "createClient", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class)})
    @RequestMapping(value = "/create",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<String> createClient(@RequestBody Client client) {
        return ResponseEntity.ok().build();
    }

}
