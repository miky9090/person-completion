package com.mlestyan.validation.document.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlestyan.validation.document.model.domain.DocumentType;
import com.mlestyan.validation.document.repository.DocumentTypeRepository;
import com.mlestyan.validation.document.service.contract.DocumentTypeService;

@Service
public class DocumentTypeServiceBean implements DocumentTypeService {
	private DocumentTypeRepository documentTypeRepository;
	
	@Autowired
	public DocumentTypeServiceBean(DocumentTypeRepository documentTypeRepository) {
		this.documentTypeRepository = documentTypeRepository;
	}
	
	@Override
	public Optional<DocumentType> getDocumentTypeById(String id) {
		return this.documentTypeRepository.findById(id);
	}
	
	@Override
	public Iterable<DocumentType> saveDocumentTypes(Iterable<DocumentType> documentTypes) {
		return this.documentTypeRepository.saveAll(documentTypes);
	}

}
