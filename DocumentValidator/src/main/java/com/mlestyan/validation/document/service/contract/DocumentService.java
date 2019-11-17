package com.mlestyan.validation.document.service.contract;

import com.mlestyan.validation.document.model.domain.DocumentResponse;
import com.mlestyan.validation.document.model.domain.OkmanyDTO;

public interface DocumentService {
	public DocumentResponse completeDocument(OkmanyDTO document);
}
