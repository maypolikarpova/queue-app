package queueapp.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import queueapp.domain.Client;
import queueapp.service.ClientService;

@RestController
@RequiredArgsConstructor
@Api(value = "v1/client", description = "Client Controller")
public class ClientController {

    private final ClientService clientService;

    @ApiOperation(value = "Create new client", nickname = "createClient", response = String.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Created", response = String.class)})
    @RequestMapping(value = "/create",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<String> createClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.createClient(client));
    }

    @ApiOperation(value = "Read client information", nickname = "readClient", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Client.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{client-id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Client> readClient(@PathVariable("client-id") String clientId) {
        return clientService.readClient(clientId)
                       .map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Update client", nickname = "updateClient", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{client-id}",
            produces = {"application/json"},
            method = RequestMethod.PUT)
    public ResponseEntity<Void> updateClient(@PathVariable("client-id") String clientId,
                                             @RequestBody Client client) {
        return clientService.updateClient(clientId, client)
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Delete client", nickname = "deleteClient", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{client-id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteClient(@PathVariable("client-id") String clientId) {
        return clientService.deleteClient(clientId)
                       ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
