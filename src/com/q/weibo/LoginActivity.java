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
import com.q.biz.store;
import com.q.model.UserInfo;
import com.q.store.DataHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
public class LoginActivity extends Activity{
	ProgressDialog pd;
	Object cookie;
	 private Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg){
				if(msg.what==0){
					pd.dismiss();
					displayToast("�û����������벻��Ϊ��");
				}
				else if(msg.what==1){
					 if(store.getCurrentUser()!=null){
				            DataHelper helper=new DataHelper(LoginActivity.this);
				            String uid=store.getCurrentUser().getId();
				            if(helper.HaveUserInfo(uid))
				            {
				                helper.UpdateUserInfo(store.getCurrentUser());
				                Log.e("UserInfo", "update");
				            }else
				            {
				                helper.SaveUserInfo(store.getCurrentUser());
				                Log.e("UserInfo", "add");
				            }
				        }
					pd.dismiss();
					
					Intent intent = new Intent(LoginActivity.this,FunctionTabActivity.class);				
					startActivity(intent);	
					finish();
				}
				else if(msg.what==2){
					pd.dismiss();
					displayToast("�û��������������");
				}
				else if(msg.what==3){
					pd.dismiss();
					displayToast("������������");
				}
			}
		};
	 public void onCreate(Bundle savedInstanceState) {

		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.login);
	        final Button btnLgn = (Button)findViewById(R.id.btnLogin);
	        btnLgn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					btnLgn.setBackgroundResource(R.drawable.regist_weibo_s);
					//pd = ProgressDialog.show(LoginActivity.this, "���Ժ�", "�������ӷ�����...", true, true);
					//login();
					Intent intent = new Intent(LoginActivity.this,FunctionTabActivity.class);				
					startActivity(intent);	
					finish();
				}
			});		 
	 }
	  //���������ӷ��������е�¼
	    public void login(){
	    	new Thread(){
	    		public void run(){
	    			Looper.prepare();
					try{					
						EditText etUid = (EditText)findViewById(R.id.etUid);	//����ʺ�EditText
						EditText etPwd = (EditText)findViewById(R.id.etPwd);	//�������EditText
						String uid = etUid.getEditableText().toString().trim();	//���������ʺ�
						String pwd = etPwd.getEditableText().toString().trim();	//������������
						if(uid.equals("") || pwd.equals("")){	
							 Message t=new Message();
							 t.what=0;
							 handler.sendMessage(t);						
							 return;										
						}
						             
						String httpUrl="http://192.168.1.104:9999/q/login";
						HttpPost httpRequest=new HttpPost(httpUrl);
						List<NameValuePair>params=new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("username", uid));
						params.add(new BasicNameValuePair("password", pwd));
						params.add(new BasicNameValuePair("from", ""));
						
						try
						
						{
							//�����ַ���
							HttpEntity httpentity = new UrlEncodedFormEntity(params, "gb2312");
							//����httpRequest
							httpRequest.setEntity(httpentity);
							//ȡ��Ĭ�ϵ�HttpClient
							DefaultHttpClient httpclient = new DefaultHttpClient();
							//ȡ��HttpResponse
							httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
							
							httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

							HttpResponse httpResponse = httpclient.execute(httpRequest);
							//HttpStatus.SC_OK��ʾ���ӳɹ�
                            
							if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
							{
								rememberMe(uid,pwd);
								UserInfo now=new UserInfo();
								store.setCurrentUser(now);
								
								store.setCookieStore(httpclient.getCookieStore());
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
						    t.what=3;
							handler.sendMessage(t);
						}
						catch (IOException e)
						{
							Message t=new Message();
						    t.what=3;
							handler.sendMessage(t);
						}
						catch (Exception e)
						{
							Message t=new Message();
						    t.what=3;
							handler.sendMessage(t);
						}  

					}
	                           catch(Exception e){
	                        	   Message t=new Message();
	   						    t.what=3;
	   							handler.sendMessage(t);
					}
	    		}
	    	}.start();
	    }
	    //���������û���id���������Preferences
	    public void rememberMe(String uid,String pwd){
	    	SharedPreferences sp = getPreferences(MODE_PRIVATE);	//���Preferences
	    	SharedPreferences.Editor editor = sp.edit();			//���Editor
	    	editor.putString("uid", uid);							//���û�������Preferences
	    	editor.putString("pwd", pwd);	
	    	editor.putString("cookie",cookie.toString());
	    	//���������Preferences
	    	editor.commit();
	        
	    }
	    public void displayToast(String str){
	    	Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
	    }
}
