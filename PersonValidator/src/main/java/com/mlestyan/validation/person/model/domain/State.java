package com.mlestyan.validation.person.model.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Data
public class State {
	@Id
	@JsonProperty("kod")
	private String kod;
	@JsonProperty("allampolgarsag")
	private String allampolgarsag;
	@JsonProperty("orszag")
	private String orszag;
	@JsonProperty("schengen")
	private String schengen;
	@JsonProperty("sis1_orsz")
	private String sis1Orsz;
	@JsonProperty("sis1_orsz_hun")
	private String sis1OrszHun;
}
