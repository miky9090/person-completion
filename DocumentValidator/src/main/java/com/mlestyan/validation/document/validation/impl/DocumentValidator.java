package com.mlestyan.validation.document.validation.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;

import com.mlestyan.validation.document.model.domain.DocumentType;
import com.mlestyan.validation.document.model.domain.OkmanyDTO;
import com.mlestyan.validation.document.service.contract.DocumentTypeService;
import com.mlestyan.validation.document.validation.constraint.ValidDocument;

/**
 * @deprecated Using Bean Validation is better choice if we only want to return the error messages only without any other contents, 
 * because we cannot reconstruct the {@link ConstraintViolation}-s on the other end and merge it with the existing violations.
 * */
public class DocumentValidator implements ConstraintValidator<ValidDocument, OkmanyDTO> {
	@Autowired
	private DocumentTypeService documentTypeService;
	private static final int MAX_DOCUMENT_NUMBER_LENGTH = 10;
	private static final Map<String, String> DOCUMENT_TYPE_PATTERNS = new HashMap<>(3);
	static {
		DOCUMENT_TYPE_PATTERNS.put("1", "[0-9]{6}[A-Z]{2}");
		DOCUMENT_TYPE_PATTERNS.put("2", "[A-Z]{2}[0-9]{7}");
		DOCUMENT_TYPE_PATTERNS.put("3", "[A-Z]{2}[0-9]{6}");
	}

	@Override
	public boolean isValid(OkmanyDTO value, ConstraintValidatorContext context) {
		if(value.getOkmTipus() == null || value.getOkmanySzam() == null) {
			return true;
		}
		
		Optional<DocumentType> optionalInputDocumentType = this.documentTypeService.getDocumentTypeById(value.getOkmTipus());
		// If not present, that means invalid document type was given, so other validator will handle it.
		if(!optionalInputDocumentType.isPresent()) {
			return true;
		}
		
		// If present, but not in the map, check length
		if(!DOCUMENT_TYPE_PATTERNS.containsKey(value.getOkmTipus()) && value.getOkmanySzam().length() > MAX_DOCUMENT_NUMBER_LENGTH) {
			return false;
		}
		
		// Check if the document number matches the criteria
		DocumentType documentType = optionalInputDocumentType.get();
		Pattern documentTypePattern = Pattern.compile(DOCUMENT_TYPE_PATTERNS.get(documentType.getKod()));
		Matcher documentTypeMatcher = documentTypePattern.matcher(value.getOkmanySzam());
		return documentTypeMatcher.matches();
	}

}
