package queueapp.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import queueapp.domain.Provider;
import queueapp.service.ProviderService;

@RestController
@RequiredArgsConstructor
@Api(value = "v1/provider", description = "Provider Controller")
public class ProviderController {

    private final ProviderService providerService;

    @ApiOperation(value = "Create new provider", nickname = "createProvider", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class)})
    @RequestMapping(value = "/create",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<String> createProvider(@RequestBody Provider provider) {
        return ResponseEntity.ok(providerService.createProvider(provider));
    }

    @ApiOperation(value = "Read provider information", nickname = "readProvider", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{provider-id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Provider> readProvider(@PathVariable("provider-id") String providerId) {
        return providerService.readProvider(providerId)
                       .map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Update provider", nickname = "updateProvider", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{provider-id}",
            produces = {"application/json"},
            method = RequestMethod.PUT)
    public ResponseEntity<String> updateProvider(@PathVariable("provider-id") String providerId,
                                                 @RequestBody Provider provider) {
        return providerService.updateProvider(providerId, provider)
                       ? ResponseEntity.ok().build()
                       : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Delete provider", nickname = "deleteProvider", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)})
    @RequestMapping(value = "/{provider-id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteProvider(@PathVariable("provider-id") String providerId) {
        return providerService.deleteProvider(providerId)
                       ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
