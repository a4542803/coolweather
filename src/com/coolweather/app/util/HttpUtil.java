package com.coolweather.app.util;

import java.net.HttpURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpUtil {
	
	public  static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.w("run", "run ok");
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
					String response = EntityUtils.toString(entity,
							"utf-8");
						Log.w("listener11111111111111","response.toString()");
						if (listener != null) {
							Log.w("listener","response.toString()");
							listener.onFinish(response.toString());
						}
						Log.w("run response",response.toString());
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



}


