package com.mlestyan.validation.person.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "person.application.state")
public class StateReadingProperties {
	
	/**
	 * The path of the json file containing the states
	 * */
	private String stateJsonFilePath;
}
