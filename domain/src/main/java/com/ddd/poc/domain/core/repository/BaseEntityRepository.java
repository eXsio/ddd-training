package com.ddd.poc.domain.core.repository;


import java.util.List;
import java.util.Optional;

public interface BaseEntityRepository<T> {

    Optional<T> findOne(Long id);

    List<T> findAll();

    <S extends T> S save(S user);

    void delete(T user);
}
