package com.ddd.poc.domain.security.model;

import com.ddd.poc.domain.security.dao.GroupEntityDao;
import org.springframework.transaction.annotation.Transactional;

public class GroupDM {

    private final GroupEntity entity;

    private final GroupEntityDao groupEntityDao;

    public GroupDM(GroupEntityDao groupEntityDao) {
        this(new GroupEntity(), groupEntityDao);
    }

    public GroupDM(GroupEntity entity, GroupEntityDao groupEntityDao) {
        this.entity = entity;
        this.groupEntityDao = groupEntityDao;
    }

    @Transactional
    public void save(String name) {
        entity.setName(name);
        groupEntityDao.save(entity);
    }

    @Transactional
    public void delete() {
        groupEntityDao.delete(entity);
    }
}
