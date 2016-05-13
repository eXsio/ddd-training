package com.ddd.poc.domain.security.repository;

import com.ddd.poc.domain.security.model.UserDM;
import com.ddd.poc.domain.security.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDomainRepository {

    private final UserEntityRepository userEntityRepository;

    private final GroupEntityRepository groupEntityRepository;

    @Autowired
    public UserDomainRepository(UserEntityRepository userEntityRepository, GroupEntityRepository groupEntityRepository) {
        this.userEntityRepository = userEntityRepository;
        this.groupEntityRepository = groupEntityRepository;
    }

    public Optional<UserDM> find(Long id) {
        Optional<UserEntity> userEntity = userEntityRepository.findOne(id);
        return userEntity.map(userEntityObj -> Optional.of(new UserDM(userEntityObj, userEntityRepository, groupEntityRepository))).orElse(Optional.<UserDM>empty());
    }

    public UserDM create() {
        return new UserDM(userEntityRepository, groupEntityRepository);
    }
}
