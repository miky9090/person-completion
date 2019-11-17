package com.mlestyan.validation.person.service.impl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlestyan.validation.person.configuration.property.StateReadingProperties;
import com.mlestyan.validation.person.model.domain.State;
import com.mlestyan.validation.person.model.domain.StateDictionary;

@ExtendWith(MockitoExtension.class)
@DisplayName("State reading service bean tests")
class StateReadingServiceBeanTest {
	
	@InjectMocks
	private StateReadingServiceBean stateReadingService;
	
	@Mock
	private StateReadingProperties stateReadingProperties;
	
	@Mock
	private ObjectMapper objectMapper;

	@Test
	void testReadStatesNullPath() {
		Mockito.when(stateReadingProperties.getStateJsonFilePath()).thenReturn(null);
		
		assertThrows(NullPointerException.class, () -> {
			stateReadingService.readStates();
		});
	}

	@Test
	void testReadStatesFileNotFound() {
		
		Mockito.when(stateReadingProperties.getStateJsonFilePath()).thenReturn("test-file-path");
		
		assertThrows(NullPointerException.class, () -> {
			stateReadingService.readStates();
		});
	}
	
	@Test
	void testReadStatesFileNullStatesInFile() {
		
		Mockito.when(stateReadingProperties.getStateJsonFilePath()).thenReturn("test-file-path");
		
		StateDictionary mockedStateDictionary = Mockito.mock(StateDictionary.class);
		Mockito.when(mockedStateDictionary.getStates()).thenReturn(null);
		
		
		try {
			Mockito.when(objectMapper.readValue(ArgumentMatchers.any(File.class), ArgumentMatchers.eq(StateDictionary.class))).thenReturn(mockedStateDictionary);
			
			List<State> result = stateReadingService.readStates();
			
			assertNotNull(result);
			assertTrue(result.isEmpty());
			
		} 
		catch (IOException e) {
			fail("This shouldn't happen...");
		}
	}
	
	@Test
	void testReadStatesFileContainsNullStatesInFile() {
		
		Mockito.when(stateReadingProperties.getStateJsonFilePath()).thenReturn("test-file-path");
		
		State state1 = Mockito.mock(State.class);
		State state2 = null;
		
		StateDictionary mockedStateDictionary = Mockito.mock(StateDictionary.class);
		Mockito.when(mockedStateDictionary.getStates()).thenReturn(Arrays.asList(state1, state2));
		
		
		try {
			Mockito.when(objectMapper.readValue(ArgumentMatchers.any(File.class), ArgumentMatchers.eq(StateDictionary.class))).thenReturn(mockedStateDictionary);
			
			List<State> result = stateReadingService.readStates();
			
			assertNotNull(result);
			assertTrue(result.size() == 1);
			assertEquals(state1, result.get(0));
		} 
		catch (IOException e) {
			fail("This shouldn't happen...");
		}
	}
	
	@Test
	void testReadStatesFileHasOnlyValidStates() {
		
		Mockito.when(stateReadingProperties.getStateJsonFilePath()).thenReturn("test-file-path");
		
		State state1 = Mockito.mock(State.class);
		State state2 = Mockito.mock(State.class);
		
		StateDictionary mockedStateDictionary = Mockito.mock(StateDictionary.class);
		Mockito.when(mockedStateDictionary.getStates()).thenReturn(Arrays.asList(state1, state2));
		
		
		try {
			Mockito.when(objectMapper.readValue(ArgumentMatchers.any(File.class), ArgumentMatchers.eq(StateDictionary.class))).thenReturn(mockedStateDictionary);
			
			List<State> result = stateReadingService.readStates();
			
			assertNotNull(result);
			assertTrue(result.size() == 2);
			assertEquals(state1, result.get(0));
			assertEquals(state2, result.get(1));
		} 
		catch (IOException e) {
			fail("This shouldn't happen...");
		}
	}
}
