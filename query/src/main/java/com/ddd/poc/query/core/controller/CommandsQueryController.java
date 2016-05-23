package com.ddd.poc.query.core.controller;

import com.ddd.poc.domain.api.RestUrls;
import com.ddd.poc.domain.core.dao.CommandDao;
import com.ddd.poc.domain.core.dto.CommandDTO;
import com.ddd.poc.domain.core.dto.EventDTO;
import com.ddd.poc.domain.core.model.CommandEntity;
import com.ddd.poc.domain.core.model.EventEntity;
import com.ddd.poc.query.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(RestUrls.COMMANDS)
public class CommandsQueryController {

    private final CommandDao eventEntityDao;

    @Autowired
    public CommandsQueryController(CommandDao eventEntityDao) {
        this.eventEntityDao = eventEntityDao;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<CommandDTO> getCommands() {
        return eventEntityDao.findAllOrderByCreatedAt().stream().map(this::getCommandDTO).collect(Collectors.toList());
    }

    protected CommandDTO getCommandDTO(CommandEntity entity) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.setId(entity.getId());
        commandDTO.setCreatedAt(DateUtil.format(entity.getCreatedAt()));
        commandDTO.setCommandClass(entity.getCommandClass());
        commandDTO.setData(entity.getCommandData());
        commandDTO.setEvents(entity.getEvents().stream().map(this::getEventDTO).collect(Collectors.toList()));
        commandDTO.setUuid(entity.getUuid());
        if (entity.getSubCommands() != null) {
            commandDTO.setSubCommands(entity.getSubCommands().stream().map(this::getCommandDTO).collect(Collectors.toList()));
        }
        return commandDTO;
    }

    protected EventDTO getEventDTO(EventEntity entity) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(entity.getId());
        eventDTO.setCreatedAt(DateUtil.format(entity.getCreatedAt()));
        eventDTO.setEventClass(entity.getEventClass());
        eventDTO.setData(entity.getEventData());
        eventDTO.setUuid(entity.getUuid());
        return eventDTO;
    }
}
