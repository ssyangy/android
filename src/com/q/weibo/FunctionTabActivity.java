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
     * TabHost�ؼ�
     */
    private TabHost mTabHost;

    /**
     * TabWidget�ؼ�
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
        /* ȥ����ǩ�·��İ��� */
        mTabHost.setPadding(mTabHost.getPaddingLeft(),
                mTabHost.getPaddingTop(), mTabHost.getPaddingRight(),
                mTabHost.getPaddingBottom() - 5);
        Resources rs = getResources();

        Intent layoutlocation = new Intent();
        layoutlocation.setClass(this, LocationActivity.class);
        TabHost.TabSpec layout1spec = mTabHost.newTabSpec("����λ��");
        layout1spec.setIndicator("����λ��",
                rs.getDrawable(android.R.drawable.stat_sys_phone_call));
        layout1spec.setContent(layoutlocation);
        mTabHost.addTab(layout1spec);

        Intent layoutmessage = new Intent();
        layoutmessage.setClass(this, MessageActivity.class);
        TabHost.TabSpec layout2spec = mTabHost.newTabSpec("����Ϣ");
        layout2spec.setIndicator("����Ϣ",
                rs.getDrawable(android.R.drawable.stat_sys_phone_call_forward));
        layout2spec.setContent(layoutmessage);
        mTabHost.addTab(layout2spec);

        Intent layoutnews = new Intent();
        layoutnews.setClass(this, NewsActivity.class);
        TabHost.TabSpec layout3spec = mTabHost.newTabSpec("������");
        layout3spec.setIndicator("������",
                rs.getDrawable(android.R.drawable.stat_sys_phone_call_on_hold));
        layout3spec.setContent(layoutnews);
        mTabHost.addTab(layout3spec);
        
        Intent layoutweibo = new Intent();
        layoutweibo.setClass(this, PublishWeiboActivity.class);
        TabHost.TabSpec layout4spec = mTabHost.newTabSpec("��΢��");
        layout4spec.setIndicator("��΢��",
                rs.getDrawable(android.R.drawable.stat_sys_phone_call_on_hold));
        layout4spec.setContent(layoutweibo);
        mTabHost.addTab(layout4spec);
        
        Intent layoutmore = new Intent();
        layoutmore.setClass(this, MoreActivity.class);
        TabHost.TabSpec layout5spec = mTabHost.newTabSpec("����");
        layout5spec.setIndicator("����",
                rs.getDrawable(android.R.drawable.stat_sys_phone_call_on_hold));
        layout5spec.setContent(layoutmore);
        mTabHost.addTab(layout5spec);

        /* ��Tab��ǩ�Ķ��� */
        mTabWidget = mTabHost.getTabWidget();
        for (int i = 0; i < mTabWidget.getChildCount(); i++)
        {
            /* �õ�ÿ����ǩ����ͼ */
            View view = mTabWidget.getChildAt(i);
            /* ����ÿ����ǩ�ı��� */
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
            /* ����Tab��ָ����ߵ���ɫ */
            // tabWidget.setBackgroundColor(Color.WHITE);
            /* ����Tab��ָ����ߵı���ͼƬ */
            // tabWidget.setBackgroundResource(R.drawable.icon);
            /* ����tab�ĸ߶� */
            mTabWidget.getChildAt(i).getLayoutParams().height = 75;
            TextView tv = (TextView) mTabWidget.getChildAt(i).findViewById(
                    android.R.id.title);
            /* ����tab���������ɫ */
            tv.setTextColor(Color.rgb(49, 116, 171));
        }

        /* �����Tabѡ���ʱ�򣬸��ĵ�ǰTab��ǩ�ı��� */
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
