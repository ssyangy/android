package com.q.util;

import java.util.List;

import com.q.model.WeiBoHolder;
import com.q.model.WeiBoInfo;
import com.q.util.AsyncImageLoader.ImageCallback;
import com.q.weibo.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeiBoAdapater extends BaseAdapter{
	private List<WeiBoInfo> wbList;
    private AsyncImageLoader asyncImageLoader;
    private Context context;
    @Override
    public int getCount() {
        return wbList.size();
    }

    @Override
    public Object getItem(int position) {
        return wbList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
     
    public WeiBoAdapater(Context from,List<WeiBoInfo> wbList){
    	this.wbList=wbList;
    	this.context=from;
    }
  
    public View getView(int position, View convertView, ViewGroup parent) {
        asyncImageLoader = new AsyncImageLoader();
        convertView = LayoutInflater.from(context).inflate(R.layout.weibo, null);
        WeiBoHolder wh = new WeiBoHolder();
        wh.wbicon = (ImageView) convertView.findViewById(R.id.wbicon);
        wh.wbtext = (TextView) convertView.findViewById(R.id.wbtext);
        wh.wbtime = (TextView) convertView.findViewById(R.id.wbtime);
        wh.wbuser = (TextView) convertView.findViewById(R.id.wbuser);
        wh.wbimage=(ImageView) convertView.findViewById(R.id.wbimage);
        WeiBoInfo wb = wbList.get(position);
        if(wb!=null){
            convertView.setTag(wb.getId());
            wh.wbuser.setText(wb.getUserName());
            wh.wbtime.setText(wb.getTime());
            wh.wbtext.setText(wb.getText(), TextView.BufferType.SPANNABLE);
            textHighlight(wh.wbtext,new char[]{'#'},new char[]{'#'});
            textHighlight(wh.wbtext,new char[]{'@'},new char[]{':',' '});
            textHighlight2(wh.wbtext,"http://"," ");
            
            if(wb.getHaveImage()){
                wh.wbimage.setImageResource(R.drawable.images);
            }
            Drawable cachedImage = asyncImageLoader.loadDrawable(wb.getUserIcon(),wh.wbicon, new ImageCallback(){

                @Override
                public void imageLoaded(Drawable imageDrawable,ImageView imageView, String imageUrl) {
                    imageView.setImageDrawable(imageDrawable);
                }
                
            });
             if (cachedImage == null) {
                 wh.wbicon.setImageResource(R.drawable.default_head);
                }else{
                    wh.wbicon.setImageDrawable(cachedImage);
                }
        }
        
        return convertView;
    }

	private void textHighlight(TextView wbtext, char[] cs, char[] cs2) {
		// TODO Auto-generated method stub
		
	}

	private void textHighlight2(TextView wbtext, String string, String string2) {
		// TODO Auto-generated method stub
		
	}
}