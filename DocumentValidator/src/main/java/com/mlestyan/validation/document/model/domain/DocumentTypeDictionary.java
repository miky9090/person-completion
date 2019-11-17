package com.mlestyan.validation.document.model.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DocumentTypeDictionary {
	@JsonProperty("dictname")
	private String dictname;
	@JsonProperty("rows")
	private List<DocumentType> documentTypes;
}
