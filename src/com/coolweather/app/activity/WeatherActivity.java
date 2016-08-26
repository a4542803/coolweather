package com.coolweather.app.activity;

import java.nio.charset.CodingErrorAction;
import java.util.Set;

import com.coolweather.app.R;
import com.coolweather.app.model.weather;
import com.coolweather.app.service.AutoUpdateServece;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import coolweather.app.myAppPlication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherActivity extends Activity implements OnClickListener {
	
		private LinearLayout weatherInfoLayout;
		private LinearLayout icon1;
		/**
		* 用于显示城市名
		* */
		private TextView cityNameText;
		/**
		* 用于显示发布时间
		*/
		private TextView publishText;
		/**
		* 用于显示天气描述信息
		*/
		private TextView weatherDespText;
		/**
		* 用于显示气温1
		*/
		private TextView temp1Text;
		/**
		* 用于显示气温2
		*/
		private TextView temp2Text;
		/**
		* 用于显示当前日期
		*/
		private TextView currentDateText;
		/**
		* 切换城市按钮
		*/
		private Button switchCity;
		private TextView iconTextView;
		private TextView weekTextView;
		private TextView windTextView;
		/**
		* 更新天气按钮
		*/
		private Button refreshWeather;
		private PicHandler	 pic_hdl; 
		private ImageView imageView;
		protected void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.weather_layout);
			Log.w("weather", "open weather ok");
			weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
			icon1 = (LinearLayout) findViewById(R.id.icon1);
			Log.w("weather thread",Thread.currentThread().getName());
			cityNameText = (TextView) findViewById(R.id.city_name);
			publishText = (TextView) findViewById(R.id.publish_text);
			weatherDespText = (TextView) findViewById(R.id.weather_desp);
			temp1Text = (TextView) findViewById(R.id.temp1);
			temp2Text = (TextView) findViewById(R.id.temp2);
			weekTextView = (TextView) findViewById(R.id.week);
			windTextView = (TextView) findViewById(R.id.wind);
			//currentDateText = (TextView) findViewById(R.id.current_date);
			switchCity = (Button) findViewById(R.id.switch_city);
			iconTextView = (TextView) findViewById(R.id.icon);
			imageView = (ImageView) findViewById(R.id.image);
			refreshWeather = (Button) findViewById(R.id.refresh_weather);
			pic_hdl = new PicHandler();
			
			String countryCode = getIntent().getStringExtra("county_code");
			if (!TextUtils.isEmpty(countryCode)) {
				
				publishText.setText("同步中...");
				weatherInfoLayout.setVisibility(View.INVISIBLE);
				cityNameText.setVisibility(View.INVISIBLE);
				icon1.setVisibility(View.INVISIBLE);
			
				queryWeatherCode(countryCode);
			}else {
					showWeather();
			}
			switchCity.setOnClickListener(this);
			refreshWeather.setOnClickListener(this);
		}
		public void onClick(View v){
			switch(v.getId()){
			case R.id.switch_city:
				Intent intent = new Intent(this,ChooseAreaActivity.class);
				intent.putExtra("from weather activity",true);
				startActivity(intent);
				finish();
				break;
			case R.id.refresh_weather:
				publishText.setText("同步中...");
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
				String weatherCode = prefs.getString("weather_code","");
				if (!TextUtils.isEmpty(weatherCode)) {
					queryWeatherInfo(weatherCode);
					}
					break;
					default:
						break;
			}
		}
		private void queryWeatherCode(String countyCode){
			String address = "http://www.weather.com.cn/data/list3/city" +
					countyCode + ".xml";
			queryFromServer(address, "countyCode");
		}
		private void queryWeatherInfo(String weatherCode){
			
			/*String address = "http://www.weather.com.cn/data/cityinfo/" +
					weatherCode + ".html";*/
			//String address = "http://api.k780.com:88/?app=weather.today&weaid=" + weatherCode+"&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json";
			//Log.w("address", address);
			String address =  "http://api.k780.com:88/?app=weather.future&weaid=" + weatherCode+"&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=xml";
			queryFromServer(address,"weatherCode");
		}
		private void queryFromServer(final String address,final String type){
			//Log.w("11111", type);
			if ("countyCode".equals(type)) {
				HttpUtil.sendHttpRequest(address,true, new HttpCallbackListener() {
					
					@Override
					public void onFinish(String response) {
						
						// TODO Auto-generated method stubm
						Log.w("type", type);
						if ("countyCode".equals(type)) {
							Log.w("respon", response);
							if (!TextUtils.isEmpty(response)) {
								
								String[] array = response.split("\\|");
								if (array != null &&array.length == 2) {
									
								
									String weatherCode = array[1];
									Log.w("weatherCode",weatherCode);
									queryWeatherInfo(weatherCode);
								}
							}
						}else if ("weatherCode".equals(type)) {
							//Log.w("weatherCode11", type);
							Utility.handleWeatherResponse(WeatherActivity.this, response);
							showImage();
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
								
									// TODO Auto-generated method stub
						
									showWeather();
									//Log.w("choose 1111111thread",Thread.currentThread().getName());
								}
							});
						}
					}
					
					@Override
					public void onError(Exception e) {
						// TODO Auto-generated method stub
						runOnUiThread( new Runnable() {
							public void run() {
								publishText.setText("同步失败");
							}
						});
					}
				});
			}
			else {
					HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
					
					@Override
					public void onFinish(String response) {
						
						// TODO Auto-generated method stubm
						Log.w("type", type);
						if ("countyCode".equals(type)) {
							Log.w("respon", response);
							if (!TextUtils.isEmpty(response)) {
								
								String[] array = response.split("\\|");
								if (array != null &&array.length == 2) {
									
								
									String weatherCode = array[1];
									Log.w("weatherCode",weatherCode);
									queryWeatherInfo(weatherCode);
								}
							}
						}else if ("weatherCode".equals(type)) {
							//Log.w("weatherCode11", type);
							//Utility.handleWeatherResponse(WeatherActivity.this, response);
							//showImage();
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
								
									// TODO Auto-generated method stub
						
									showWeather();
									//Log.w("choose 1111111thread",Thread.currentThread().getName());
								}
							});
						}
					}
					
					@Override
					public void onError(Exception e) {
						// TODO Auto-generated method stub
						runOnUiThread( new Runnable() {
							public void run() {
								publishText.setText("同步失败");
							}
						});
					}
				});
			}
				
			}
			
	
		
		private void showImage(){
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			 final 	String urlString = prefs.getString("weather_icon", "");
			if (urlString.contains("http")) {
				
				new Thread(new Runnable(){
					public void run() {
						// TODO Auto-generated method stub
						 Bitmap img = HttpUtil.getUrlImage(urlString);  
					        Message msg = /*pic_hdl.obtainMessage()*/new Message(); 
					        msg.what = 0; 
					        msg.obj = img; 
					        pic_hdl.sendMessage(msg);  
					}
				}).start();
				
			}
		}
		
		public void showWeather(){
			
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			
			cityNameText.setText(prefs.getString("city_name",""));
			temp1Text.setText(prefs.getString("temp1", ""));
			temp2Text.setText(prefs.getString("temp2", ""));
			weatherDespText.setText(prefs.getString("weather_desp", ""));
			publishText.setText("今天" + prefs.getString("current_date", "") + "发布");
			//currentDateText.setText(prefs.getString("current_date", ""));
			//iconTextView.setText(prefs.getString("weather_icon", ""));
			weekTextView.setText(prefs.getString("week", ""));
			windTextView.setText(prefs.getString("wind", ""));
			for(weather w:myAppPlication.getSet())
			{
				Log.w("set", w.getWeekString());
			}
			Log.w("size", myAppPlication.getSet().size()+"");
			weatherInfoLayout.setVisibility(View.VISIBLE);
			cityNameText.setVisibility(View.VISIBLE);
			icon1.setVisibility(View.VISIBLE);
			Intent intent = new Intent(this, AutoUpdateServece.class);
			startService(intent);
		}
	
		class PicHandler extends Handler{ 
		 
		    public void handleMessage(Message msg) { 
		        // TODO Auto-generated method stub 
		       //String s = (String)msg.ob
		        //ptv.setText(s); 
		        Bitmap myimg = (Bitmap)msg.obj; 
		        imageView.setImageBitmap(myimg); 

		    } 
		 
		      
		} 
		


}
