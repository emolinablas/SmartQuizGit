package com.researchmobile.smartquiz.entity;

import java.io.Serializable;

public class Sector implements Serializable{
private static final long serialVersionUID = 1L;
	
	private String idSector;
	private String description;
	
	
	public void setIdSector(String idSector) {
		this.idSector = idSector;
	}
	public String getIdSector() {
		return idSector;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}

}
