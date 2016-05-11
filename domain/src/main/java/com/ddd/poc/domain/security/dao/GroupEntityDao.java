package com.ddd.poc.domain.security.dao;

import com.ddd.poc.domain.security.model.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupEntityDao extends JpaRepository<GroupEntity, Long> {

    Optional<GroupEntity> findByName(String name);
}
