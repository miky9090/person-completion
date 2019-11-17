package com.mlestyan.validation.document.service.contract;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mlestyan.validation.document.model.domain.DocumentType;

public interface DocumentTypeReadingService {
	public List<DocumentType> readDocumentTypes() throws JsonParseException, JsonMappingException, IOException;
}
