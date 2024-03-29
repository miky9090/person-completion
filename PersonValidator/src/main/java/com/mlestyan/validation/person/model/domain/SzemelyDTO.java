/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlestyan.validation.person.model.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author Ruzsinak
 */
public class SzemelyDTO implements Serializable {

    /**
     * @return the visNev
     */
    public String getVisNev() {
        return visNev;
    }

    /**
     * @param visNev the visNev to set
     */
    public void setVisNev(String visNev) {
        this.visNev = visNev;
    }

    /**
     * @return the szulNev
     */
    public String getSzulNev() {
        return szulNev;
    }

    /**
     * @param szulNev the szulNev to set
     */
    public void setSzulNev(String szulNev) {
        this.szulNev = szulNev;
    }

    /**
     * @return the aNev
     */
    public String getaNev() {
        return aNev;
    }

    /**
     * @param aNev the aNev to set
     */
    public void setaNev(String aNev) {
        this.aNev = aNev;
    }

    /**
     * @return the szulDat
     */
    public Date getSzulDat() {
        return szulDat;
    }

    /**
     * @param szulDat the szulDat to set
     */
    public void setSzulDat(Date szulDat) {
        this.szulDat = szulDat;
    }

    /**
     * @return the neme
     */
    public String getNeme() {
        return neme;
    }

    /**
     * @param neme the neme to set
     */
    
    public void setNeme(String neme) {
        this.neme = neme != null ? neme.toUpperCase() : null;
    }

    /**
     * @return the allampKod
     */
    public String getAllampKod() {
        return allampKod;
    }

    /**
     * @param allampKod the allampKod to set
     */
    public void setAllampKod(String allampKod) {
        this.allampKod = allampKod != null ? allampKod.toUpperCase() : null;
    }

    /**
     * @return the allampDekod
     */
    public String getAllampDekod() {
        return allampDekod;
    }

    /**
     * @param allampDekod the allampDekod to set
     */
    public void setAllampDekod(String allampDekod) {
        this.allampDekod = allampDekod;
    }

    /**
     * @return the okmLista
     */
    public List<OkmanyDTO> getOkmLista() {
        return okmLista;
    }

    /**
     * @param okmLista the okmLista to set
     */
    public void setOkmLista(List<OkmanyDTO> okmLista) {
        this.okmLista = okmLista;
    }
    private static final long serialVersionUID = 4L;
    
    @ApiModelProperty(example = "Kiss József")
    private String visNev;
    
    @ApiModelProperty(example = "Kiss József")
    private String szulNev;
    
    @ApiModelProperty(example = "Kiss Mária")
    private String aNev;
    
    @ApiModelProperty(dataType = "date", example = "2010.01.01")
    @JsonFormat(pattern="yyyy.MM.dd")
    private Date szulDat;
    
    @ApiModelProperty(example = "F")
    private String neme;
    
    @ApiModelProperty(example = "HUN")
    private String allampKod;
    
    private String allampDekod;
    
    @ApiModelProperty
    private List<OkmanyDTO> okmLista;
    
}
