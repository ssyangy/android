package com.q.weibo;
import java.io.IOException;
import com.q.biz.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class PublishWeiboActivity extends Activity {
	ProgressDialog pd;
	String userId= null;
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what==0){
				pd.dismiss();
				displayToast("您还没有输入任何内容");
			}
			else if(msg.what==1){
				displayToast ("发表成功!");
				pd.dismiss();				
				Intent intent = new Intent(PublishWeiboActivity.this,FunctionTabActivity.class);				
				startActivity(intent);	
				finish();
			}
			else if(msg.what==2){
				pd.dismiss();
				displayToast("请检查网络连接");
			}

		}
	};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publishweibo);
		final Button returnBtn=(Button)findViewById(R.id.publishweiboreturn);
		returnBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnBtn.setBackgroundResource(R.drawable.title_button_selected);
				Intent intent = new Intent(PublishWeiboActivity.this,FunctionTabActivity.class);				
				startActivity(intent);	
				finish();
			}
		});
		final Button sendBtn=(Button)findViewById(R.id.publishweibosend);
		sendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnBtn.setBackgroundResource(R.drawable.title_button_selected);
				 pd = ProgressDialog.show(PublishWeiboActivity.this, "请稍候", "正在发布微博...", true, true);
			
				send();
			}
		});
	}
 public void send(){
	 new Thread(){
 		public void run(){
 			Looper.prepare();
				try{			
		 EditText textinput= (EditText)findViewById(R.id.newweibo);	
         String text=textinput.getEditableText().toString();
         if(text.equals("") ){	
        	 Message t=new Message();
			 t.what=0;
			 handler.sendMessage(t);						
			 return;					
		}
     	String httpUrl="http://192.168.1.104:9999/q/weibo";
		HttpPost httpRequest=new HttpPost(httpUrl);
		List<NameValuePair>params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("content", text));
		params.add(new BasicNameValuePair("from", ""));
		//httpRequest.addHeader("Content-Type", "text/xml"); 
		//httpRequest.addHeader("Accept","application/json");
		try
		
		{
			//设置字符集
			HttpEntity httpentity = new UrlEncodedFormEntity(params, "UTF-8");
			//请求httpRequest
			httpRequest.setEntity(httpentity);
			//取得默认的HttpClient
			DefaultHttpClient httpclient = new DefaultHttpClient();
			//请求超时
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
			//读取超时
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
			//MyCookieStore appState = ((MyCookieStore)getApplicationContext());    
			CookieStore tempstore = store.getCookieStore(); 
			httpclient.setCookieStore(tempstore);
			//取得HttpResponse			
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			//HttpStatus.SC_OK表示连接成功
         
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				Message t=new Message();
			    t.what=1;
				handler.sendMessage(t);
			}
			else
			{
				Message t=new Message();
			    t.what=2;
				handler.sendMessage(t);
			}
		}
		catch (ClientProtocolException e)
		{
			Message t=new Message();
		    t.what=2;
			handler.sendMessage(t);
			//displayToast("a"+e.getMessage().toString());
		}
		catch (IOException e)
		{
			Message t=new Message();
		    t.what=2;
			handler.sendMessage(t);
		//	displayToast("b"+e.getMessage().toString());
		}
		catch (Exception e)
		{
			Message t=new Message();
		    t.what=2;
			handler.sendMessage(t);
			//displayToast("c"+e.getMessage().toString());
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
     public void displayToast(String str){
 	  Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }
}
