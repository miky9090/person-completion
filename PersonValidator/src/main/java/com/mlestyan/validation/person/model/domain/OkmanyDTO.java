/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlestyan.validation.person.model.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author Ruzsinak
 */
public class OkmanyDTO implements Serializable{
	private static final long serialVersionUID = 1L;
    
	@ApiModelProperty(value = "The type of the document", example = "1")
    private String okmTipus;
    
    @ApiModelProperty(value = "The document number", example = "ABCDEF12")
    private String okmanySzam;

    @ApiModelProperty(value = "An image represented as a byte array", example = "[1, 0, 1, 0]")
    private byte[] okmanyKep;
    
    @ApiModelProperty(value = "Expiration date", dataType = "date", example = "2010.01.01")
    @JsonFormat(pattern="yyyy.MM.dd")
    private Date lejarDat;
    
    @ApiModelProperty(value = "Is the document valid", example = "false")
    private boolean ervenyes;
    
    /**
     * @return the okmTipus
     */
    public String getOkmTipus() {
        return okmTipus;
    }

    /**
     * @param okmTipus the okmTipus to set
     */
    public void setOkmTipus(String okmTipus) {
        this.okmTipus = okmTipus;
    }

    /**
     * @return the okmanySzam
     */
    public String getOkmanySzam() {
        return okmanySzam;
    }

    /**
     * @param okmanySzam the okmanySzam to set
     */
    public void setOkmanySzam(String okmanySzam) {
        this.okmanySzam = okmanySzam;
    }

    /**
     * @return the okmanyKep
     */
    public byte[] getOkmanyKep() {
        return okmanyKep;
    }

    /**
     * @param okmanyKep the okmanyKep to set
     */
    public void setOkmanyKep(byte[] okmanyKep) {
        this.okmanyKep = okmanyKep;
    }

    /**
     * @return the lejarDat
     */
    public Date getLejarDat() {
        return lejarDat;
    }

    /**
     * @param lejarDat the lejarDat to set
     */
    public void setLejarDat(Date lejarDat) {
        this.lejarDat = lejarDat;
    }

    /**
     * @return the ervenyes
     */
    public boolean isErvenyes() {
        return ervenyes;
    }

    /**
     * @param ervenyes the ervenyes to set
     */
    public void setErvenyes(boolean ervenyes) {
        this.ervenyes = ervenyes;
    }
}
