package com.q.model;

import android.graphics.drawable.Drawable;

public class UserInfo {
	public static final String ID="_id";
	public static final String USERID="userId";
	public static final String TOKEN="token";
	public static final String TOKENSECRET="tokenSecret";
	public static final String USERNAME="userName";
	public static final String USERICON="userIcon";
	public static final String LATITUDE="latitude";
	public static final String LONGITUDE="longitude";

	private String id;
	private String userId;//”√ªßid
	private String token;
	private String password;
	private String userName;
	private Drawable userIcon;
	private double latitude;
	private double longitude;
	
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
	
	public String getId(){
	return id;
	}

	public void setId(String id){
	this.id=id;
	}

	public String getUserId() {
	return userId;
	}

	public void setUserId(String userId) {
	this.userId = userId;
	}

	public String getToken() {
	return token;
	}

	public void setToken(String token) {
	this.token = token;
	}

	public String getPassword() {
	return password;
	}

	public void setTokenSecret(String tokenSecret) {
	this.password = tokenSecret;
	}

	public String getUserName() {
	return userName;
	}

	public void setUserName(String userName) {
	this.userName = userName;
	}

	public Drawable getUserIcon() {
	return userIcon;
	}

	public void setUserIcon(Drawable userIcon) {
	this.userIcon = userIcon;
	}
	}

