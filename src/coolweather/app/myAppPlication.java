package coolweather.app;

import java.util.Set;

import com.coolweather.app.model.weather;

import android.app.Application;

public class myAppPlication extends Application {

	public static Set<weather> listSet; 
	
	public static Set<weather> getSet()
	{
		return listSet;
	}
	public static void setwaether(Set<weather> listSet1)
	{
		listSet = listSet1;
	}
}
