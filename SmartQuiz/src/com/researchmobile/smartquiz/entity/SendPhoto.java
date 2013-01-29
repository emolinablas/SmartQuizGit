package com.researchmobile.smartquiz.entity;

import java.io.Serializable;

public class SendPhoto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String pregunta;
	private String urlPhoto;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPregunta() {
		return pregunta;
	}
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	public String getUrlPhoto() {
		return urlPhoto;
	}
	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}

}
