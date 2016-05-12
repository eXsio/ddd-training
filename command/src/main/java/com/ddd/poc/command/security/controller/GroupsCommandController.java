package com.ddd.poc.command.security.controller;

import com.ddd.poc.command.security.command.CreateGroupCommand;
import com.ddd.poc.command.security.command.DeleteGroupCommand;
import com.ddd.poc.command.security.command.UpdateGroupCommand;
import com.ddd.poc.command.security.service.GroupService;
import com.ddd.poc.domain.api.RestUrls;
import com.ddd.poc.domain.security.dto.GroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestUrls.GROUPS)
public class GroupsCommandController {

    private final GroupService groupService;

    @Autowired
    public GroupsCommandController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createNewGroup(@RequestBody GroupDTO groupDTO) {
        groupService.createGroup(new CreateGroupCommand(groupDTO.getName()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateGroup(@RequestBody GroupDTO groupDTO, @PathVariable("id") Long id) {
        groupDTO.setId(id);
        groupService.updateGroup(new UpdateGroupCommand(groupDTO.getId(), groupDTO.getName()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteGroup(@PathVariable("id") Long id) {
        groupService.deleteGroup(new DeleteGroupCommand(id));
    }


}
