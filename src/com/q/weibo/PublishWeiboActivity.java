package com.q.weibo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PublishWeiboActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publishweibo);
		final Button returnBtn=(Button)findViewById(R.id.publishweiboreturn);
		returnBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnBtn.setBackgroundResource(R.drawable.title_button_selected);
				Intent intent = new Intent(PublishWeiboActivity.this,FunctionTabActivity.class);				
				startActivity(intent);	
				finish();
			}
		});
	}
}
