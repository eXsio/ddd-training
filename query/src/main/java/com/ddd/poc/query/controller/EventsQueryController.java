package com.ddd.poc.query.controller;

import com.ddd.poc.domain.api.RestUrls;
import com.ddd.poc.domain.core.dao.EventEntityDao;
import com.ddd.poc.domain.core.model.EventEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(RestUrls.EVENTS)
public class EventsQueryController {

    private final EventEntityDao eventEntityDao;

    @Autowired
    public EventsQueryController(EventEntityDao eventEntityDao) {
        this.eventEntityDao = eventEntityDao;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<EventEntity> getEvents() {
        return eventEntityDao.findAllOrderByCreatedAt();
    }
}
