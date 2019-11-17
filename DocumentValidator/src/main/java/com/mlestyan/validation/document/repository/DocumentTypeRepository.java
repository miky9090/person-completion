package com.mlestyan.validation.document.repository;

import org.springframework.data.repository.CrudRepository;

import com.mlestyan.validation.document.model.domain.DocumentType;

public interface DocumentTypeRepository extends CrudRepository<DocumentType, String> {}
