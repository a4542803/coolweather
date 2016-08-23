package com.coolweather.app.util;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.Country;
import com.coolweather.app.model.Province;
import com.coolweather.app.model.city;

import android.R.bool;
import android.text.TextUtils;
import android.util.Log;

public class Utility {
	
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB,String response){
		
		if (!TextUtils.isEmpty(response)) {
			
			String[] allProvinces = response.split(",");
			if (allProvinces != null && allProvinces.length>0) {
				
				for(String p: allProvinces){
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCodeString(array[0]);
					province.setProvinceName(array[1]);
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,String response,int provinceId){
		
		if (!TextUtils.isEmpty(response)) {
			Log.w("hand", response);
			String[] allCities = response.split(",");
			if (allCities != null && allCities.length>0) {
				Log.w("for ", "incom");
				for(String c: allCities){
					String[] array = c.split("\\|");
					city City = new city();
					City.setCityCode(array[0]);
					City.setCityName(array[1]);
					City.setProvinceId(provinceId);
					coolWeatherDB.saveCity(City);
					
				}
				return true;
			}
		}
		return false;
	}
	public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,
	String response, int cityId) {
	if (!TextUtils.isEmpty(response)) {
		Log.w("response", response);
	String[] allCounties = response.split(",");
	if (allCounties != null && allCounties.length > 0) {
	for (String c : allCounties) {
	String[] array = c.split("\\|");
	Country county = new Country();
	county.setCountryCodeString(array[0]);
	county.setCountryName(array[1]);
	county.setCity_id(cityId);
	// 将解析出来的数据存储到County表
	coolWeatherDB.saveCountry(county);
	
	}
	
	return true;
	}
	}
	return false;
}
	
}
