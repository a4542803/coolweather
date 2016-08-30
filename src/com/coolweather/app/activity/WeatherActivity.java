package com.coolweather.app.activity;

import java.nio.charset.CodingErrorAction;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.coolweather.app.R;
import com.coolweather.app.R.drawable;
import com.coolweather.app.model.weather;
import com.coolweather.app.service.AutoUpdateServece;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import coolweather.app.myAppPlication;

import android.R.bool;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TwoLineListItem;

public class WeatherActivity extends Activity implements OnClickListener {
	
		private LinearLayout weatherInfoLayout;
		private LinearLayout icon1;
		private RelativeLayout relativeLayout;
		private boolean ReflashFlag;
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
	//	private TextView temp1Text;
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
		private  weather weainfo = null;
		private Button switchCity;
		private TextView iconTextView;
		private TextView weekTextView;
		private TextView windTextView;
		private Button	buttonOne;
		private Button twpButton;
		private Button threeButton;
		private Button	buttonFour;
		private Button ButtonFri;
		private Button ButtonSat;
		private Button Buttonwnd;
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
			relativeLayout = (RelativeLayout) findViewById(R.id.layout_button);
			icon1 = (LinearLayout) findViewById(R.id.icon1);
			Log.w("weather thread",Thread.currentThread().getName());
			cityNameText = (TextView) findViewById(R.id.city_name);
			publishText = (TextView) findViewById(R.id.publish_text);
			weatherDespText = (TextView) findViewById(R.id.weather_desp);
		//	temp1Text = (TextView) findViewById(R.id.temp1);
			temp2Text = (TextView) findViewById(R.id.temp2);
			weekTextView = (TextView) findViewById(R.id.week);
			windTextView = (TextView) findViewById(R.id.wind);
			//currentDateText = (TextView) findViewById(R.id.current_date);
			switchCity = (Button) findViewById(R.id.switch_city);
			iconTextView = (TextView) findViewById(R.id.icon);
			imageView = (ImageView) findViewById(R.id.image);
			refreshWeather = (Button) findViewById(R.id.refresh_weather);
			pic_hdl = new PicHandler();
			buttonOne = (Button) findViewById(R.id.buttonone);
			twpButton = (Button) findViewById(R.id.buttontwo);
			threeButton = (Button) findViewById(R.id.buttonthree);
			buttonFour = (Button) findViewById(R.id.buttonfour);
			ButtonFri = (Button) findViewById(R.id.buttonfri);
			ButtonSat = (Button) findViewById(R.id.buttonsat);
			Buttonwnd = (Button) findViewById(R.id.buttonwnd);
			String countryCode = getIntent().getStringExtra("county_code");
			if (!TextUtils.isEmpty(countryCode)) {
				
				publishText.setText("同步中...");
				weatherInfoLayout.setVisibility(View.INVISIBLE);
				cityNameText.setVisibility(View.INVISIBLE);
				icon1.setVisibility(View.INVISIBLE);
				relativeLayout.setVisibility(View.INVISIBLE);
				queryWeatherCode(countryCode);
			}else {
					showWeather(0);
			}
			switchCity.setOnClickListener(this);
			refreshWeather.setOnClickListener(this);
			buttonOne.setOnClickListener(this);
			twpButton.setOnClickListener(this);
			threeButton.setOnClickListener(this);
			buttonFour.setOnClickListener(this);
			ButtonFri.setOnClickListener(this);
			ButtonSat.setOnClickListener(this);
			Buttonwnd.setOnClickListener(this);
			buttonOne.setSelected(true);
			twpButton.setSelected(false);
			threeButton.setSelected(false);
			buttonFour.setSelected(false);
			ButtonFri.setSelected(false);
			ButtonSat.setSelected(false);
			Buttonwnd.setSelected(false);
			ReflashFlag = false;
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
				//SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
				//String weatherCode = prefs.getString("weather_code","");
				//Object weainfoWeather[] = myAppPlication.getSet().toArray(); 
				 //weather weainfo = null;
				//Log.w("id", weainfo.getId()+"");
				/*if (!TextUtils.isEmpty(((weather)weainfoWeather[weainfo.getId()]).getWeatherCodeString())) {
					queryWeatherInfo(((weather)weainfoWeather[weainfo.getId()]).getWeatherCodeString());
					}*/
				if (!TextUtils.isEmpty(weainfo.getWeatherCodeString())) {
					ReflashFlag = true;
					queryWeatherInfo(weainfo.getWeatherCodeString());
					
					}
				
				break;
			case R.id.buttonone:
				buttonOne.setSelected(true);
				twpButton.setSelected(false);
				threeButton.setSelected(false);
				buttonFour.setSelected(false);
				ButtonFri.setSelected(false);
				ButtonSat.setSelected(false);
				Buttonwnd.setSelected(false);
				ReflashFlag = false;
				showWeather(0);
				break;
			case R.id.buttontwo:
				
				showWeather(1);
				break;
			case R.id.buttonthree:
				
				showWeather(2);
				break;
			case R.id.buttonfour:
				
				showWeather(3);
				break;
			case R.id.buttonfri:
				
				showWeather(4);
				break;
			case R.id.buttonsat:
				
				showWeather(5);
				break;
			case R.id.buttonwnd:
				
