package com.coolweather.app.service;

import javax.security.auth.PrivateCredentialPermission;

import com.coolweather.app.activity.WeatherActivity;
import com.coolweather.app.model.weather;
import com.coolweather.app.receiver.AutoUpdateReceiver;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import coolweather.app.myAppPlication;

import android.R.integer;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.AlarmClock;

public class AutoUpdateServece extends Service{
	
	public IBinder onBind(Intent intent){
		return null;
	}
	
	public int onStartCommand(Intent intent,int flags,int startId)
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				updateWeather();
			}
		}).start();
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		int anHour = 8*60*60*1000;
		long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
		Intent i = new Intent(this,AutoUpdateReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
		return super.onStartCommand(intent,flags,startId);
		
	}
	private void updateWeather(){
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String weatherCode = prefs.getString("weather_code","");
		Object[] arry = 	myAppPlication.getSet().toArray();
		weather weathers = (weather) arry[0];
		String address = "http://www.weather.com.cn/data/cityinfo/" + weathers.getWeatherCodeString() +".html";
		HttpUtil.sendHttpRequest(address,new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				//Utility.handleWeatherResponse(AutoUpdateServece.this, response);
			new WeatherActivity().showWeather(0);
				
				
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				e.printStackTrace();
			}
		});
	}
	
}
