package com.researchmobile.smartquiz.entity;

import java.io.Serializable;

public class Result implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private boolean result;
	private String name;
	private String user;
	private String message;
	private Red[] red;
	private Area[] area;
	private SubArea[] subArea;
	private Quiz[] quiz;
	
	//Nuevas Entidades
	// - - - - - - - - - - - - - - - - - - - -
	 
	private Business[] bussines;
	private Network[] network;
	private Sector[] sector;
	private Store[] store;
	private Supervision[] supervision;
	
	// - - - - - - - - - - - - - - - - - - - - 
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Red[] getRed() {
		return red;
	}
	public void setRed(Red[] red) {
		this.red = red;
	}
	public Store[] getStore() {
		return store;
	}
	public void setStore(Store[] store) {
		this.store = store;
	}
	public Area[] getArea() {
		return area;
	}
	public void setArea(Area[] area) {
		this.area = area;
	}
	public SubArea[] getSubArea() {
		return subArea;
	}
	public void setSubArea(SubArea[] subArea) {
		this.subArea = subArea;
	}
	public Quiz[] getQuiz() {
		return quiz;
	}
	public void setQuiz(Quiz[] quiz) {
		this.quiz = quiz;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public void setBussines(Business[] bussines) {
		this.bussines = bussines;
	}
	public Business[] getBussines() {
		return bussines;
	}
	public void setNetwork(Network[] network) {
		this.network = network;
	}
	public Network[] getNetwork() {
		return network;
	}
	public void setSector(Sector[] sector) {
		this.sector = sector;
	}
	public Sector[] getSector() {
		return sector;
	}
	public void setSupervision(Supervision[] supervision) {
		this.supervision = supervision;
	}
	public Supervision[] getSupervision() {
		return supervision;
	}

}
