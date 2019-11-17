package com.mlestyan.validation.person.service.contract;

import java.util.Optional;

import com.mlestyan.validation.person.model.domain.State;

public interface StateService {
	public Optional<State> getStateById(String id);
	public Iterable<State> saveStates(Iterable<State> states);
}
