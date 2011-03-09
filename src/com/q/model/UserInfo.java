package com.q.model;

import android.graphics.drawable.Drawable;

public class UserInfo {
	public static final String ID="_id";
	public static final String USERID="userId";
	public static final String TOKEN="token";
	public static final String TOKENSECRET="tokenSecret";
	public static final String USERNAME="userName";
	public static final String USERICON="userIcon";

	private String id;
	private String userId;//”√ªßid
	private String token;
	private String password;
	private String userName;
	private Drawable userIcon;

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

