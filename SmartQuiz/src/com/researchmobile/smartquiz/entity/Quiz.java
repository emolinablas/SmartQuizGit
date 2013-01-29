package com.researchmobile.smartquiz.entity;

import java.io.Serializable;

public class Quiz implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id_quiz;
	private String description;
	private String idStore;
	private String idSubArea;
	private String latitude;
	private String longitude;
	private String hour_first;
	private String hour_last;
	private String date;
	
	private String name;
	
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
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHour_first() {
		return hour_first;
	}
	public void setHour_first(String hour_first) {
		this.hour_first = hour_first;
	}
	public String getHour_last() {
		return hour_last;
	}
	public void setHour_last(String hour_last) {
		this.hour_last = hour_last;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public String getIdStore() {
		return idStore;
	}
	public void setIdStore(String idStore) {
		this.idStore = idStore;
	}
	public String getIdSubArea() {
		return idSubArea;
	}
	public void setIdSubArea(String idSubArea) {
		this.idSubArea = idSubArea;
	}
	
}
