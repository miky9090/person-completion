package com.mlestyan.validation.person.model.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StateDictionary {
	@JsonProperty("dictname")
	private String dictname;
	@JsonProperty("rows")
	private List<State> states;
}
