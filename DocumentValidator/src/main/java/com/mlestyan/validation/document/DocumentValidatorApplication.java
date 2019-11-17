package com.mlestyan.validation.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mlestyan.validation.document.service.contract.DocumentTypeReadingService;
import com.mlestyan.validation.document.service.contract.DocumentTypeService;

@SpringBootApplication
public class DocumentValidatorApplication implements CommandLineRunner {
	@Autowired
	private DocumentTypeReadingService documentTypeReadingService;
	
	@Autowired
	private DocumentTypeService documentTypeService;

	public static void main(String[] args) {
		SpringApplication.run(DocumentValidatorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.documentTypeService.saveDocumentTypes(this.documentTypeReadingService.readDocumentTypes());
	}
}
