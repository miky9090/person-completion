package com.mlestyan.validation.person.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlestyan.validation.person.model.domain.State;
import com.mlestyan.validation.person.repository.StateRepository;
import com.mlestyan.validation.person.service.contract.StateService;

@Service
public class StateServiceBean implements StateService {
	private StateRepository stateRepository;

	@Autowired
	public StateServiceBean(StateRepository stateRepository) {
		this.stateRepository = stateRepository;
	}
	
	@Override
	public Iterable<State> saveStates(Iterable<State> states) {
		return this.stateRepository.saveAll(states);
	}

	@Override
	public Optional<State> getStateById(String id) {
		return this.stateRepository.findById(id);
	}
}
