package com.ddd.poc.command.security.controller;

import com.ddd.poc.command.security.command.CreateUserCommand;
import com.ddd.poc.command.security.command.DeleteUserCommand;
import com.ddd.poc.command.security.command.JoinGroupCommand;
import com.ddd.poc.command.security.command.LeaveGroupCommand;
import com.ddd.poc.command.security.command.UpdateUserCommand;
import com.ddd.poc.domain.api.RestUrls;
import com.ddd.poc.domain.core.annotation.Asynchronous;
import com.ddd.poc.domain.core.annotation.Synchronous;
import com.ddd.poc.domain.core.service.CommandBus;
import com.ddd.poc.domain.security.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestUrls.USERS)
public class UsersCommandController {

    private final CommandBus commandBus;

    @Autowired
    public UsersCommandController(@Asynchronous CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createNewUser(@RequestBody UserDTO userDTO) {
        commandBus.publishCommand(new CreateUserCommand(userDTO));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") Long id) {
        userDTO.setId(id);
        commandBus.publishCommand(new UpdateUserCommand(userDTO));
    }

    @RequestMapping(value = "/{id}/join/{groupId}", method = RequestMethod.POST)
    public void joinGroup(@PathVariable("id") Long id, @PathVariable("groupId") Long groupId) {
        commandBus.publishCommand(new JoinGroupCommand(groupId, id));
    }

    @RequestMapping(value = "/{id}/leave/{groupId}", method = RequestMethod.POST)
    public void leaveGroup(@PathVariable("id") Long id, @PathVariable("groupId") Long groupId) {
        commandBus.publishCommand(new LeaveGroupCommand(groupId, id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") Long id) {
        commandBus.publishCommand(new DeleteUserCommand(id));
    }


}
