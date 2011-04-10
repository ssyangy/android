package com.q.util;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import java.util.Timer;
import java.util.TimerTask;
public class LocationGps {
    LocationManager lm;
    private   Location now;
    private   boolean isGpsEnabled;
    private  LocationListener listener;
    private Handler gpsHandler;
    public boolean isGpsEnabled(){
    	return this.isGpsEnabled;
    }
	public  Location getLocation(){
           return now;
	}
	public void startGetData(Context context){
		 lm=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);  
    	 if (lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
   		 isGpsEnabled=true;
   		 String provider = lm.getBestProvider(getCriteria(), true); // 获取GPS信息
   		 lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);  
   		 now = lm.getLastKnownLocation(provider); // 通过GPS获取位置
   		 Timer timer=new Timer();
   		 TimerTask myStopTask=new stopGps(lm);
   		 timer.schedule(myStopTask, 40000);
   	 }
     
   	 isGpsEnabled=false;
   	 now=null;  
	}
	private  final  LocationListener locationListener=new LocationListener(){
	
	        public void onLocationChanged(Location location) {
	        	now=location;
	        	Message t=new Message();
				t.what=0;
				gpsHandler.sendMessage(t);
				lm.removeUpdates(locationListener);
				lm=null;
	        }

	
			public void onProviderDisabled(String arg0) {
				isGpsEnabled=false;
				
			}

			public void onProviderEnabled(String arg0) {
				isGpsEnabled=true;
				
			}

			public void onStatusChanged(String arg0, int arg1,
					Bundle arg2) {
				
			}
	};
    public   LocationGps(Context context,Handler gpsHandler){
    	startGetData(context);
    	this.gpsHandler=gpsHandler;
    }
	public  Criteria getCriteria(){
    	Criteria c=new Criteria();
    	c.setAccuracy(Criteria.ACCURACY_COARSE);
    	c.setSpeedRequired(false);
    	c.setCostAllowed(false);
    	c.setBearingRequired(false);
    	c.setAltitudeRequired(false);
    	c.setPowerRequirement(Criteria.POWER_HIGH);
    	return c;
    }
	 class stopGps extends TimerTask{
        private LocationManager lms;
		public stopGps(LocationManager lm){
			this.lms=lm;
		}
		public void run() {
			if(lms!=null){
			lms.removeUpdates(locationListener);
			lms=null;
			}
		}
		
	}
}
