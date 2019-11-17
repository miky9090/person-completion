package com.mlestyan.validation.document.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlestyan.validation.document.model.domain.DocumentResponse;
import com.mlestyan.validation.document.model.domain.ErrorContext;
import com.mlestyan.validation.document.model.domain.OkmanyDTO;
import com.mlestyan.validation.document.service.contract.DocumentService;
import com.mlestyan.validation.document.service.contract.validation.DocumentImageValidationService;
import com.mlestyan.validation.document.service.contract.validation.DocumentNumberValidationService;
import com.mlestyan.validation.document.service.contract.validation.DocumentTypeValidationService;

import static com.mlestyan.validation.document.service.impl.ValidationMessageConstants.*;

@Service
public class DocumentServiceBean implements DocumentService {
	@Autowired
	private DocumentTypeValidationService documentTypeValidationService;
	
	@Autowired
	private DocumentNumberValidationService documentNumberValidationService;
	
	@Autowired
	private DocumentImageValidationService documentImageValidationService;

	/**
	 * Validates and completes the input {@link OkmanyDTO}. A document is valid only if there are no errors and the expiration date is in the future.
	 * Errors are collected in a list of {@link ErrorContext} object.
	 * @param document the input document
	 * @return a document response wrapper object holding the completed {@link OkmanyDTO document} and the {@link ErrorContext}-s
	 * */
	@Override
	public DocumentResponse completeDocument(OkmanyDTO document) {
		List<ErrorContext> errors = new ArrayList<>();
		DocumentResponse response = new DocumentResponse();
		
		if(document.getOkmTipus() == null || !documentTypeValidationService.validateDocumentType(document.getOkmTipus())) {
			errors.add(new ErrorContext(INVALID_DOCUMENT_TYPE));
		}
		else if(document.getOkmanySzam() == null || !documentNumberValidationService.validateDocumentNumber(document.getOkmTipus(), document.getOkmanySzam())) {
			errors.add(new ErrorContext(INVALID_DOCUMENT_NUMBER));
		}
		
		if(document.getOkmanyKep() == null || !documentImageValidationService.validateDocumentImage(document.getOkmanyKep())) {
			errors.add(new ErrorContext(INVALID_IMAGE));
		}
		
		if(document.getLejarDat() == null) {
			errors.add(new ErrorContext(INVALID_EXPIRATION_DATE));
		}
		else if(errors.isEmpty() && document.getLejarDat().after(new Date())) {
			// If there was any error in the data, it shouldn't be a valid document
			document.setErvenyes(true);
		}
		
		response.setErrors(errors);
		response.setDocument(document);
		
		return response;
	}
}
