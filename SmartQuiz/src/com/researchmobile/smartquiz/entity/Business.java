package com.researchmobile.smartquiz.entity;

import java.io.Serializable;

public class Business implements Serializable{
private static final long serialVersionUID = 1L;
	
	private String idBusiness;
	private String description;
	
	
	public void setIdBusiness(String idBusiness) {
		this.idBusiness = idBusiness;
	}
	public String getIdBusiness() {
		return idBusiness;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	
	

}
