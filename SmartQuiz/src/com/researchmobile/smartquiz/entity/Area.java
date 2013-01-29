package com.researchmobile.smartquiz.entity;

import java.io.Serializable;

public class Area implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String idArea;
	private String description;
	private String estado;
	public String getIdArea() {
		return idArea;
	}
	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getEstado() {
		return estado;
	}
	
}
