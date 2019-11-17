package com.mlestyan.validation.person.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlestyan.validation.person.configuration.property.StateReadingProperties;
import com.mlestyan.validation.person.model.domain.State;
import com.mlestyan.validation.person.model.domain.StateDictionary;
import com.mlestyan.validation.person.service.contract.StateReadingService;

@Service
@EnableConfigurationProperties(StateReadingProperties.class)
public class StateReadingServiceBean implements StateReadingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(StateReadingServiceBean.class);
	private StateReadingProperties stateReadingProperties;
	private ObjectMapper objectMapper;
	
	@Autowired
	public StateReadingServiceBean(StateReadingProperties stateReadingProperties, ObjectMapper objectMapper) {
		this.stateReadingProperties = stateReadingProperties;
		this.objectMapper = objectMapper;
	}

	/**
	 * Reads the available states from the JSON file specified in the application property file.
	 * @return the collected {@link State} objects
	 * @throws JsonParseException {@link ObjectMapper#readValue(File, Class)}
	 * @throws JsonMappingException {@link ObjectMapper#readValue(File, Class)}
	 * @throws IOException {@link ObjectMapper#readValue(File, Class)}
	 * */
	@Override
	public List<State> readStates() throws JsonParseException, JsonMappingException, IOException {
		LOGGER.info("Reading the available state details from " + stateReadingProperties.getStateJsonFilePath() + " ...");
		File statesJsonFile = new File(stateReadingProperties.getStateJsonFilePath());
		StateDictionary stateDictionary = objectMapper.readValue(statesJsonFile, StateDictionary.class);
		LOGGER.info("Available state details has been read.");
		if(stateDictionary.getStates() == null) {
			// No states were provided. Return an empty list, since it's possible that the database will be populated with states from other sources as well
			// Also print warning message
			LOGGER.warn("States JSON file (" + stateReadingProperties.getStateJsonFilePath() + ") does not contain any state definitions.");
			return Collections.emptyList();
		}
		return stateDictionary.getStates().stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
	}

}
