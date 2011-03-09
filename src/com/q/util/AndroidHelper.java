package com.q.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class AndroidHelper {
	  //获取屏幕方向
    public static int ScreenOrient(Activity activity)
        {
            int orient = activity.getRequestedOrientation(); 
            if(orient != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE && orient != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                //宽>高为横屏,反正为竖屏  
                 WindowManager windowManager = activity.getWindowManager();  
                 Display display = windowManager.getDefaultDisplay();  
                 int screenWidth  = display.getWidth();  
                 int screenHeight = display.getHeight();  
                 orient = screenWidth < screenHeight ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            }
            return orient;
        }
    public static void AutoBackground(Activity activity,View view,int Background_v, int Background_h)
    {
        int orient=ScreenOrient(activity);
        if (orient == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) { //纵向 
            view.setBackgroundResource(Background_v);
        }else{ //横向
            view.setBackgroundResource(Background_h);
        }  
    }
}
