package com.mlestyan.validation.document.model.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Data
public class DocumentType {
	@Id
	@JsonProperty("kod")
	private String kod;
	@JsonProperty("ertek")
	private String ertek;
}
