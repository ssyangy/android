package com.q.weibo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.q.biz.store;
import com.q.util.*;

public class LoadingActivity extends Activity {
	Object cookie;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout);
      //背景自动适应
        AndroidHelper.AutoBackground(this, layout, R.drawable.about_background_land, R.drawable.about_background_portriat);
        checkIfRemember();
    }
  //方法：从Preferences中读取用户名和密码
    public void checkIfRemember(){
    	SharedPreferences sp = getPreferences(MODE_PRIVATE);	//获得Preferences
    	String uid = sp.getString("uid", null);
    	String pwd = sp.getString("pwd", null);
    	if(uid != null && pwd!= null){
    		login(uid,pwd);
    	}
    	else{
    		Intent intent = new Intent(LoadingActivity.this,LoginActivity.class);
			startActivity(intent);
			finish();
    	}
    }
    
    private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what==0){
				Intent intent = new Intent(LoadingActivity.this,FunctionTabActivity.class);
				startActivity(intent);
				finish();
			}
			else if(msg.what==1){
				Intent intent = new Intent(LoadingActivity.this,LoginActivity.class);
				startActivity(intent);
				displayToast("请检查网络连接");
				finish();
			}

		}
	};
    
    public void login(final String uid,final String pwd){
    	new Thread(){
    		public void run(){
    			Looper.prepare();
				try{					
					             
					String httpUrl="http://192.168.1.104:9999/q/login";
					HttpPost httpRequest=new HttpPost(httpUrl);
					List<NameValuePair>params=new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("username", uid));
					params.add(new BasicNameValuePair("password", pwd));
					params.add(new BasicNameValuePair("from", ""));
					
					try
					
					{
						//设置字符集
						HttpEntity httpentity = new UrlEncodedFormEntity(params, "gb2312");
						//请求httpRequest
						httpRequest.setEntity(httpentity);
						//取得默认的HttpClient
						DefaultHttpClient httpclient = new DefaultHttpClient();
						httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
						
						httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
						//取得HttpResponse
						HttpResponse httpResponse = httpclient.execute(httpRequest);
						//HttpStatus.SC_OK表示连接成功

						if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
						{
				
							store.setCookieStore(httpclient.getCookieStore());
							Message t=new Message();
						    t.what=0;
							handler.sendMessage(t);
						}
						else
						{ Message t=new Message();
					    t.what=1;
						handler.sendMessage(t);
						}
					}
					catch (ClientProtocolException e)
					{
						Log.e("ClientProtocolException",e.getMessage().toString());
						 Message t=new Message();
						    t.what=1;
							handler.sendMessage(t);
		
					}
					catch (IOException e)
					{
						Log.e("IOException",e.getMessage().toString());
						 Message t=new Message();
						    t.what=1;
							handler.sendMessage(t);
					}
					catch (Exception e)
					{
						Log.e("Exception",e.getMessage().toString());
						 Message t=new Message();
						    t.what=1;
							handler.sendMessage(t);
					}  
	
				}
                         catch(Exception e){
                        	 Message t=new Message();
     					    t.what=1;
     						handler.sendMessage(t);
				}
    		}
    	}.start();
    }
    public void displayToast(String str){
    	Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

}