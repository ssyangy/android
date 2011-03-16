package com.q.util;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import com.q.model.GpsInfo;
import com.q.model.LocationInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
public class Location {
	LocationInfo info;
	Handler handler;
   public Location(Context context,Handler handler){
	this.handler=handler;
   } 
   public JSONObject getGps(GpsInfo data){
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
   public JSONObject getWifiCell(GpsInfo data){
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
	    info=new LocationInfo();
	   try {
		JSONObject inner=object.getJSONObject("location");
		info.setLatitude(inner.getString("latitude"));
		info.setLongitude(inner.getString("longitude"));
		info.setAccuracy(inner.getString("accuracy"));
		JSONObject address=object.getJSONObject("address");
		info.setStreetName(address.getString("street_number"));
		info.setCityName(address.getString("city"));
		info.setRegionName(address.getString("region"));
		info.setCountyName(address.getString("country"));
		return true;
	  } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
   }
}
