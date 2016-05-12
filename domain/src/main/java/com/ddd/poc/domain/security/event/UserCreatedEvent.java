package com.ddd.poc.domain.security.event;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.security.dto.UserDTO;

public class UserCreatedEvent extends DomainEvent {

    private final UserDTO userDTO;

    public UserCreatedEvent(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}