				showWeather(6);
				break;
				default:
				break;
				
			}
		}
		/*public boolean onKey(View view,integer keyCode,KeyEvent enent)
		{	
			if (KeyEvent.ACTION_DOWN == enent.getAction()) {
				view.setBackgroundColor(Color.BLUE);
			}
			else if (KeyEvent.ACTION_UP == enent.getAction()) {
				view.setBackgroundColor(Color.GREEN);
			}
			return false;
		}
		public void onFocusChange(View view,boolean hasFocus)
		{
			if (hasFocus) {
				view.setBackgroundColor(Color.GREEN);
			}
			else {
				
				view.setBackgroundColor(Color.BLUE);
			}
		}*/
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
							//Utility.handleWeatherResponse(WeatherActivity.this, response);
							//showImage();
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
								
									// TODO Auto-generated method stub
						
									showWeather(0);
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
							//Log.w("respon", response);
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
									/*if (weainfo.getId()>0) {
										
										showWeather(weainfo.getId()-1);
									}
									else */{
										showWeather(0);
									}
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
			
	
		
		private void showImage(String url){
			
			 final 	String urlString = url;
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
		
		public  void showWeather(int i){
			
			//SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			
			/*cityNameText.setText(prefs.getString("city_name",""));
			temp1Text.setText(prefs.getString("temp1", ""));
			temp2Text.setText(prefs.getString("temp2", ""));
			weatherDespText.setText(prefs.getString("weather_desp", ""));
			publishText.setText("今天" + prefs.getString("current_date", "") + "发布");
			//currentDateText.setText(prefs.getString("current_date", ""));
			//iconTextView.setText(prefs.getString("weather_icon", ""));
			weekTextView.setText(prefs.getString("week", ""));
			windTextView.setText(prefs.getString("wind", ""));*/
			 Object weainfoWeather[] = myAppPlication.getSet().toArray(); 
			// weather weainfo = null;
			if (i== 0) {
				
				if (ReflashFlag) {
					Log.w("reflash", "reflash");
					i = weainfo.getId() - 1;
				}
			}
			 
			 
			switch (i) {
			case 0:
				  weainfo = (weather) weainfoWeather[i];
				 
				  buttonOne.setText(((weather)weainfoWeather[0]).getWeekString());
				  twpButton.setText(((weather)weainfoWeather[1]).getWeekString());
				  threeButton.setText(((weather)weainfoWeather[2]).getWeekString());
				  buttonFour.setText(((weather)weainfoWeather[3]).getWeekString());
				  ButtonFri.setText(((weather)weainfoWeather[4]).getWeekString());
				  ButtonSat.setText(((weather)weainfoWeather[5]).getWeekString());
				  Buttonwnd.setText(((weather)weainfoWeather[6]).getWeekString());
				  ReflashFlag = true;
				break;
			case 1:
				  weainfo = (weather) weainfoWeather[i];
				  buttonOne.setSelected(false);
					twpButton.setSelected(true);
					threeButton.setSelected(false);
					buttonFour.setSelected(false);
					ButtonFri.setSelected(false);
					ButtonSat.setSelected(false);
					Buttonwnd.setSelected(false);
					ReflashFlag = true;
				break;
				
			case 2:
				weainfo = (weather) weainfoWeather[i];
				buttonOne.setSelected(false);
				twpButton.setSelected(false);
				threeButton.setSelected(true);
				buttonFour.setSelected(false);
				ButtonFri.setSelected(false);
				ButtonSat.setSelected(false);
				Buttonwnd.setSelected(false);
				ReflashFlag = true;
				break;
			
			case 3:
				weainfo = (weather) weainfoWeather[i];
				buttonOne.setSelected(false);
				twpButton.setSelected(false);
				threeButton.setSelected(false);
				buttonFour.setSelected(true);
				ButtonFri.setSelected(false);
				ButtonSat.setSelected(false);
				Buttonwnd.setSelected(false);
				ReflashFlag = true;
				break;
			
			case 4:
				weainfo = (weather) weainfoWeather[i];
				buttonOne.setSelected(false);
				twpButton.setSelected(false);
				threeButton.setSelected(false);
				buttonFour.setSelected(false);
				ButtonFri.setSelected(true);
				ButtonSat.setSelected(false);
				Buttonwnd.setSelected(false);
				ReflashFlag = true;
				break;
			
			case 5:
				weainfo = (weather) weainfoWeather[i];
				buttonOne.setSelected(false);
				twpButton.setSelected(false);
				threeButton.setSelected(false);
				buttonFour.setSelected(false);
				ButtonFri.setSelected(false);
				ButtonSat.setSelected(true);
				Buttonwnd.setSelected(false);
				ReflashFlag = true;
				break;
			
			case 6:
				weainfo = (weather) weainfoWeather[i];
				buttonOne.setSelected(false);
				twpButton.setSelected(false);
				threeButton.setSelected(false);
				buttonFour.setSelected(false);
				ButtonFri.setSelected(false);
				ButtonSat.setSelected(false);
				Buttonwnd.setSelected(true);
				ReflashFlag = true;
				break;
			default:
				break;
			}
			
			/*for(new set[]:myAppPlication.getSet().iterator())
			{
				//Log.w("set", w.getWeekString());
			}*/
		
			
			
			weekTextView.setText(weainfo.getWeekString());
			cityNameText.setText(weainfo.getCityNameString());
			//temp1Text.setText(weainfo.getCurTempString());
			temp2Text.setText(weainfo.getAllTempString());
			weatherDespText.setText(weainfo.getWeatherInfoString());
			publishText.setText(weainfo.getDate());
			//currentDateText.setText(prefs.getString("current_date", ""));
			//iconTextView.setText(prefs.getString("weather_icon", ""));
			windTextView.setText(weainfo.getWind());
			showImage(weainfo.getWeaicon());
			weatherInfoLayout.setVisibility(View.VISIBLE);
			cityNameText.setVisibility(View.VISIBLE);
			icon1.setVisibility(View.VISIBLE);
			relativeLayout.setVisibility(View.VISIBLE);
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
