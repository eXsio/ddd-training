package com.ddd.poc.domain.security.model;

import com.ddd.poc.domain.security.dao.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupRepository {

    private final GroupDao groupDao;

    @Autowired
    public GroupRepository(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public Optional<Group> find(Long id) {
        Optional<GroupEntity> groupEntity = groupDao.findOne(id);
        return groupEntity.map(groupEntityObj -> Optional.of(new Group(groupEntityObj))).orElse(Optional.<Group>empty());
    }

    public Group create() {
        return new Group();
    }

    public Group save(Group groupDM) {
        return new Group(groupDao.save(groupDM.getEntity()));
    }

    public void delete(Group groupDM) {
        groupDao.delete(groupDM.getEntity());
    }
}
