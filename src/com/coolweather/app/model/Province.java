package com.coolweather.app.model;

import android.R.integer;

public class Province {
	
	private int id;
	private String provinceName;
	private String provinceCodeString;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getProvinceCodeString() {
		return provinceCodeString;
	}
	public void setProvinceCodeString(String provinceCodeString) {
		this.provinceCodeString = provinceCodeString;
	}
	
}
