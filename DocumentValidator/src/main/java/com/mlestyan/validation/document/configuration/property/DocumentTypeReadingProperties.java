package com.mlestyan.validation.document.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "document.application")
public class DocumentTypeReadingProperties {
	/**
	 * The path of the json file containing the document types
	 * */
	private String documentTypesJsonFilePath;
}
