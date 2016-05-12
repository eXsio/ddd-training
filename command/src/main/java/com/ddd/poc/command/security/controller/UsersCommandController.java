package com.ddd.poc.command.security.controller;

import com.ddd.poc.command.security.command.CreateUserCommand;
import com.ddd.poc.command.security.command.DeleteUserCommand;
import com.ddd.poc.command.security.command.UpdateUserCommand;
import com.ddd.poc.command.security.service.UserService;
import com.ddd.poc.domain.api.RestUrls;
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

    private final UserService userService;

    @Autowired
    public UsersCommandController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createNewUser(@RequestBody UserDTO userDTO) {
        userService.createUser(new CreateUserCommand(userDTO));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") Long id) {
        userDTO.setId(id);
        userService.updateUser(new UpdateUserCommand(userDTO));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(new DeleteUserCommand(id));
    }


}
