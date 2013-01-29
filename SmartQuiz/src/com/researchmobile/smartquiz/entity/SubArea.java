package com.researchmobile.smartquiz.entity;

import java.io.Serializable;

public class SubArea implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private boolean result;
	private String message;
	private String idSubArea;
	private String description;
	private String idArea;
	private String estado;
	private Answer[] respuesta;
	
	public String getIdSubArea() {
		return idSubArea;
	}
	public void setIdSubArea(String idSubArea) {
		this.idSubArea = idSubArea;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIdArea() {
		return idArea;
	}
	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getEstado() {
		return estado;
	}
	public void setRespuesta(Answer[] respuesta) {
		this.respuesta = respuesta;
	}
	public Answer[] getRespuesta() {
		return respuesta;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public boolean isResult() {
		return result;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}

}
