package com.coolweather.app.model;

public class city {
	
	private int id;
	private String cityName;
	private String cityCode;
	private int provinceId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String countyName) {
		this.cityName = countyName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String countyCode) {
		this.cityCode = countyCode;
	}
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int cityId) {
		this.provinceId = cityId;
	}
}
