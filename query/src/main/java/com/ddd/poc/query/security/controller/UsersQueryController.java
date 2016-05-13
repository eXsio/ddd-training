package com.ddd.poc.query.security.controller;

import com.ddd.poc.domain.api.RestUrls;
import com.ddd.poc.domain.security.repository.UserEntityRepository;
import com.ddd.poc.domain.security.dto.UserDTO;
import com.ddd.poc.domain.security.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(RestUrls.USERS)
public class UsersQueryController {

    private final UserEntityRepository userEntityDao;

    @Autowired
    public UsersQueryController(UserEntityRepository userEntityDao) {
        this.userEntityDao = userEntityDao;
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    Collection<UserDTO> getAll() {
        return userEntityDao.findAll().stream().map(this::getUserDTO).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    UserDTO getSingle(@PathVariable("id") Long id) {
        return userEntityDao.findOne(id).map(this::getUserDTO).orElse(null);
    }

    private UserDTO getUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setActive(userEntity.isActive());
        userDTO.setId(userEntity.getId());
        userDTO.setGroups(userEntity.getGroups().stream().map(groupEntity -> groupEntity.getName()).collect(Collectors.toList()));
        return userDTO;
    }
}
