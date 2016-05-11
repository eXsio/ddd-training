package com.ddd.poc.command.controller;

import com.ddd.poc.command.event.UserCreatedEvent;
import com.ddd.poc.command.event.UserDeletedEvent;
import com.ddd.poc.command.event.UserUpdatedEvent;
import com.ddd.poc.domain.api.RestUrls;
import com.ddd.poc.domain.core.service.EventBus;
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

    private final EventBus eventBus;

    @Autowired
    public UsersCommandController(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createNewUser(@RequestBody UserDTO userDTO) {
        eventBus.publishEvent(new UserCreatedEvent(userDTO));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") Long id) {
        userDTO.setId(id);
        eventBus.publishEvent(new UserUpdatedEvent(userDTO));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") Long id) {
        eventBus.publishEvent(new UserDeletedEvent(id));
    }


}
