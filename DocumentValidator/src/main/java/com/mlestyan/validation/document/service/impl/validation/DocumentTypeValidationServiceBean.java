package com.mlestyan.validation.document.service.impl.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlestyan.validation.document.service.contract.DocumentTypeService;
import com.mlestyan.validation.document.service.contract.validation.DocumentTypeValidationService;

@Service
public class DocumentTypeValidationServiceBean implements DocumentTypeValidationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentImageValidationServiceBean.class);
	
	@Autowired
	private DocumentTypeService documentTypeService;

	/**
	 * Checks if the document type is valid by finding it in the database.
	 * @param documentType the document type to be validated
	 * @return true if the document type could be found in the database, false otherwise
	 * */
	@Override
	public boolean validateDocumentType(String documentType) {
		LOGGER.debug("Validating document type...");
		boolean isValidDocumentType = documentType != null && documentType.length() == 1 && this.documentTypeService.getDocumentTypeById(documentType).isPresent();
		LOGGER.debug("Validation of document type was successful.");
		return isValidDocumentType;
	}
}
