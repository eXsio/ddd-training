package com.ddd.poc.domain.core.dao;


import java.util.List;
import java.util.Optional;

public interface DomainDao<T> {

    Optional<T> findOne(Long id);

    List<T> findAll();

    <S extends T> S save(S user);

    void delete(T user);
}
