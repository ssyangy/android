package com.q.biz;

import org.apache.http.client.CookieStore;

import com.q.model.UserInfo;

public class store {
	static CookieStore cookies;
	static UserInfo currentuser;
	public static CookieStore getCookieStore(){
	return cookies;
	}
	public static void setCookieStore(CookieStore store){
		cookies=store;
	}
	public static UserInfo getCurrentUser(){
		return currentuser;
	}
	public static void setCurrentUser(UserInfo now){
		currentuser=now;
	}
}
