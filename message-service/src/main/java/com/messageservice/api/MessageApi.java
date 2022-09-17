package com.messageservice.api;

import com.messageservice.dto.MessageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Message API")
@RequestMapping("/message")
public interface MessageApi {

    @ApiOperation("Send message")
    @ApiImplicitParams({@ApiImplicitParam(name = "message", paramType = "body", required = true,
            value = "In Service layer fields UID and timestamp will be generated")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping
    ResponseEntity<Void> sendMessage(@RequestBody MessageDto message);

    @ApiOperation("Show messages in full format (all fields) ")
    @ApiImplicitParams({@ApiImplicitParam(name = "roomId", paramType = "path", required = true)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/room/{roomId}/plain")
    List<MessageDto> showMessages(@PathVariable Long roomId);

    @ApiOperation("Show messages in short format (without UID and room)")
    @ApiImplicitParams({@ApiImplicitParam(name = "roomId", paramType = "path", required = true)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/room/{roomId}/short")
    List<MessageDto> showMessageShort(@PathVariable Long roomId);
}
