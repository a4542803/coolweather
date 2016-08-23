package com.coolweather.app.db;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

import com.coolweather.app.model.Country;
import com.coolweather.app.model.Province;
import com.coolweather.app.model.city;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CoolWeatherDB {
	
	public static final String DB_NAME = "cool_weather";
	
	public static final int VERSION = 1;
	private static CoolWeatherDB coolWeatherDB;
	private SQLiteDatabase db;
	
	private CoolWeatherDB(Context context){
		coolweatherOpenHelper dbHelper = new coolweatherOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	public synchronized static CoolWeatherDB getInstance(Context context){
		
		if (coolWeatherDB == null) {
			
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	public void saveProvince(Province province){
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name",province.getProvinceName());
			values.put("province_code", province.getProvinceCodeString());
			db.insert("province", null, values);
			
		}
	}
	public List<Province> loadProvinces(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("province", null, null, null,null,null,null);
		if (cursor.moveToFirst()) {
			do {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCodeString(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		return list;
	}
	public void saveCity(city city){
		if (city != null) {
			
			ContentValues values = new ContentValues();
			values.put("city_name",city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
			
		}
	}
	public List<city> loadCities(int provinceId){
		
		List<city> list = new ArrayList<city>();
		Cursor cursor = db.query("City", null, "province_id = ?", new String[]{String.valueOf(provinceId)}, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				city cityString = new city();
				cityString.setId(cursor.getInt(cursor.getColumnIndex("id")));
				cityString.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				cityString.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				cityString.setProvinceId(provinceId);
				list.add(cityString);
			} while (cursor.moveToNext());
		}
		
		return list;
	}
	public void saveCountry(Country country)
	{
		if (country!= null) {
			
			ContentValues values = new ContentValues();
			values.put("county_name", country.getCountryName());
			values.put("county_code",country.getCountryCodeString());
			values.put("city_id",country.getCity_id());
			db.insert("Country", null, values);
			
		}
	}
	public List<Country> loadCounties(int cityId){
		List<Country> list = new ArrayList<Country>();
		Cursor cursor = db.query("Country", null, "city_id = ?", new String[]{String.valueOf(cityId)}, null, null, null);
		if (cursor.moveToFirst()) {
			Log.w("in county", "incomin");
			do {
				Country country = new Country();
				country.setId(cursor.getInt(cursor.getColumnIndex("id")));
				country.setCountryName(cursor.getString(cursor.getColumnIndex("county_name")));
				country.setCountryCodeString(cursor.getString(cursor.getColumnIndex("county_code")));
				country.setCity_id(cityId);
				list.add(country);
				Log.w("hahah", list.get(list.size()-1).toString());
			} while (cursor.moveToNext());
		}
		Log.w("return", list.toString());
		return list;
		
	}
}
