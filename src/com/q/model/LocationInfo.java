package com.q.model;

import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;

public class LocationInfo {
	private String cityName;
	private String streetName;
	private String regionName;
    private String countyName;
    private double latitude;
    private double longitude;
    private String accuracy;
    private String streetNmuber;
    private String kind;//me,friend
	public GeoPoint MgeoPoint;
	public String Title;
	public String Content;
	Drawable PhotoIcon;
	public long time;
	public String getStreetNumber(){
		return this.streetNmuber;
	}
	public void setStreetNumber(String streetnumber){
		this.streetNmuber=streetnumber;
	}
	public long getTime(){
		return time;
	}
	public void setTime(long time){
		this.time=time;
	}
	public GeoPoint getMgeoPoint() {
		return MgeoPoint;
	}
	public void setMgeoPoint(GeoPoint mgeoPoint) {
		MgeoPoint = mgeoPoint;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public Drawable getPhotoIcon() {
		return PhotoIcon;
	}
	public void setPhotoIcon(Drawable photoIcon) {
		PhotoIcon = photoIcon;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

}
