package com.mlestyan.validation.document.model.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DocumentResponse implements Serializable {
	private static final long serialVersionUID = -4256641004632547334L;
	private OkmanyDTO document;
	private List<ErrorContext> errors;
}
