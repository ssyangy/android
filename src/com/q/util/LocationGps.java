package com.q.util;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.app.Activity;
public class LocationGps {
    LocationManager lm;
    private  Location now;

	public  Location getLocation(){
		return now;
	}
    public  void LocationGps(Context context){
    	lm=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
    	
    	
    	 Location location = lm 
         .getLastKnownLocation(LocationManager.GPS_PROVIDER);  
          if (location == null) {  
          location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);  
 }  
    	
    	String bestProvider=lm.getBestProvider(getCriteria(), true);
    	now=lm.getLastKnownLocation(bestProvider);
    }

	public  Criteria getCriteria(){
    	Criteria c=new Criteria();
    	c.setAccuracy(Criteria.ACCURACY_COARSE);
    	c.setSpeedRequired(false);
    	c.setCostAllowed(false);
    	c.setBearingRequired(false);
    	c.setAltitudeRequired(false);
    	c.setPowerRequirement(Criteria.POWER_LOW);
    	return c;
    }
}
