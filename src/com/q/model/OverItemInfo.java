package com.q.model;

import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;

public class OverItemInfo {

	public GeoPoint MgeoPoint;
	public String Title;
	public String Content;
	Drawable PhotoIcon;
	
	public OverItemInfo(GeoPoint geoPoint, String title, String content, Drawable icon)
	{
		this.MgeoPoint = geoPoint;
		this.Title = title;
		this.Content = content;
		this.PhotoIcon = icon;
	}
}
