package com.researchmobile.smartquiz.entity;

import java.io.Serializable;

public class Indicator implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String idIndicator;
	private String description;
	private String pregunta;
	private String value;
	private String urlPhoto;
	private String idQuiz;
	private String quizDescription;
	private int state;
	private boolean useCamera;
	public String getIdIndicator() {
		return idIndicator;
	}
	public void setIdIndicator(String idIndicator) {
		this.idIndicator = idIndicator;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUrlPhoto() {
		return urlPhoto;
	}
	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}
	public String getIdQuiz() {
		return idQuiz;
	}
	public void setIdQuiz(String idQuiz) {
		this.idQuiz = idQuiz;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public boolean isUseCamera() {
		return useCamera;
	}
	public void setUseCamera(boolean useCamera) {
		this.useCamera = useCamera;
	}
	public String getPregunta() {
		return pregunta;
	}
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	public void setQuizDescription(String quizDescription) {
		this.quizDescription = quizDescription;
	}
	public String getQuizDescription() {
		return quizDescription;
	}

}
