package com.q.weibo;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.q.model.OverItemInfo;

import com.q.biz.store;
import com.q.model.LocationInfo;
import com.q.store.DataHelper;
import com.q.util.ImageFilter;
import com.q.util.Location;

public class LocationActivity extends MapActivity{
	private MapView	 mMapView;
	private MapController mMapController; 
	ProgressDialog pd;
	private Location locationHelp;
	private boolean gps=false;
	private List<Overlay> mapList;
	private Context context;
	/**
	  * 弹出的气泡View
	  */
	 private View popView;

	 private final ItemizedOverlay.OnFocusChangeListener onFocusChangeListener = new ItemizedOverlay.OnFocusChangeListener() 
	 {
		  @Override
		  public void onFocusChanged(ItemizedOverlay overlay, OverlayItem newFocus) 
		  {
		      //创建气泡窗口
			  if (popView != null) {
				  popView.setVisibility(View.GONE);
			  }
			  
			  if (newFocus != null) {
				  MapView.LayoutParams geoLP = (MapView.LayoutParams) popView.getLayoutParams();
				  GeoPoint mPoint = newFocus.getPoint();
				  int lat = mPoint.getLatitudeE6();
				  int log = mPoint.getLongitudeE6();
				  geoLP.point = new GeoPoint(lat, log);//这行用于popView的定位
				  
				  TextView title = (TextView) popView.findViewById(R.id.map_bubbleTitle);
				  title.setText(newFocus.getTitle());

				  TextView desc = (TextView) popView.findViewById(R.id.map_bubbleText);
				  if (newFocus.getSnippet() == null || newFocus.getSnippet().length() == 0) {
					  desc.setVisibility(View.GONE);
				  } else {
					  desc.setVisibility(View.VISIBLE);
					  desc.setText(newFocus.getSnippet());
				  }
				  mMapView.updateViewLayout(popView, geoLP);
				  popView.setVisibility(View.VISIBLE);
			  }
		  }
	 };
	 private void drawtemp(LocationInfo nowPlace){
		GeoPoint nowGeoPoint = new GeoPoint((int) (nowPlace.getLatitude() * 1000000), (int) (nowPlace.getLongitude()* 1000000));
		//GeoPoint nowGeoPoint = new GeoPoint((int) (31.2689792 * 1000000), (int) (121.4881002* 1000000));
	    mMapController.animateTo(nowGeoPoint);
		List<LocationInfo> list = new ArrayList<LocationInfo>();
		nowPlace.setTitle("我的位置");
		if(nowPlace.getAccuracy()!=null){
			if(nowPlace.getStreetNumber()!=null){
		      nowPlace.setContent("准确度:"+nowPlace.getAccuracy()+"米,"+nowPlace.getCityName()+" "+nowPlace.getStreetName()+nowPlace.getStreetNumber()+"附近");
			}
			else{
			  nowPlace.setContent("准确度:"+nowPlace.getAccuracy()+"米,"+nowPlace.getCityName()+" "+nowPlace.getStreetName());
			}
		}
		else{
			nowPlace.setContent("数据未知");
		}
		nowPlace.setMgeoPoint(nowGeoPoint);
		
		list.add(nowPlace);
	
		InterestingLocations funPlaces = new InterestingLocations(this.context , list,marker);
		mapList.clear();
		mapList.add(funPlaces);
		funPlaces.setOnFocusChangeListener(onFocusChangeListener);
	 }
	private Handler locationHandler=new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what==0){
				if(pd!=null){
				pd.dismiss();}
				LocationInfo nowPlace=locationHelp.GetNowInfo();				
				if(nowPlace!=null){				
					drawtemp(nowPlace);
         		    displayToast(nowPlace.getAccuracy()+" "+nowPlace.getLatitude()+" "+nowPlace.getLongitude()+" "+nowPlace.getStreetName());			
					if(store.getCurrentUser()!=null){
				            DataHelper helper=new DataHelper(LocationActivity.this);
				            String uid=store.getCurrentUser().getId();
				            if(helper.HaveUserInfo(uid))				            	
				            {
				            	store.getCurrentUser().setLatitude(msg.getData().getDouble("latitude"));
				            	store.getCurrentUser().setLongitude(msg.getData().getDouble("Longitude"));
				                helper.UpdateUserInfo(store.getCurrentUser());
				              //   LocationInfo me=new LocationInfo();
	                          //  updateMyLocation();
				            }
				        }
				}				
				displayToast("取到了地理位置信息");
			}
			else if(msg.what==1){				
				if(pd.isShowing()){
					pd.dismiss();}
				displayToast("请稍候再试");
			}
			else if(msg.what==2){
				if(pd.isShowing()){
					pd.dismiss();}
				displayToast("请检查网络连接");
			}
		}
	};
	Drawable marker;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
		
           this.context=this;
	mMapView = (MapView) findViewById(R.id.mapview);
	mMapView.setStreetView(false);
	//取得MapController对象(控制MapView)
	mMapController = mMapView.getController(); 
	mMapView.setEnabled(true);
	mMapView.setClickable(true);
	//设置地图支持缩放
	mMapView.setBuiltInZoomControls(true); 
	
	mMapController.setZoom(15); 
    mapList = mMapView.getOverlays();   
	locationHelp=new Location(this,locationHandler);
	
	LocationInfo nowPlace=updateMyLocation();
	
	  //初始化气泡,并设置为不可见
	LayoutInflater inflater = LayoutInflater.from(this);
    popView = inflater.inflate(R.layout.overlay_pop, null);
    mMapView.addView( popView, new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT, MapView.LayoutParams.WRAP_CONTENT,
 				   null, MapView.LayoutParams.BOTTOM_CENTER));
       //由于我的气泡的尾巴是在下边居中的,因此要设置成MapView.LayoutParams.BOTTOM_CENTER.
       //这里没有给GeoPoint,在onFocusChangeListener中设置
    popView.setVisibility(View.GONE);
   
    Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.pin_border)).getBitmap();
	Bitmap bitmap1 = ((BitmapDrawable) getResources().getDrawable(R.drawable.beauty)).getBitmap();	
	marker = new BitmapDrawable(ImageFilter.createBitmap(bitmap, bitmap1, 0));
	marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());		
	if(nowPlace!=null){
		drawtemp(nowPlace);
	}
	Button updateLocation=(Button)findViewById(R.id.updatelocation);
	updateLocation.setOnClickListener(new OnClickListener(){
		public void onClick(View v){
			LocationInfo nowPlace2=updateMyLocation();
			if(nowPlace2!=null){
				drawtemp(nowPlace2);
			}
		}	
	});
}
protected boolean isRouteDisplayed()
{
	return false;
}
  

  public void displayToast(String str){
  	Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
  }
  public LocationInfo updateMyLocation(){
	  pd = ProgressDialog.show(LocationActivity.this, "请稍候", "正在获取地址信息...", true, true);
	  LocationInfo now= locationHelp.getLocation();
	  return now;//null
  }


  class InterestingLocations extends ItemizedOverlay {
		private List<OverlayItem> locations = new ArrayList<OverlayItem>();
		private Context mContext;
		private Drawable marker;
		List<LocationInfo> mlist;
		public InterestingLocations( Context context, List<LocationInfo> list,Drawable marker)
		{
			//super(marker);
			super(boundCenterBottom(marker));
			mContext = context;
			this.mlist = list;
			this.marker = marker;
			for(int i=0;i<list.size();i++)
			{
				locations.add(new OverlayItem(list.get(i).MgeoPoint , list.get(i).Title, list.get(i).Content));
			}
			populate();
		}		
		//添加OverlayItem对象

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {			
			super.draw(canvas, mapView, false);
			boundCenterBottom(this.marker);
		}		
		@Override
		protected boolean onTap(int index) {
			 return true; 
		}
		
		@Override
		protected OverlayItem createItem(int i) {
			return locations.get(i);
		}
		
		@Override
		public int size() {
			return locations.size();
		}
	}
}
