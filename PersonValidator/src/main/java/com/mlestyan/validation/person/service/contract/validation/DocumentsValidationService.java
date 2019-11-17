package com.mlestyan.validation.person.service.contract.validation;

import com.mlestyan.validation.person.model.domain.OkmanyDTO;

public interface DocumentsValidationService {
	public boolean validateDocuments(Iterable<OkmanyDTO> documents);
}
