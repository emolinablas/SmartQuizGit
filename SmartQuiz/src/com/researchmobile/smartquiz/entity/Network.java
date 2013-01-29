package com.researchmobile.smartquiz.entity;

import java.io.Serializable;

public class Network implements Serializable{
private static final long serialVersionUID = 1L;
	
	private String idNetwork;
	private String description;
	private String idBusiness;
	
	
	public void setIdNetwork(String idNetwork) {
		this.idNetwork = idNetwork;
	}
	public String getIdNetwork() {
		return idNetwork;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setIdBusiness(String idBusiness) {
		this.idBusiness = idBusiness;
	}
	public String getIdBusiness() {
		return idBusiness;
	}
	

}
