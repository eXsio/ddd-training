package com.ddd.poc.domain.core.dao;


import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    Optional<T> findOne(Long id);

    List<T> findAll();

    <S extends T> T save(T entity);

    void delete(T entity);
}
