package com.ddd.poc.query.core.controller;

import com.ddd.poc.domain.api.RestUrls;
import com.ddd.poc.domain.core.dao.CommandEntityDao;
import com.ddd.poc.domain.core.dto.CommandDTO;
import com.ddd.poc.domain.core.dto.EventDTO;
import com.ddd.poc.domain.core.model.CommandEntity;
import com.ddd.poc.domain.core.model.EventEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(RestUrls.COMMANDS)
public class CommandsQueryController {

    private final CommandEntityDao eventEntityDao;

    @Autowired
    public CommandsQueryController(CommandEntityDao eventEntityDao) {
        this.eventEntityDao = eventEntityDao;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<CommandDTO> getCommands() {
        return eventEntityDao.findAllOrderByCreatedAt().stream().map(this::getCommandDTO).collect(Collectors.toList());
    }

    protected CommandDTO getCommandDTO(CommandEntity entity) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.setId(entity.getId());
        commandDTO.setCreatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(entity.getCreatedAt().getTime()), ZoneId.systemDefault()));
        commandDTO.setCommandClass(entity.getCommandClass());
        commandDTO.setData(entity.getCommandData());
        commandDTO.setEvents(entity.getEvents().stream().map(this::getEventDTO).collect(Collectors.toList()));
        return commandDTO;
    }

    protected EventDTO getEventDTO(EventEntity entity) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(entity.getId());
        eventDTO.setCreatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(entity.getCreatedAt().getTime()), ZoneId.systemDefault()));
        eventDTO.setEventClass(entity.getEventClass());
        eventDTO.setData(entity.getEventData());
        return eventDTO;
    }
}
