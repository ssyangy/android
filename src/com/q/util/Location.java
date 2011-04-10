package com.q.util;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.q.model.CellTowerInfo;
import com.q.model.GpsInfo;
import com.q.model.LocationInfo;
import com.q.model.WifiTowerInfo;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
public class Location {
	LocationInfo info;
	LocationInfo gpsInfo;
	Handler handler;
	Context context;
	LocationGps gps;
	private Handler gpsHandler=new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what==0){	
				android.location.Location temp=gps.getLocation();
				gpsInfo=new LocationInfo();
				gpsInfo.setTime(System.currentTimeMillis());
				DecimalFormat df = new DecimalFormat("0.#");
				if(temp.hasAccuracy()){
				gpsInfo.setAccuracy(df.format(temp.getAccuracy()));
				}
				gpsInfo.setLatitude(temp.getLatitude());
				gpsInfo.setLongitude(temp.getLongitude());		
				postDate( getGps(temp));
			}
	}};
	public LocationInfo GetNowInfo(){
		if(gpsInfo!=null&info!=null){
        if(Math.abs(gpsInfo.getTime()-info.getTime())<60000){
        	if(gpsInfo!=null){
        		return gpsInfo;
        	}
        	else if(info!=null){
        		return info;
        	}
        }
		}
        	return info;

	}
	
   public Location(Context context,Handler handler){
	 this.handler=handler;
	 this.context=context;
	
   }
   public void displayToast(String str){
	  	Toast.makeText(context,str,Toast.LENGTH_SHORT).show();	  
   }
   
   
   
   
   public JSONObject getGps(android.location.Location data){
	   JSONObject object=new JSONObject();
	   JSONObject inner=new JSONObject();
	   try {
		inner.put("latitude", data.getLatitude());
		inner.put("longitude", data.getLongitude());
		object.put("version", "1.1.0");
	    object.put("host", "maps.google.com");
	    object.put("request_address", true);
	    object.put("address_language", "zh_CN");
	    object.put("location",inner );
		 return object;
	   } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		 return object;
	   }   	 
   }
   public JSONObject getGsmWifi(ArrayList<CellTowerInfo> celldata,ArrayList<WifiTowerInfo> wifidata){	  
	   
	   JSONObject object=new JSONObject();
	   JSONArray wifis=new JSONArray();
	   JSONArray cells=new JSONArray();
	   try {
	    object.put("version", "1.1.0");
	    object.put("host", "maps.google.com");
	    object.put("radio_type","gsm");
	    object.put("request_address", true);
	    object.put("address_language", "zh_CN");
		 for(int i=0;i<wifidata.size();i++){
			 JSONObject temp=new JSONObject();
			 temp.put("mac_address", wifidata.get(i).getMac());
			 temp.put("ssid", wifidata.get(i).getName());
			 if(wifidata.get(i).getSignal_strength()<0){
				 temp.put("signal_strength", wifidata.get(i).getSignal_strength());
			 }
			 wifis.put(temp);
		 }
		 for(int i=0;i<celldata.size();i++){
			 JSONObject temp=new JSONObject();
			 temp.put("cell_id", celldata.get(i).getCid());
			 temp.put("location_area_code", celldata.get(i).getLac());
			 if(celldata.get(i).getSignal_strength()<0){
				 temp.put("signal_strength", celldata.get(i).getSignal_strength());
			 }
			 cells.put(temp);
		 }
		 object.put("cell_towers", cells);
		object.put("wifi_towers", wifis);
	    return object;
	   } catch (JSONException e) {
			e.printStackTrace();
			 return object;
		   }    
   }
   public JSONObject getCdmaWifi(ArrayList<CellTowerInfo> celldata,ArrayList<WifiTowerInfo> wifidata){	  
	   JSONObject object=new JSONObject();
	   JSONArray wifis=new JSONArray();
	   JSONArray cells=new JSONArray();
	   try {
	    object.put("version", "1.1.0");
	    object.put("host", "maps.google.com");
	    object.put("radio_type","cdma");
	    object.put("request_address", true);
	    object.put("address_language", "zh_CN");
		 for(int i=0;i<wifidata.size();i++){
			 JSONObject temp=new JSONObject();
			 temp.put("mac_address", wifidata.get(i).getMac());
			 temp.put("ssid", wifidata.get(i).getName());
			 if(wifidata.get(i).getSignal_strength()<0){
				 temp.put("signal_strength", wifidata.get(i).getSignal_strength());
			 }
			 wifis.put(temp);
		 }
		 for(int i=0;i<celldata.size();i++){
			 JSONObject temp=new JSONObject();
			 temp.put("cell_id", celldata.get(i).getBid());
			 temp.put("location_area_code", celldata.get(i).getNid());
			 temp.put("mobile_network_code", celldata.get(i).getSid());
			 if(celldata.get(i).getSignal_strength()<0){
				 temp.put("signal_strength", celldata.get(i).getSignal_strength());
			 }
			 cells.put(temp);
		 }
		 object.put("cell_towers", cells);
		object.put("wifi_towers", wifis);
	    return object;
	   } catch (JSONException e) {
			e.printStackTrace();
			 return object;
		   }    
   }
   public JSONObject getWifi(ArrayList<WifiTowerInfo> data){
	   JSONObject object=new JSONObject();
	   JSONArray wifis=new JSONArray();
	   try {
	    object.put("version", "1.1.0");
	    object.put("host", "maps.google.com");
	    object.put("request_address", true);
	    object.put("address_language", "zh_CN");
		 for(int i=0;i<data.size();i++){
			 JSONObject temp=new JSONObject();
			 temp.put("mac_address", data.get(i).getMac());
			 temp.put("ssid", data.get(i).getName());
			 if(data.get(i).getSignal_strength()<0){
				 temp.put("signal_strength", data.get(i).getSignal_strength());
			 }
			 wifis.put(temp);
		 }
		 
		object.put("wifi_towers", wifis);
	    return object;
	   } catch (JSONException e) {
			e.printStackTrace();
			 return object;
		   }  
   }
   public JSONObject getGsmCell(ArrayList<CellTowerInfo> data){
	     JSONObject object=new JSONObject();
	     JSONArray cells=new JSONArray();
	   
	     try {
	     object.put("version", "1.1.0");
		 object.put("host", "maps.google.com");
		 object.put("radio_type","gsm");
		 object.put("request_address", true);
		 object.put("address_language", "zh_CN");
	 
		 for(int i=0;i<data.size();i++){
			 JSONObject temp=new JSONObject();
			 temp.put("cell_id", data.get(i).getCid());
			 temp.put("location_area_code", data.get(i).getLac());
			 if(data.get(i).getSignal_strength()<0){
				 temp.put("signal_strength", data.get(i).getSignal_strength());
			 }
			 cells.put(temp);
			// displayToast("cell_id:"+data.get(i).getCid()+
		//			 "location_area_code:"+data.get(i).getLac()+"signal_strength:"+data.get(i).getSignal_strength());
			
		 }
		 object.put("cell_towers", cells);
		
		 return object;
		   } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 return object;
		   }   	 
	   }
   
   public JSONObject getCdmaCell(ArrayList<CellTowerInfo> data){
	     JSONObject object=new JSONObject();
	     JSONArray cells=new JSONArray();
	     try {
	     object.put("version", "1.1.0");
		 object.put("host", "maps.google.com");
		 object.put("radio_type","cdma");
		 object.put("request_address", true);
		 object.put("address_language", "zh_CN");
		
		 for(int i=0;i<data.size();i++){
			 JSONObject temp=new JSONObject();
			 temp.put("cell_id", data.get(i).getBid());
			 temp.put("location_area_code", data.get(i).getNid());
			 temp.put("mobile_network_code", data.get(i).getSid());
			 if(data.get(i).getSignal_strength()<0){
				 temp.put("signal_strength", data.get(i).getSignal_strength());
			 }
			 cells.put(temp);
		 }
		 object.put("cell_towers", cells);
		 return object;
		   } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 return object;
		   }   	 
	   }

   public void postDate(final JSONObject object){
	   new Thread(){
	 		public void run(){
	 			Looper.prepare();
					try{	
		String httpUrl="http://www.google.com/loc/json";
		HttpPost httpRequest=new HttpPost(httpUrl);
		// 绑定到请求 Entry
		StringEntity se = new StringEntity(object.toString(),"UTF-8");
		httpRequest.setEntity(se);
		//取得默认的HttpClient
		DefaultHttpClient httpclient = new DefaultHttpClient();
		//请求超时
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
		//读取超时
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
		HttpResponse httpResponse = httpclient.execute(httpRequest);
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
		{
			// 得到应答的字符串，这也是一个 JSON 格式保存的数据
			String retSrc = EntityUtils.toString(httpResponse.getEntity());
			JSONObject result = new JSONObject(retSrc);
			analysisData(result);
			Message t=new Message();
			t.what=0;
			handler.sendMessage(t);
		}
		else
		{	
			Message t=new Message();
		    t.what=1;
		    handler.sendMessage(t);
		}
	    }
	    catch(Exception e){
	    	Message t=new Message();
		    t.what=2;
		    handler.sendMessage(t);
	    }
	}
  }.start();
}

   public boolean analysisData(JSONObject object){	  
	   try {
		JSONObject inner=object.getJSONObject("location");
		if(inner.has("accuracy")){
		info=new LocationInfo();
		info.setLatitude(Double.parseDouble(inner.getString("latitude")));
		info.setLongitude(Double.parseDouble(inner.getString("longitude")));
		info.setAccuracy(inner.getString("accuracy"));
		JSONObject address=inner.getJSONObject("address");
		if(address.has("street")){
		info.setStreetName(address.getString("street"));
		}
		if(address.has("city")){
		info.setCityName(address.getString("city"));
		}
		if(address.has("region")){
		info.setRegionName(address.getString("region"));
		}
		if(address.has("county")){
		info.setCountyName(address.getString("county"));
		}
		if(address.has("street_number")){
		info.setStreetNumber(address.getString("street_number"));
		}
		info.setTime(System.currentTimeMillis());
		}
		else{
			JSONObject address=inner.getJSONObject("address");
			if(address.has("street")){
			gpsInfo.setStreetName(address.getString("street"));
			}
			if(address.has("city")){
			gpsInfo.setCityName(address.getString("city"));
			}
			if(address.has("region")){
			gpsInfo.setRegionName(address.getString("region"));
			}
			if(address.has("county")){
				gpsInfo.setCountyName(address.getString("county"));
				}
				if(address.has("street_number")){
					gpsInfo.setStreetNumber(address.getString("street_number"));
			}
		}

		return true;
	  } catch (JSONException e) {

		e.printStackTrace();
		return false;
	}
   }
   //先取gps的经纬度,若取不到,再取基站
    //若是gsm,发送数据 return null
    //若是cdma,能直接取到经纬度,就return
               //否则先去google查询,return null
     //若前面的方法都无法使用,最后采取wifi mac,return null
   public LocationInfo getLocation(){
	   gps=new LocationGps(context,gpsHandler);
	   gps.startGetData(context);
	   LocationInfo temp=new LocationInfo();
	   LocationWifi wifi=new LocationWifi(context);
	   if(gps.isGpsEnabled()){
	   android.location.Location l1=gps.getLocation();
	   }
		   LocationCellTower l2=new LocationCellTower(context);
		   if(l2.getNetworkType()==2){
			   if(wifi.isWifiOn()){					  
				   postDate(getGsmWifi(l2.getNearGsm(),wifi.getNearWifi()));
				   return null;
			   }
			   postDate(getGsmCell(l2.getNearGsm()));
			   return null;
		   }
		   else if(l2.getNetworkType()==4){
			   if(wifi.isWifiOn()){					  
				   postDate(getCdmaWifi(l2.getNearCdma(),wifi.getNearWifi()));
				   return null;
			   }
			   return null;
		   }
		   else if(wifi.isWifiOn()){
			   postDate(getWifi(wifi.getNearWifi()));
			   return null;
		   }
	   displayToast("请打开相关服务再试");
	   return null;	   
   }
   public void getFriends(){
	   new Thread(){
	 		public void run(){
	 			Looper.prepare();
					try{	
		String httpUrl="http://192.168.1.104:9999/q/getfriends";
		HttpPost httpRequest=new HttpPost(httpUrl);

		//取得默认的HttpClient
		DefaultHttpClient httpclient = new DefaultHttpClient();
		//请求超时
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
		//读取超时
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
		HttpResponse httpResponse = httpclient.execute(httpRequest);
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
		{
			// 得到应答的字符串，这也是一个 JSON 格式保存的数据
			String retSrc = EntityUtils.toString(httpResponse.getEntity());
			JSONObject result = new JSONObject(retSrc);
			//分析朋友的json数据!!
			Message t=new Message();
			t.what=3;
			handler.sendMessage(t);
		}
		else
		{	
			Message t=new Message();
		    t.what=4;
		    handler.sendMessage(t);
		}
	    }
	    catch(Exception e){
	    	Message t=new Message();
		    t.what=5;
		    handler.sendMessage(t);
	    }
	}
 }.start();
   }
  
}
	
