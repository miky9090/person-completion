package com.mlestyan.validation.person.service.contract;

import com.mlestyan.validation.person.model.domain.DocumentResponse;
import com.mlestyan.validation.person.model.domain.OkmanyDTO;

public interface DocumentService {
	public DocumentResponse completeDocument(OkmanyDTO document);
}
