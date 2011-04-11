package com.q.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

 public class LocationNetwork {
	 private Context context;
	 private Handler handler;
	 LocationManager lm;
	 private   Location now;
	 private boolean isNetworkEnabled;
    public   LocationNetwork(Context context,Handler networkHandler){
    	this.context=context;
    	this.handler=networkHandler;
   
    }
    public boolean isNetworkEnabled(){
    	return this.isNetworkEnabled;
    }
    public Location getNetworkLocation(){
    	return now;
    }
    public void getLocation(){
    	lm=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);  
    	if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
    		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1,locationListener);
    		now=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    		isNetworkEnabled=true;
    		
    	}
    	else{
    	isNetworkEnabled=false;
    	}
    }
	private  final  LocationListener locationListener=new LocationListener(){
		
        public void onLocationChanged(Location location) {
        	
        	now=location;
		
        }


		public void onProviderDisabled(String arg0) {
			isNetworkEnabled=false;
			
		}

		public void onProviderEnabled(String arg0) {
			isNetworkEnabled=true;
			
		}

		public void onStatusChanged(String arg0, int arg1,
				Bundle arg2) {
			
		}
};
}
