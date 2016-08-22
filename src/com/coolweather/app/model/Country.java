package com.coolweather.app.model;

import android.R.integer;

public class Country {
	
	private int id;
	private String countryName;
	private String countryCodeString;
	private int provinceid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryCodeString() {
		return countryCodeString;
	}
	public void setCountryCodeString(String countryCodeString) {
		this.countryCodeString = countryCodeString;
	}
	public int getCity_id() {
		return provinceid;
	}
	public void setCity_id(int provinceid) {
		this.provinceid = provinceid;
	}
}
