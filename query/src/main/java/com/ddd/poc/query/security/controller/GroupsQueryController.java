package com.ddd.poc.query.security.controller;

import com.ddd.poc.domain.api.RestUrls;
import com.ddd.poc.domain.security.dao.GroupEntityDao;
import com.ddd.poc.domain.security.dto.GroupDTO;
import com.ddd.poc.domain.security.model.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(RestUrls.GROUPS)
public class GroupsQueryController {

    private final GroupEntityDao groupEntityDao;

    @Autowired
    public GroupsQueryController(GroupEntityDao groupEntityDao) {
        this.groupEntityDao = groupEntityDao;
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    Collection<GroupDTO> getAll() {
        return groupEntityDao.findAll().stream().map(this::getGroupDTO).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    GroupDTO getSingle(@PathVariable("id") Long id) {
        return groupEntityDao.findOne(id).map(this::getGroupDTO).orElse(null);
    }

    private GroupDTO getGroupDTO(GroupEntity groupEntity) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(groupEntity.getId());
        groupDTO.setName(groupEntity.getName());
        return groupDTO;
    }
}
