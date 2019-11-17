package com.mlestyan.validation.person.model.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorContext implements Serializable {
	private static final long serialVersionUID = -431532918053453550L;
	
	public ErrorContext(String errorCode) {
		this.errorCode = errorCode;
	}
	
	@JsonIgnore
	private String errorCode;
	
	@JsonProperty("message")
	private String resolvedMessage;
}
