package com.mrmachine.pizza.persistence.repository;


import org.springframework.data.repository.CrudRepository;

import com.mrmachine.pizza.persistence.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, String> {
}
