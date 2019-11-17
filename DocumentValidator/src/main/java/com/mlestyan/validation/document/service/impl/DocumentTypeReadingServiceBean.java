package com.mlestyan.validation.document.service.impl;

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
import com.mlestyan.validation.document.configuration.property.DocumentTypeReadingProperties;
import com.mlestyan.validation.document.model.domain.DocumentType;
import com.mlestyan.validation.document.model.domain.DocumentTypeDictionary;
import com.mlestyan.validation.document.service.contract.DocumentTypeReadingService;

@Service
@EnableConfigurationProperties(DocumentTypeReadingProperties.class)
public class DocumentTypeReadingServiceBean implements DocumentTypeReadingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentTypeReadingServiceBean.class);
	private DocumentTypeReadingProperties documentTypeReadingProperties;
	private ObjectMapper objectMapper;
	
	@Autowired
	public DocumentTypeReadingServiceBean(DocumentTypeReadingProperties documentTypeReadingProperties, ObjectMapper objectMapper) {
		this.documentTypeReadingProperties = documentTypeReadingProperties;
		this.objectMapper = objectMapper;
	}

	/**
	 * Reads the available document types from the JSON file whose path is specified in the application property file as <i>document-types-json-file-path</i>.
	 * @return the list of {@link DocumentType} objects read from the JSON file
	 * @throws JsonParseException {@link ObjectMapper#readValue(File, Class)}
	 * @throws JsonMappingException {@link ObjectMapper#readValue(File, Class)}
	 * @throws IOException {@link ObjectMapper#readValue(File, Class)}
	 * */
	@Override
	public List<DocumentType> readDocumentTypes() throws JsonParseException, JsonMappingException, IOException {
		LOGGER.info("Reading document types from " + documentTypeReadingProperties.getDocumentTypesJsonFilePath() + " ...");
		File documentTypeJsonFile = new File(documentTypeReadingProperties.getDocumentTypesJsonFilePath());
		DocumentTypeDictionary documentTypeDictionary = objectMapper.readValue(documentTypeJsonFile, DocumentTypeDictionary.class);
		if(documentTypeDictionary.getDocumentTypes() == null) {
			LOGGER.warn("Document types JSON file (" + documentTypeReadingProperties.getDocumentTypesJsonFilePath() + ") does not contain any document type definitions.");
			return Collections.emptyList();
		}
		LOGGER.info("Document types has been read.");
		return documentTypeDictionary.getDocumentTypes().stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
	}

}
