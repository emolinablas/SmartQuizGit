package com.researchmobile.smartquiz.entity;

import java.io.Serializable;

public class Red implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String idRed;
	private String description;
	
	public String getIdRed() {
		return idRed;
	}
	public void setIdRed(String idRed) {
		this.idRed = idRed;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
