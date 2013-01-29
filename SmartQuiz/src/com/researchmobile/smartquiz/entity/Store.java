package com.researchmobile.smartquiz.entity;

import java.io.Serializable;

public class Store implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String idStore;
	private String description;
	private String idSector;
	private String idNetwork;
	
	public void setIdStore(String idStore) {
		this.idStore = idStore;
	}
	public String getIdStore() {
		return idStore;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setIdSector(String idSector) {
		this.idSector = idSector;
	}
	public String getIdSector() {
		return idSector;
	}
	public void setIdNetwork(String idNetwork) {
		this.idNetwork = idNetwork;
	}
	public String getIdNetwork() {
		return idNetwork;
	}
	


}
