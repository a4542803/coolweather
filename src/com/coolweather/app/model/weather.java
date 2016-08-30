package com.coolweather.app.model;

import java.util.Comparator;

import android.R.integer;

public class weather {
	
	public int id;
	private String curTempString;
	private String allTempString;
	
	private String weatherInfoString;
	private String weekString;
	private String wind;
	private String weatherCodeString;
	public String getWeatherCodeString() {
		return weatherCodeString;
	}
	public void setWeatherCodeString(String weatherCodeString) {
		this.weatherCodeString = weatherCodeString;
	}
	public String getWeaicon() {
		return weaicon;
	}
	public void setWeaicon(String weaicon) {
		this.weaicon = weaicon;
	}
	private String weaicon;
	public String getCityNameString() {
		return cityNameString;
	}
	public void setCityNameString(String cityNameString) {
		this.cityNameString = cityNameString;
	}
	private String date;
	private String cityNameString;
	public String getCurTempString() {
		return curTempString;
	}
	public void setCurTempString(String curTempString) {
		this.curTempString = curTempString;
	}
	public String getAllTempString() {
		return allTempString;
	}
	public void setAllTempString(String allTempString) {
		this.allTempString = allTempString;
	}
	public String getWeatherInfoString() {
		return weatherInfoString;
	}
	public void setWeatherInfoString(String weatherInfoString) {
		this.weatherInfoString = weatherInfoString;
	}
	public String getWeekString() {
		return weekString;
	}
	public void setWeekString(String weekString) {
		this.weekString = weekString;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String toString(){  
		        return String.valueOf(this.id);  
	    }  

}


