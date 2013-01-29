package com.researchmobile.smartquiz.entity;

import java.io.Serializable;

public class Supervision implements Serializable {
private static final long serialVersionUID = 1L;
	
	private String idSupervision;
	private String description;
	private String idStore;
	public void setIdSupervision(String idSupervision) {
		this.idSupervision = idSupervision;
	}
	public String getIdSupervision() {
		return idSupervision;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setIdStore(String idStore) {
		this.idStore = idStore;
	}
	public String getIdStore() {
		return idStore;
	}
	

}
