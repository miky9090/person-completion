package com.mlestyan.validation.person.service.contract;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mlestyan.validation.person.model.domain.State;

public interface StateReadingService {
	public List<State> readStates() throws JsonParseException, JsonMappingException, IOException;
}
