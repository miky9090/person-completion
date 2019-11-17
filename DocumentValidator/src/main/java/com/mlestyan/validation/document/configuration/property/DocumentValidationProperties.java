package com.mlestyan.validation.document.configuration.property;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "document.validation")
public class DocumentValidationProperties {
	/**
	 * The enumeration of the available image formats.
	 * This can be used in the configuration to avoid typos.
	 * */
	public enum SupportedImageFormat {
		JPG, JPEG;
	}
	
	/**
	 * The patterns to be used when validating document numbers.<br>
	 * Key: document type<br>
	 * Value: validation pattern<br>
	 * */
	private Map<String, String> documentNumberValidationPatterns;
	
	/**
	 * The maximum length of document number.<br>
	 * Applied <b>only</b> on document numbers whose document type is valid (can be found in the database), but no pattern is provided in the <i>document-number-validation-patterns</i> property.
	 * */
	private int maxDocumentNumberLength;
	
	/**
	 * The supported image formats of document images.
	 * */
	private SupportedImageFormat[] supportedImageFormats;
	
	/**
	 * The expected width of the document image.
	 * */
	private int imageWidth;
	
	/**
	 * The expected height of the document image.
	 * */
	private int imageHeight;
}
