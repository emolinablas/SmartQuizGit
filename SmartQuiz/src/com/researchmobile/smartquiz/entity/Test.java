package com.researchmobile.smartquiz.entity;

import java.io.Serializable;

public class Test implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String idTest;
	private String test;
	private int state;
	private boolean useCamera;
	private String urlPhoto;
	public String getIdTest() {
		return idTest;
	}
	public void setIdTest(String idTest) {
		this.idTest = idTest;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getUrlPhoto() {
		return urlPhoto;
	}
	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}
	public boolean isUseCamera() {
		return useCamera;
	}
	public void setUseCamera(boolean useCamera) {
		this.useCamera = useCamera;
	}
}
