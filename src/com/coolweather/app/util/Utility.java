package com.coolweather.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.Country;
import com.coolweather.app.model.Province;
import com.coolweather.app.model.city;

import android.R.bool;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
public  static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,String response,int provinceId){
		
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
	public  static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,
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
	// ���������������ݴ洢��County��
	coolWeatherDB.saveCountry(county);
	
	}
	
	return true;
	}
	}
	return false;
}
	public   static void handleWeatherResponse(Context context,
			String response){
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
			String cityName = weatherInfo.getString("city");
			String weatherCode = weatherInfo.getString("cityid");
			String temp1 = weatherInfo.getString("temp1");
			String temp2 = weatherInfo.getString("temp2");
			String weatherDesp = weatherInfo.getString("weather");
			//String publishTime = weatherInfo.getString("ptime");
			saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void saveWeatherInfo(Context context, String cityName,
			String weatherCode, String temp1, String temp2, String weatherDesp
		){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��M��d��",Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected",true);
		editor.putString("city_name",cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		//editor.putString("publish_time", publishTime);
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
		
	}
}
