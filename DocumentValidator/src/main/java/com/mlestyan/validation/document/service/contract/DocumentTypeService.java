package com.mlestyan.validation.document.service.contract;

import java.util.Optional;

import com.mlestyan.validation.document.model.domain.DocumentType;

public interface DocumentTypeService {
	public Optional<DocumentType> getDocumentTypeById(String id);
	public Iterable<DocumentType> saveDocumentTypes(Iterable<DocumentType> documentTypes);
}
