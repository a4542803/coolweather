package com.coolweather.app.activity;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.R;
import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.Country;
import com.coolweather.app.model.Province;
import com.coolweather.app.model.city;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.*;
import com.coolweather.app.util.Utility;



import android.R.integer;
//import android.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseAreaActivity extends Activity {
	
	public static final int LEVEL_PROVNINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;
	private boolean isFromWeatherActivity;
	private ProgressDialog progressDialog;
	private TextView titleText;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private CoolWeatherDB coolWeatherDB;
	private List<String> datalist = new ArrayList<String>();
	//private List<String> dataList = new ArrayList<String>();
	/**
	* 省列表
	*/
	private List<Province> provinceList;
	/**
	* 市列表
	*/
	private List<city> cityList;
	/**
	* 县列表
	*/
	private List<Country> countyList;
	/**
	* 选中的省份
	*/
	private Province selectedProvince;
	/**
	* 选中的城市
	*/
	private city selectedCity;
	/**
	* 当前选中的级别
	*/
	private int currentLevel;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);				
		isFromWeatherActivity = getIntent().getBooleanExtra("from_weather_activity", false);
		
		SharedPreferences prefs = PreferenceManager.
				getDefaultSharedPreferences(this);
		
		Log.w("chooseon",""+isFromWeatherActivity+prefs.getBoolean("city_selected", false));
				if (prefs.getBoolean("city_selected", false)&&isFromWeatherActivity) {
				Intent intent = new Intent(this, WeatherActivity.class);
				startActivity(intent);
				finish();
				return;
				}
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		listView = (ListView) findViewById(R.id.list_view);
		titleText = (TextView) findViewById(R.id.title_text);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datalist);
		listView.setAdapter(adapter);
		coolWeatherDB = CoolWeatherDB.getInstance(this);
		//Log.w("choose thread",Thread.currentThread().getName());
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?>arg0,View view,int index,long arg3){
				if (currentLevel == LEVEL_PROVNINCE) {
					
					selectedProvince = provinceList.get(index);
					queryCities();
				} else if (currentLevel == LEVEL_CITY) {
				selectedCity = cityList.get(index);
				queryCounties();
				
				}
				else if (currentLevel == LEVEL_COUNTY) {
					String countyCode = countyList.get(index).getCountryCodeString();
					Intent intent = new Intent(ChooseAreaActivity.this,
					WeatherActivity.class);
					intent.putExtra("county_code", countyCode);
					Log.w("open", "open weather");
					startActivity(intent);
					Log.w("open", "open weather1111111");
					finish();
				}
			}
		});
		queryProvinces();
	}
	private void queryProvinces(){
		provinceList = coolWeatherDB.loadProvinces();
		if (provinceList.size()>0) {
			datalist.clear();
			for(Province province:provinceList)
			{
				datalist.add(province.getProvinceName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText("中国");
			currentLevel = LEVEL_PROVNINCE;
		}else {
			queryFromServer(null,"province");
		}
	}
	/**
	* 查询选中省内所有的市，优先从数据库查询，如果没有查询到再去服务器上查询。
	*/
	private void queryCities() {
	cityList = coolWeatherDB.loadCities(selectedProvince.getId());
	if (cityList.size() > 0) {
	datalist.clear();
	for (city City : cityList) {
	datalist.add(City.getCityName());
	}
	
	adapter.notifyDataSetChanged();
	listView.setSelection(0);
	titleText.setText(selectedProvince.getProvinceName());
	currentLevel = LEVEL_CITY;
	} 
	else {
	queryFromServer(selectedProvince.getProvinceCodeString(), "city");
		}
	}
	private void queryCounties() {
		countyList = coolWeatherDB.loadCounties(selectedCity.getId());
		if (countyList.size() > 0) {
		datalist.clear();
		for (Country county : countyList) {
		datalist.add(county.getCountryName());
		
		}
		adapter.notifyDataSetChanged();
		listView.setSelection(0);
		titleText.setText(selectedCity.getCityName());
		currentLevel = LEVEL_COUNTY;
		} else {
		queryFromServer(selectedCity.getCityCode(), "county");
		}
		}
	private void queryFromServer(final String code, final String type){
		
		String address;
		if (!TextUtils.isEmpty(code)) {
			
			address = address = "http://www.weather.com.cn/data/list3/city" + code +
					".xml";
		}else {
			
			address = "http://www.weather.com.cn/data/list3/city.xml";
			
		}
		showProgressDialog();
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
		//	@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				boolean result = false;
				if ("province".equals(type)) {
					result = Utility.handleProvincesResponse(coolWeatherDB, response);
				}else if ("city".equals(type)) {
					
					result = Utility.handleCitiesResponse(coolWeatherDB,
					response, selectedProvince.getId());
					} else if ("county".equals(type)) {
						
					result = Utility.handleCountiesResponse(coolWeatherDB,
					response, selectedCity.getId());
					}
				if (result) {
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							closeProgressDialog();
							if ("province".equals(type)) {
								queryProvinces();
							}else if ("city".equals(type)) {
								queryCities();
							}else if ("county".equals(type)) {
								
								queryCounties();
							}
							
						}
					});
				}
			}
			
			//@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						closeProgressDialog();
						Toast.makeText(ChooseAreaActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
	private void showProgressDialog() {
		if (progressDialog == null) {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("正在加载...");
		progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
		}
		/**
		* 关闭进度对话框
		*/
		private void closeProgressDialog() {
		if (progressDialog != null) {
		progressDialog.dismiss();
			}
		}
		@Override
		public void onBackPressed() {
		if (currentLevel == LEVEL_COUNTY) {
		queryCities();
		} else if (currentLevel == LEVEL_CITY) {
		queryProvinces();
		}else {
			if (isFromWeatherActivity) {
				Intent intent = new Intent(this, WeatherActivity.class);
				startActivity(intent);
				}
				finish();
		}
		}
}
