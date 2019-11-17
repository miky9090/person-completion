package com.mlestyan.validation.person.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mlestyan.validation.person.model.domain.State;
import com.mlestyan.validation.person.repository.StateRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("State service bean tests")
class StateServiceBeanTest {
	
	@InjectMocks
	private StateServiceBean stateServiceBean;
	
	@Mock
	private StateRepository stateRepository;

	@Test
	void testSaveStatesNull() {
		Iterable<State> states = null;
		Mockito.when(stateRepository.saveAll(ArgumentMatchers.isNull())).thenThrow(IllegalArgumentException.class);
		
		assertThrows(IllegalArgumentException.class, () -> {
			stateServiceBean.saveStates(states);
		});
	}
	
	@Test
	void testSaveStatesContainsElements() {
		State state1 = Mockito.mock(State.class);
		State state2 = Mockito.mock(State.class);
		List<State> states = Arrays.asList(state1, state2);
		
		Mockito.when(stateRepository.saveAll(states)).thenReturn(states);
		
		Iterable<State> resultStates = stateServiceBean.saveStates(states);
		
		assertEquals(states, resultStates);
	}

	@Test
	void testGetStateByIdNull() {
		String id = null;
		Mockito.when(stateRepository.findById(ArgumentMatchers.isNull())).thenThrow(IllegalArgumentException.class);
		
		assertThrows(IllegalArgumentException.class, () -> {
			stateServiceBean.getStateById(id);
		});
	}
	
	@Test
	void testGetStateByIdValidId() {
		String id = "HUN";
		Optional<State> optionalState = Optional.of(new State());
		Mockito.when(stateRepository.findById(id)).thenReturn(optionalState);
		
		Optional<State> resultState = stateServiceBean.getStateById(id);
		
		assertEquals(optionalState, resultState);
	}
	
	@Test
	void testGetStateByIdInvalidId() {
		String id = "HUN";
		Mockito.when(stateRepository.findById(id)).thenReturn(Optional.empty());
		
		Optional<State> resultState = stateServiceBean.getStateById(id);
		
		assertEquals(Optional.empty(), resultState);
	}

}
