package com.coolweather.app.receiver;

import com.coolweather.app.service.AutoUpdateServece;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoUpdateReceiver extends BroadcastReceiver {
	
		public void onReceive(Context context,Intent intent){
			Intent i = new Intent(context,AutoUpdateServece.class);
			context.startService(i);
		}
}
