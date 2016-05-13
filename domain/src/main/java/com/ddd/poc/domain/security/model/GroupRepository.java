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

    public Optional<GroupDM> find(Long id) {
        Optional<GroupEntity> groupEntity = groupDao.findOne(id);
        return groupEntity.map(groupEntityObj -> Optional.of(new GroupDM(groupEntityObj))).orElse(Optional.<GroupDM>empty());
    }

    public GroupDM create() {
        return new GroupDM();
    }

    public GroupDM save(GroupDM groupDM) {
        return new GroupDM(groupDao.save(groupDM.getEntity()));
    }

    public void delete(GroupDM groupDM) {
        groupDao.delete(groupDM.getEntity());
    }
}
