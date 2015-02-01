package com.arroon.listview;

public class Movie {
	private String name;
	private String logo;
	private String buttonText;
	public Movie(String name, String logo, String buttonText) {
		super();
		this.name = name;
		this.logo = logo;
		this.buttonText = buttonText;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getButtonText() {
		return buttonText;
	}
	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}
	
}
