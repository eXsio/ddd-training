package com.ddd.poc.domain.security.model;

import com.ddd.poc.domain.core.model.BaseAggregate;
import com.ddd.poc.domain.security.repository.GroupEntityRepository;
import org.springframework.transaction.annotation.Transactional;

public class GroupDM extends BaseAggregate<GroupEntity> {

    private final GroupEntityRepository groupEntityRepository;

    public GroupDM(GroupEntityRepository groupEntityRepository) {
        this(new GroupEntity(), groupEntityRepository);
    }

    public GroupDM(GroupEntity entity, GroupEntityRepository groupEntityRepository) {
        super(entity);
        this.groupEntityRepository = groupEntityRepository;
    }

    @Transactional
    public void save(String name) {
        entity.setName(name);
        groupEntityRepository.save(entity);
    }

    @Transactional
    public void delete() {
        groupEntityRepository.delete(entity);
    }
}
