package com.mlestyan.validation.person.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "person.application.document.api")
public class DocumentApiProperties {
	/**
	 * The url of the document api
	 * */
	private String url;
	
	/**
	 * The path of the document complete rest service
	 * */
	private String documentCompletePath;
}
