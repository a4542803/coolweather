package com.coolweather.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import org.json.JSONObject;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.Country;
import com.coolweather.app.model.Province;
import com.coolweather.app.model.city;
import com.coolweather.app.model.weather;

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
	// 将解析出来的数据存储到County表
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
			JSONObject weatherInfo = jsonObject.getJSONObject("result");
			String cityName = weatherInfo.getString("citynm");
			String weatherCode = weatherInfo.getString("cityid");
			String days = weatherInfo.getString("days");
			String temperature = weatherInfo.getString("temperature");
			String temperature_curr = weatherInfo.getString("temperature_curr");
			String weatherDesp = weatherInfo.getString("weather");
			//String publishTime = weatherInfo.getString("ptime");
			String weather_icon = weatherInfo.getString("weather_icon");
			String week = weatherInfo.getString("week");
			String wind = weatherInfo.getString("wind");
			
			saveWeatherInfo(context,cityName,weatherCode,temperature,temperature_curr,weatherDesp,days,weather_icon,week,wind);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void saveWeatherInfo(Context context, String cityName,
			String weatherCode, String temp1, String temp2, String weatherDesp,
		String days,String weather_icon,String week,String wind){
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日",Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected",true);
		editor.putString("city_name",cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		//editor.putString("publish_time", publishTime);
		editor.putString("current_date", days);
		editor.putString("weather_icon", weather_icon);
		editor.putString("week", week);
		editor.putString("wind", wind);
		editor.commit();
		
		
	}
	
}
