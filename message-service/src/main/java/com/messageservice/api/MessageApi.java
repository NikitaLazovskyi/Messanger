package com.messageservice.api;

import com.messageservice.dto.MessageDto;
import com.messageservice.dto.UserDto;
import com.messageservice.dto.group.OnSendMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@Api(tags = "Message API")
@RequestMapping("/api/v1/message")
@Validated
public interface MessageApi {

    @ApiOperation("Send message")
    @ApiImplicitParams({@ApiImplicitParam(name = "message", paramType = "body", required = true,
            value = "In Service layer fields UID and timestamp will be generated")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping
    ResponseEntity<Void> sendMessage(@RequestBody @Validated(OnSendMessage.class) MessageDto message);

    @ApiOperation("Show messages in full format (all fields) ")
    @ApiImplicitParams({@ApiImplicitParam(name = "roomId", paramType = "path", required = true)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/room/{roomId}/plain")
    List<MessageDto> showMessages(@PathVariable @Positive(message = "{id} {of_room} {positive}") Long roomId);

    @ApiOperation("Show messages in short format (without UID and room)")
    @ApiImplicitParams({@ApiImplicitParam(name = "roomId", paramType = "path", required = true)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/room/{roomId}/short")
    List<MessageDto> showMessageShort(@PathVariable @Positive(message = "{id} {of_room} {positive}") Long roomId);

    @ApiOperation("Show all usernames")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    List<UserDto> showAllUsernames();
}
