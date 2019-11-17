package com.mlestyan.validation.person.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mlestyan.validation.person.model.domain.DocumentResponse;
import com.mlestyan.validation.person.model.domain.OkmanyDTO;

@FeignClient("document-service")
public interface DocumentServiceClient {
	@RequestMapping(method = RequestMethod.POST, value = "/complete-document", consumes = "application/json")
    DocumentResponse completeocument(OkmanyDTO document);
}
