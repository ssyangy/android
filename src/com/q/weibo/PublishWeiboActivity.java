package com.q.weibo;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
public class PublishWeiboActivity extends Activity {
	   public static final String MIME_TYPE_IMAGE_JPEG = "image/jpeg"; 
	    public static final int ACTIVITY_GET_IMAGE = 0; 
	    public static final String KEY_FILE_NAME = "name"; 
	    public static final String KEY_FILE_TYPE = "type"; 
	    public static final String KEY_FILE_BITS = "bits"; 
	    public static final String KEY_FILE_OVERWRITE = "overwrite"; 
	    public static final String KEY_FILE_URL = "url"; 
	 
	    private byte[] mContent;
	ProgressDialog pd;
	String userId= null;
	private String tempImgPath=null;
	final int LIST_DIALOG = 2;			//声明列表对话框的id
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
	/*
	public static Intent getPhotoPickIntent(){
		Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		///intent.putExtra("crop","true");
		//intent.putExtra("aspectX",2);
		//intent.putExtra("aspectY",1);
		//intent.putExtra("outputX", 80);
		//intent.putExtra("outputY", 100);
		intent.putExtra("return-data", true);
		return intent;
		
	}*/
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publishweibo);
		displayToast("xxx");
		final Button returnBtn=(Button)findViewById(R.id.publishweiboreturn);
		returnBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnBtn.setBackgroundResource(R.drawable.title_button_selected);
				//Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
				//tempImgPath="/sdcard/dcim/Camera" + System.currentTimeMillis() + ".jpg";
				//it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(tempImgPath)));

				//startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);
	
				//	showDialog(LIST_DIALOG);			//显示列表对话框
					if(store.isImgExist()==true){
						showDialog(3);
					}
					else{
						showDialog(LIST_DIALOG);			//显示列表对话框
					}

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
	@Override
	protected Dialog onCreateDialog(int id) {	
	
		Dialog dialog = null;
	
		switch(id){		
		case LIST_DIALOG:
			Builder b = new AlertDialog.Builder(this);		//创建Builder对象
			b.setIcon(R.drawable.set);					//设置图标
			b.setTitle(R.string.set);	
			//设置标题
			displayToast(String.valueOf(store.isImgExist()));
			b.setItems(										//设置列表中的各个属性
					R.array.weiboMenu, 							//字符串数组
					new DialogInterface.OnClickListener() {	//为列表设置OnClickListener监听器
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if(which==0){
								Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
								startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);
							}
							else if(which==1){
								//final Intent intent=getPhotoPickIntent();
								//startActivityForResult(intent,3021 );
								 Intent getImage = new Intent(Intent.ACTION_GET_CONTENT); 
							        getImage.addCategory(Intent.CATEGORY_OPENABLE); 
							        getImage.setType(MIME_TYPE_IMAGE_JPEG); 
							        startActivityForResult(getImage, ACTIVITY_GET_IMAGE); 
							}
							else if(which==2){
								
							}
					
						}
					});
			dialog=b.create();		
			//生成Dialog对象
		
			
			
			break;
			
		case 3:
			Builder bs = new AlertDialog.Builder(this);		//创建Builder对象
			bs.setIcon(R.drawable.set);					//设置图标
			bs.setTitle(R.string.set);	
			//设置标题
			displayToast(String.valueOf(store.isImgExist()));
			bs.setItems(										//设置列表中的各个属性
					R.array.weiboMenu2, 							//字符串数组
					new DialogInterface.OnClickListener() {	//为列表设置OnClickListener监听器
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if(which==0){
								Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
								startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);
							}
							else if(which==1){
								//final Intent intent=getPhotoPickIntent();
								//startActivityForResult(intent,3021 );
								 Intent getImage = new Intent(Intent.ACTION_GET_CONTENT); 
							        getImage.addCategory(Intent.CATEGORY_OPENABLE); 
							        getImage.setType(MIME_TYPE_IMAGE_JPEG); 
							        startActivityForResult(getImage, ACTIVITY_GET_IMAGE); 
							}
							else if(which==2){
								
							}
                          else if(which==3){
                        	  LinearLayout layout=(LinearLayout)findViewById(R.id.weiboInsert);
                              layout.setVisibility(View.GONE);
                 
                              store.setImgExist(false);
							}
						}
					});
			dialog=bs.create();
			break;		
		default:
			break;			
		}
		return dialog;									//返回Dialog对象
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
     /*
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         try{
            // ImageView img = (ImageView)findViewById(R.id.image);
       //     Bitmap take = U.ResizeBitmap(U.getBitmapForFile(tempImgPath), 640);
           //  img.setImageBitmap(take);
          //   imgflag = true;
         }catch(Exception e){
         }
    }*/
     public static Bitmap getPicFromBytes(byte[] bytes, 
             BitmapFactory.Options opts) {

         if (bytes != null) 
             if (opts != null) 
                 return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, 
                         opts); 
             else 
                 return BitmapFactory.decodeByteArray(bytes, 0, bytes.length); 
         return null; 
     }

     public static byte[] getBytesFromInputStream(InputStream is, int bufsiz) 
             throws IOException { 
         int total = 0; 
         byte[] bytes = new byte[4096]; 
         ByteBuffer bb = ByteBuffer.allocate(bufsiz);

         while (true) { 
             int read = is.read(bytes); 
             if (read == -1) 
                 break; 
             bb.put(bytes, 0, read); 
             total += read; 
         }

         byte[] content = new byte[total]; 
         bb.flip(); 
         bb.get(content, 0, total);

         return content; 
     }
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	 if (resultCode != RESULT_OK) {

             return;

         }

         Bitmap bm = null;

         ContentResolver resolver = getContentResolver();
         
         if (requestCode == ACTIVITY_GET_IMAGE) {

             try { 
                 //获得图片的uri 
                 Uri originalUri = data.getData(); 
                 //将图片内容解析成字节数组 
                 mContent = getBytesFromInputStream(resolver.openInputStream(Uri 
                         .parse(originalUri.toString())), 3500000); 
                 //将字节数组转换为ImageView可调用的Bitmap对象 
                 bm = getPicFromBytes(mContent, null); 
                 LinearLayout layout=(LinearLayout)findViewById(R.id.weiboInsert);
                 layout.setVisibility(View.VISIBLE);
                 ImageView iv=(ImageView)findViewById(R.id.weiboImg);
                 iv.setImageBitmap(bm);
                 store.setImgExist(true);
                 displayToast(bm.getHeight()+" "+bm.getWidth());
             } catch (IOException e) { 
                 System.out.println(e.getMessage()); 
             }

         }
         super.onActivityResult(requestCode, resultCode, data);
         try{
             Bundle extras = data.getExtras();
             Bitmap b = (Bitmap) extras.get("data");
            // take = b;
            // ImageView img = (ImageView)findViewById(R.id.image);
            // img.setImageBitmap(take);
             LinearLayout layout=(LinearLayout)findViewById(R.id.weiboInsert);
             layout.setVisibility(View.VISIBLE);
             ImageView iv=(ImageView)findViewById(R.id.weiboImg);
             iv.setImageBitmap(b);
             store.setImgExist(true);
             displayToast(b.getHeight()+" "+b.getWidth());
         }catch(Exception e){
         }
 
    }
     public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
         super.onCreateContextMenu(menu, v, menuInfo);
         menu.add(0, 0, 0, "相册");  //添加两个菜单项
         menu.add(0, 1, 0, "拍照");   
     }
}
