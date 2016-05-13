package com.ddd.poc.domain.security.repository;

import com.ddd.poc.domain.security.model.GroupDM;
import com.ddd.poc.domain.security.model.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupDomainRepository {

    private final GroupEntityRepository groupEntityRepository;

    @Autowired
    public GroupDomainRepository(GroupEntityRepository groupEntityRepository) {
        this.groupEntityRepository = groupEntityRepository;
    }

    public Optional<GroupDM> find(Long id) {
        Optional<GroupEntity> groupEntity = groupEntityRepository.findOne(id);
        return groupEntity.map(groupEntityObj -> Optional.of(new GroupDM(groupEntityObj, groupEntityRepository))).orElse(Optional.<GroupDM>empty());
    }

    public GroupDM create() {
        return new GroupDM(groupEntityRepository);
    }
}
