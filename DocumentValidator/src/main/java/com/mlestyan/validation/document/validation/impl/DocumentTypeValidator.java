package com.mlestyan.validation.document.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;

import com.mlestyan.validation.document.service.contract.DocumentTypeService;
import com.mlestyan.validation.document.validation.constraint.ValidDocumentType;

/**
 * @deprecated Using Bean Validation is better choice if we only want to return the error messages only without any other contents, 
 * because we cannot reconstruct the {@link ConstraintViolation}-s on the other end and merge it with the existing violations.
 * */
@Deprecated
public class DocumentTypeValidator implements ConstraintValidator<ValidDocumentType, String> {
	@Autowired
	private DocumentTypeService documentTypeService;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value==null || this.documentTypeService.getDocumentTypeById(value).isPresent();
	}

}
