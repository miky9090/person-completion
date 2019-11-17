package com.mlestyan.validation.person.model.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PersonResponse implements Serializable {
	private static final long serialVersionUID = -8421308158865488108L;
	private SzemelyDTO person;
	private List<ErrorContext> errors;
}
