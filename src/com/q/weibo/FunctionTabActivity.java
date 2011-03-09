package com.q.weibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class FunctionTabActivity extends Activity{
	private LinearLayout home,information, me, search, more;
	LayoutInflater inflater;
	LinearLayout head;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.functiontab);
		inflater = LayoutInflater.from(this);
		head = (LinearLayout) findViewById(R.id.head);
		headhome();
		home = (LinearLayout) findViewById(R.id.home);
		home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				home.setBackgroundResource(R.drawable.home_btn_bg_d);
				information.setBackgroundResource(R.drawable.home_btn_bg_n);
				me.setBackgroundResource(R.drawable.home_btn_bg_n);
				search.setBackgroundResource(R.drawable.home_btn_bg_n);
				more.setBackgroundResource(R.drawable.home_btn_bg_n);
			}
		});
		home.setBackgroundResource(R.drawable.home_btn_bg_d);
		information=(LinearLayout)findViewById(R.id.information);
		information.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				home.setBackgroundResource(R.drawable.home_btn_bg_n);
				information.setBackgroundResource(R.drawable.home_btn_bg_d);
				me.setBackgroundResource(R.drawable.home_btn_bg_n);
				search.setBackgroundResource(R.drawable.home_btn_bg_n);
				more.setBackgroundResource(R.drawable.home_btn_bg_n);
			}
		});
		me=(LinearLayout)findViewById(R.id.me);
		me.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				home.setBackgroundResource(R.drawable.home_btn_bg_n);
				information.setBackgroundResource(R.drawable.home_btn_bg_n);
				me.setBackgroundResource(R.drawable.home_btn_bg_d);
				search.setBackgroundResource(R.drawable.home_btn_bg_n);
				more.setBackgroundResource(R.drawable.home_btn_bg_n);
			}
		});
		search=(LinearLayout)findViewById(R.id.search);
		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				home.setBackgroundResource(R.drawable.home_btn_bg_n);
				information.setBackgroundResource(R.drawable.home_btn_bg_n);
				me.setBackgroundResource(R.drawable.home_btn_bg_n);
				search.setBackgroundResource(R.drawable.home_btn_bg_d);
				more.setBackgroundResource(R.drawable.home_btn_bg_n);
			}
		});
		more=(LinearLayout)findViewById(R.id.more);
		more.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				home.setBackgroundResource(R.drawable.home_btn_bg_n);
				information.setBackgroundResource(R.drawable.home_btn_bg_n);
				me.setBackgroundResource(R.drawable.home_btn_bg_n);
				search.setBackgroundResource(R.drawable.home_btn_bg_n);
				more.setBackgroundResource(R.drawable.home_btn_bg_d);
			}
		});
		final Button newblog=(Button)findViewById(R.id.newblog);
		newblog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				newblog.setBackgroundResource(R.drawable.title_button_selected);
				Intent intent = new Intent(FunctionTabActivity.this,PublishWeiboActivity.class);				
				startActivity(intent);	
				finish();
			}
		});		 
	}
	public void headhome(){
		 RelativeLayout layout = (RelativeLayout) inflater.inflate(
                 R.layout.headhome, null).findViewById(R.id.headhome);
		 head.removeAllViews();
		 head.addView(layout);

	}
}
