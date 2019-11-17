package com.mlestyan.validation.person.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "person.validation")
public class PersonValidationProperties {
	/**
	 * The maximum length a person can have
	 * */
	private int maxNameLength;
	
	/**
	 * The min. age a person cna be
	 * */
	private int minAge;
	
	/**
	 * The min. age a person can be
	 * */
	private int maxAge;
	
	/**
	 * The exact number of characters that nationality code has to have
	 * */
	private int expectedNationalityCharacterCount;
}
