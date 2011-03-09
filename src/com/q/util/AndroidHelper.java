package com.q.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class AndroidHelper {
	  //��ȡ��Ļ����
    public static int ScreenOrient(Activity activity)
        {
            int orient = activity.getRequestedOrientation(); 
            if(orient != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE && orient != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                //��>��Ϊ����,����Ϊ����  
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
        if (orient == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) { //���� 
            view.setBackgroundResource(Background_v);
        }else{ //����
            view.setBackgroundResource(Background_h);
        }  
    }
}
