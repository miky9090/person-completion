package com.mlestyan.validation.document.service.impl.validation;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.mlestyan.validation.document.configuration.property.DocumentValidationProperties;
import com.mlestyan.validation.document.model.domain.DocumentType;
import com.mlestyan.validation.document.service.contract.DocumentTypeService;
import com.mlestyan.validation.document.service.contract.validation.DocumentNumberValidationService;

@Service
@EnableConfigurationProperties(DocumentValidationProperties.class)
public class DocumentNumberValidationServiceBean implements DocumentNumberValidationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentImageValidationServiceBean.class);
	
	@Autowired
	private DocumentTypeService documentTypeService;
	
	@Autowired
	private DocumentValidationProperties documentValidationProperties;

	/**
	 * Validates a document number. The document type defines the validation pattern for the document number.
	 * @param documentType the type of the document, e.g. 1: id card number, 2: passport
	 * @return true if the document passes the validation criterias defined by the document type, false otherwise
	 * */
	@Override
	public boolean validateDocumentNumber(String documentType, String documentNumber) {
		LOGGER.debug("Validating document number...");
		
		if(documentType == null || documentNumber == null) {
			LOGGER.debug("Null input for validating document number.");
			return false;
		}
		
		// Find the document type from the database
		LOGGER.debug("Finding the document type from the database...");
		Optional<DocumentType> optionalInputDocumentType = this.documentTypeService.getDocumentTypeById(documentType);
		
		// If not present, that means invalid document type was given
		if(!optionalInputDocumentType.isPresent()) {
			LOGGER.debug("Document type not found in the database.");
			return false;
		}
		LOGGER.debug("Document type found in the database.");
		
		Map<String, String> documentTypePatterns = documentValidationProperties.getDocumentNumberValidationPatterns();
		
		// If present, but not in the map, check length
		if(!documentTypePatterns.containsKey(documentType)) {
			if(documentNumber.length() <= documentValidationProperties.getMaxDocumentNumberLength()) {
				return true;
			}
			LOGGER.debug("No document type validation pattern available for the provided document type and the length of the document number is invalid. Expected max: " + documentValidationProperties.getMaxDocumentNumberLength() + ", provided: " + documentNumber.length());
			return false;
		}
		
		// Check if the document number matches the criteria
		Pattern documentTypePattern = Pattern.compile(documentTypePatterns.get(documentType));
		Matcher documentTypeMatcher = documentTypePattern.matcher(documentNumber);
		boolean matches = documentTypeMatcher.matches();
		LOGGER.debug("Pattern validation for document number was successful.");
		return matches;
	}

}
