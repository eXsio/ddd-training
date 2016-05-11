package com.ddd.poc.domain.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "groups")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    private Collection<UserEntity> users;

    public GroupEntity() {
    }

    public GroupEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public Collection<UserEntity> getUsers() {
        return users;
    }

}
