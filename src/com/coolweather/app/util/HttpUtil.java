package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.coolweather.app.activity.WeatherActivity;
import com.coolweather.app.model.weather;

import coolweather.app.myAppPlication;

import android.R.bool;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

class PersonComparator implements Comparator<weather>{  
	 
    @Override  
    public int compare(weather o1, weather o2) {  
          
       return o1.id - o2.id;  
    }  
      
}  
public class HttpUtil {
	
	private static void parseXMLWithPull(InputStream is) {
	try {
		//Set<weather> weathers = null;
		 Set<weather> weathers = null;
		weather Weatherinfo = null;
		
		
	XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	XmlPullParser xmlPullParser = factory.newPullParser();
	//InputStream is = new ByteArrayInputStream(xmlData.getBytes());
	
	xmlPullParser.setInput(is,"utf-8");
	
	//String[] s = {"item_0","item_1","item_2","item_3","item_4","item_5","item_6"};
	/*BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	StringBuffer response = new StringBuffer();
	String line;
	Log.w("read line","read line start" );
	while ((line = reader.readLine())!=null) {
		
		response.append(line);
	}
	Log.w("while", response.toString());
	String[] s = {"item_0","item_1","item_2","item_3","item_4","item_5","item_6"}; */
	
	int eventType = xmlPullParser.getEventType();
	int id = 0;
	/*String name = "";
	String version = "";*/
	String weekString = "";
	String cityName = "";
	String curTemp = "";
	String allTemp = "";
	String wind = "";
	String date = "";
	String weainfo = "";
	String weather_icon = "";
	String days = "";
	String weathercode = "";
	/*if (!weathers.isEmpty()) {
		weathers.clear();
	}*/
	while (eventType != XmlPullParser.END_DOCUMENT) {
	String nodeName = xmlPullParser.getName();
	
	switch (eventType) {
	// 开始解析某个结点
	case XmlPullParser.START_DOCUMENT:
		// Weatherinfo = new weather();
		weathers = new TreeSet<weather>(new PersonComparator());
		
		break;
	case XmlPullParser.START_TAG: {
			
		/*for (int i = 0; i < s.length; i++)*/ {
			if ("item_0".equals(nodeName)||"item_1".equals(nodeName)
					||"item_2".equals(nodeName)||"item_3".equals(nodeName)
					||"item_4".equals(nodeName)||"item_5".equals(nodeName)||"item_6".equals(nodeName)) 
			/*if (s[id].equals(nodeName))*/
			{
				Weatherinfo = new weather();
				Weatherinfo.setId(id+1);
				
			}
			else if ("week".equals(nodeName)) {
				weekString = xmlPullParser.nextText();
				Weatherinfo.setWeekString(weekString);
				
			}
			else if ("citynm".equals(nodeName)) {
				cityName = xmlPullParser.nextText();
				Weatherinfo.setCityNameString(cityName);
				
			}
			else if ("temperature_curr".equals(nodeName)) {
				curTemp = xmlPullParser.nextText();
				Weatherinfo.setCurTempString(curTemp);
				//++id;
				
			}
			else if ("temperature".equals(nodeName)) {
				allTemp = xmlPullParser.nextText();
				Weatherinfo.setAllTempString(allTemp);
				//++id;

			}
			else if ("weather".equals(nodeName)) {
				weainfo = xmlPullParser.nextText();
				Weatherinfo.setWeatherInfoString(weainfo);
				//++id;
			}
			else if ("wind".equals(nodeName)) {
				wind = xmlPullParser.nextText();
				Weatherinfo.setWind(wind);
				//++id;

			}
			else if ("weather_icon".equals(nodeName)) {
				weather_icon = xmlPullParser.nextText();
				Weatherinfo.setWeaicon(weather_icon);
				//++id;

			}
			else if ("days".equals(nodeName)) {
				days = xmlPullParser.nextText();
				Weatherinfo.setDate(days);
				//++id;
			}
			else if ("cityid".equals(nodeName)) {
				weathercode = xmlPullParser.nextText();
				Weatherinfo.setWeatherCodeString(weathercode);
				//++id;
			}
			
		}
	break;
	}
	case XmlPullParser.END_TAG: {
		
	if ("item_0".equals(nodeName)||"item_1".equals(nodeName)
			||"item_2".equals(nodeName)||"item_3".equals(nodeName)
			||"item_4".equals(nodeName)||"item_5".equals(nodeName)||"item_6".equals(nodeName)) 
		//if (s[id].equals(nodeName))
		{
	  weathers.add(Weatherinfo);
	  myAppPlication.setwaether(weathers);
	 // for(weather w:myAppPlication.getSet().toString())
		{	
			id++;
			//Log.w("myAppPlication",myAppPlication.getSet().toString());
		}
	  Weatherinfo = null;
	 
		}
	break;
	}
	default:
	break;
	}
	eventType = xmlPullParser.next();
	}
	
	} catch (Exception e) {
	e.printStackTrace();
	}
	}
	public  static void sendHttpRequest(final String address,final boolean send,final HttpCallbackListener listener){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//Log.w("run", "run ok");
				HttpURLConnection connection = null;
				try {
						/*URL url = new URL(address);
						connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("GET");
						connection.setConnectTimeout(8000);
						connection.setReadTimeout(8000);
						InputStream inputStream = connection.getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
						StringBuffer response = new StringBuffer();
						String line;
						Log.w("read line","read line start" );
						while ((line = reader.readLine())!=null) {
							Log.w("while", "in111111111");
							response.append(line);
						}*/
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(address);
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
					// 请求和响应都成功了
						
					HttpEntity entity = httpResponse.getEntity();
					if (send) {
						String response = EntityUtils.toString(entity,"utf-8");
						if (listener != null) {
							//parseXMLWithPull(response);
							//Log.w("run response",response.toString());
							listener.onFinish(response.toString());
						}
					}
					else {
						
						String response = EntityUtils.toString(entity,"utf-8");
							if (listener != null) {
							
							listener.onFinish(response.toString());
							//Log.w("run response",response.toString());
						}
					}
					
										
						
						//Log.w("run response",response.toString());
					}
				} catch (Exception e) {
					// TODO: handle exception
					if (listener != null) {
						e.printStackTrace();
						listener.onError(e);
					}
					
				}
				finally{
					if (connection != null) {
						connection.disconnect();
					}
				}
				
			}
		}).start();
	}
