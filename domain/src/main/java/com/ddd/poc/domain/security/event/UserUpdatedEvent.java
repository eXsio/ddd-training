package com.ddd.poc.domain.security.event;

import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.security.dto.UserDTO;

public class UserUpdatedEvent implements DomainEvent {

    private final UserDTO userDTO;

    public UserUpdatedEvent(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}
