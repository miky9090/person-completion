package com.mlestyan.validation.person.repository;

import org.springframework.data.repository.CrudRepository;

import com.mlestyan.validation.person.model.domain.State;

public interface StateRepository extends CrudRepository<State, String> {}