public  static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//Log.w("run", "run ok");
				HttpURLConnection connection = null;
				try {
						URL url = new URL(address);
						connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("GET");
						connection.setConnectTimeout(8000);
						connection.setReadTimeout(8000);
						InputStream inputStream = connection.getInputStream();
						//InputStream inputStream1 =	inputStream ;
						parseXMLWithPull(inputStream);
						
						if (listener != null) {
							//parseXMLWithPull(response);
						
							listener.onFinish("ok");
						}			
						
						//Log.w("run response",response.toString());
					}
				 catch (Exception e) {
					// TODO: handle exception
					if (listener != null) {
						e.printStackTrace();
						listener.onError(e);
					}
					
				}
				finally{
					if (connection != null) {
						connection.disconnect();
					}
				}
				
			}
		}).start();
	}

public static Bitmap getUrlImage(String url) {   

    Bitmap img = null;   
    try {   
        URL picurl = new URL(url);   
        // 获得连接   
        HttpURLConnection conn = (HttpURLConnection)picurl.openConnection();   
        conn.setConnectTimeout(6000);//设置超时   
        conn.setDoInput(true);   
        conn.setUseCaches(false);//不缓存   
        conn.connect();  
      /*  File sddirFile = null;
        sddirFile = Environment.getExternalStorageDirectory();
        String pathString = sddirFile.toString() + "/" +"sky.png";
        File filenew = new File(pathString);
        Log.w("path", pathString);
        if(!filenew.exists())  
        {
        	if(filenew.createNewFile());  
        	 //System.out.println("创建成功");  
        	Log.w("create", "create file ok");
        }

       */
        InputStream is = conn.getInputStream();//获得图片的数据流   
       /* FileOutputStream fileout = new FileOutputStream(filenew);
        byte [] c = new byte[1024];
        int a = 0;
        while ((a = is.read(c))!=-1) {
			fileout.write(c);
			Log.w("photo", "1111");
		}*/
        img = BitmapFactory.decodeStream(is);   
        is.close();  
        //fileout.close();
    } catch (Exception e) {   
        e.printStackTrace();   
    }   
    return img;   
} 

class getSet{
	
	public  Set<weather> listSet; 
	
	public  Set<weather> getSet()
	{
		return listSet;
	}
	public void setwaether(Set<weather> listSet)
	{
		this.listSet = listSet;
	}
}


}


