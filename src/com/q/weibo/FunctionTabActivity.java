package com.q.weibo;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
public class FunctionTabActivity  extends TabActivity{
	 /**
     * TabHost控件
     */
    private TabHost mTabHost;

    /**
     * TabWidget控件
     */
    private TabWidget mTabWidget;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
        mTabHost = this.getTabHost();
        /* 去除标签下方的白线 */
        mTabHost.setPadding(mTabHost.getPaddingLeft(),
                mTabHost.getPaddingTop(), mTabHost.getPaddingRight(),
                mTabHost.getPaddingBottom() - 5);
        Resources rs = getResources();

        Intent layoutlocation = new Intent();
        layoutlocation.setClass(this, LocationActivity.class);
        TabHost.TabSpec layout1spec = mTabHost.newTabSpec("好友位置");
        layout1spec.setIndicator("好友位置",
                rs.getDrawable(android.R.drawable.stat_sys_phone_call));
        layout1spec.setContent(layoutlocation);
        mTabHost.addTab(layout1spec);

        Intent layoutmessage = new Intent();
        layoutmessage.setClass(this, MessageActivity.class);
        TabHost.TabSpec layout2spec = mTabHost.newTabSpec("发消息");
        layout2spec.setIndicator("发消息",
                rs.getDrawable(android.R.drawable.stat_sys_phone_call_forward));
        layout2spec.setContent(layoutmessage);
        mTabHost.addTab(layout2spec);

        Intent layoutnews = new Intent();
        layoutnews.setClass(this, NewsActivity.class);
        TabHost.TabSpec layout3spec = mTabHost.newTabSpec("新鲜事");
        layout3spec.setIndicator("新鲜事",
                rs.getDrawable(android.R.drawable.stat_sys_phone_call_on_hold));
        layout3spec.setContent(layoutnews);
        mTabHost.addTab(layout3spec);
        
        Intent layoutweibo = new Intent();
        layoutweibo.setClass(this, PublishWeiboActivity.class);
        TabHost.TabSpec layout4spec = mTabHost.newTabSpec("发微博");
        layout4spec.setIndicator("发微博",
                rs.getDrawable(android.R.drawable.stat_sys_phone_call_on_hold));
        layout4spec.setContent(layoutweibo);
        mTabHost.addTab(layout4spec);
        
        Intent layoutmore = new Intent();
        layoutmore.setClass(this, MoreActivity.class);
        TabHost.TabSpec layout5spec = mTabHost.newTabSpec("更多");
        layout5spec.setIndicator("更多",
                rs.getDrawable(android.R.drawable.stat_sys_phone_call_on_hold));
        layout5spec.setContent(layoutmore);
        mTabHost.addTab(layout5spec);

        /* 对Tab标签的定制 */
        mTabWidget = mTabHost.getTabWidget();
        for (int i = 0; i < mTabWidget.getChildCount(); i++)
        {
            /* 得到每个标签的视图 */
            View view = mTabWidget.getChildAt(i);
            /* 设置每个标签的背景 */
            if (mTabHost.getCurrentTab() == i)
            {
                view.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.number_bg_pressed));
            }
            else
            {
                view.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.number_bg));
            }
            /* 设置Tab间分割竖线的颜色 */
            // tabWidget.setBackgroundColor(Color.WHITE);
            /* 设置Tab间分割竖线的背景图片 */
            // tabWidget.setBackgroundResource(R.drawable.icon);
            /* 设置tab的高度 */
            mTabWidget.getChildAt(i).getLayoutParams().height = 75;
            TextView tv = (TextView) mTabWidget.getChildAt(i).findViewById(
                    android.R.id.title);
            /* 设置tab内字体的颜色 */
            tv.setTextColor(Color.rgb(49, 116, 171));
        }

        /* 当点击Tab选项卡的时候，更改当前Tab标签的背景 */
        mTabHost.setOnTabChangedListener(new OnTabChangeListener()
        {
            @Override
            public void onTabChanged(String tabId)
            {
                for (int i = 0; i < mTabWidget.getChildCount(); i++)
                {
                    View view = mTabWidget.getChildAt(i);
                    if (mTabHost.getCurrentTab() == i)
                    {
                        view.setBackgroundDrawable(getResources().getDrawable(
                                R.drawable.number_bg_pressed));
                    }
                    else
                    {
                        view.setBackgroundDrawable(getResources().getDrawable(
                                R.drawable.number_bg));
                    }
                }
            }
        });
    }
}
