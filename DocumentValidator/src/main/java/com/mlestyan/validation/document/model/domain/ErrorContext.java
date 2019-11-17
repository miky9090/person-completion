package com.mlestyan.validation.document.model.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ErrorContext implements Serializable {
	private static final long serialVersionUID = -431532918053453550L;
	
	public ErrorContext(String errorCode) {
		this.errorCode = errorCode;
	}
	
	@JsonIgnore
	private String errorCode;
	private String resolvedMessage;
}
