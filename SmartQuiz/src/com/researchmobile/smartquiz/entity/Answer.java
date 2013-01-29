package com.researchmobile.smartquiz.entity;

import java.io.Serializable;

public class Answer implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id_quiz;
	private Indicator[] indicator;
	private boolean result;
	private String message;
	private String name;
	private String Description;
	private String value;
	private String url_photo;
	private String startTime;
	private String endTime;
	private String latitude;
	private String longitude;
	
	public String getId_quiz() {
		return id_quiz;
	}
	public void setId_quiz(String id_quiz) {
		this.id_quiz = id_quiz;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUrl_photo() {
		return url_photo;
	}
	public void setUrl_photo(String url_photo) {
		this.url_photo = url_photo;
	}
	public Indicator[] getIndicator() {
		return indicator;
	}
	public void setIndicator(Indicator[] indicator) {
		this.indicator = indicator;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
