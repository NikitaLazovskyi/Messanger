package com.messageservice.api;

import com.messageservice.dto.RoomDto;
import com.messageservice.dto.UserDto;
import com.messageservice.dto.group.OnCreateRoom;
import com.messageservice.dto.group.OnRenameRoom;
import com.messageservice.dto.strings.StringItem;
import com.messageservice.dto.strings.ValidateString;
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
import java.util.Set;

@Api(tags = "Room API")
@RequestMapping("/api/v1/room")
@Validated
public interface RoomApi {

    @ApiOperation("Create room")
    @ApiImplicitParams({@ApiImplicitParam(name = "roomDto", paramType = "body", required = true)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    RoomDto create(@RequestBody @Validated(OnCreateRoom.class) RoomDto roomDto);

    @ApiOperation("Create room")
    @ApiImplicitParams({@ApiImplicitParam(name = "roomId", paramType = "path", required = true)})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete-room/{roomId}")
    ResponseEntity<Void> delete(@PathVariable @Positive(message = "{id} {of_room} {positive}") Long roomId);

    @ApiOperation("Rename room")
    @ApiImplicitParams({@ApiImplicitParam(name = "roomDto", paramType = "body", required = true,
            value = "Requirements: roomDto must contain an ID and name, that will be set to founded by ID room")})
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    RoomDto rename(@RequestBody @Validated({OnRenameRoom.class}) RoomDto roomDto);

    @ApiOperation("Show all rooms")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<RoomDto> showAllRooms();

    @ApiOperation("Add member to already existed room")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomId", paramType = "query", required = true,
                    value = "ID of existed room"),
            @ApiImplicitParam(name = "username", paramType = "query", required = true,
                    value = "Username of existed user that will be added to room")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/add-member")
    ResponseEntity<Void> addMember(
            @RequestParam("roomId") @Positive(message = "{id} {of_room} {positive}") Long roomId,
            @RequestParam("username") @ValidateString(value = StringItem.USERNAME, message = "{username} {invalid}") String username
    );

    @ApiOperation("Remove member from already existed room")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomId", paramType = "query", required = true,
                    value = "ID of existed room"),
            @ApiImplicitParam(name = "username", paramType = "query", required = true,
                    value = "Username of existed user in members list related room. Will be removed from this list.")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/remove-member")
    ResponseEntity<Void> removeMember(
            @RequestParam("roomId") @Positive(message = "{id} {of_room} {positive}") Long roomId,
            @RequestParam("username") @ValidateString(value = StringItem.USERNAME, message = "{username} {invalid}") String username
    );

    @ApiOperation("Get list of all usernames that has access to room")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomId", paramType = "path", required = true,
                    value = "ID of existed room")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/members/{roomId}")
    Set<UserDto> showMembers(@PathVariable("roomId") @Positive(message = "{id} {of_room} {positive}") Long roomId);
}
