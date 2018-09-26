package queueapp.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import queueapp.domain.Registry;
import queueapp.service.RegistryService;

@RestController
@RequiredArgsConstructor
@Api(value = "v1/registry", description = "Reqistry Controller")
public class RegistryController {

    private final RegistryService registryService;

    @ApiOperation(value = "Create new registry", nickname = "createRegistry", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class)})
    @RequestMapping(value = "/create",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<String> createRegistry(@RequestBody Registry registry) {
        return ResponseEntity.ok(registryService.createRegistry(registry));
    }

    @ApiOperation(value = "Read registry information", nickname = "readRegistry", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{registry-id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Registry> readRegistry(@PathVariable("registry-id") String registryId) {
        return registryService.readRegistry(registryId)
                       .map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Update registry", nickname = "updateRegistry", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{registry-id}",
            produces = {"application/json"},
            method = RequestMethod.PUT)
    public ResponseEntity<String> updateRegistry(@PathVariable("registry-id") String registryId,
                                                 @RequestBody Registry registry) {
        return registryService.updateRegistry(registryId, registry)
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Delete registry", nickname = "deleteRegistry", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{registry-id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteRegistry(@PathVariable("registry-id") String registryId) {
        return registryService.deleteRegistry(registryId)
                       ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
