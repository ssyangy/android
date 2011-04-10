package com.q.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;



public class ImageFilter {
 
	public static Bitmap toGrayscale(Bitmap bmpOriginal) 
	{         
	    int width, height; 
	    height = bmpOriginal.getHeight(); 
	    width = bmpOriginal.getWidth();     
	 
	    Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565); 
	    Canvas c = new Canvas(bmpGrayscale); 
	    Paint paint = new Paint(); 
	    ColorMatrix cm = new ColorMatrix(); 
	    cm.setSaturation(0); 
	    ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm); 
	    paint.setColorFilter(f); 
	    c.drawBitmap(bmpOriginal, 0, 0, paint); 
	    return bmpGrayscale; 
	} 
	
	//Create photo icon start
	/**  
	 * create the bitmap from a byte array  
	 * 
	 * @param src the bitmap object you want proecss 
	 * @param watermark the water mark above the src 
	 * @return return a bitmap object ,if paramter's length is 0,return null 
	 */  
	public static Bitmap createBitmap(Bitmap src, Bitmap watermark, int num)   
	{
		if( src == null )   
	    {   
	    	return null;   
	    }   
	  
	    int mWidth = src.getWidth();   
	    int mHeight = src.getHeight();   
	    
	    int width = watermark.getWidth();
	    int height = watermark.getHeight();
	    // 设置想要的大小
	    int newWidth = 50;
	    int newHeight = 50;
	    // 计算缩放比例
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    // 取得想要缩放的matrix参数
	    Matrix matrix = new Matrix();
	    matrix.postScale(scaleWidth, scaleHeight);
	    // 得到新的图片
	    Bitmap newbm1 = Bitmap.createBitmap(watermark, 0, 0, width, height, matrix,
	      true);
	    Bitmap newbm = null;
	    if(num==1||num==4)
	    {
	    	newbm = ImageFilter.toGrayscale(newbm1);
	    }
	    else
	    {
	    	newbm = newbm1;
	    }

	    //create the new blank bitmap   
	    Bitmap newb = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_4444);
	    Canvas canvas = new Canvas(newb);   
	    //draw src into  
	    Paint paint = new Paint(Color.GRAY);
	    canvas.drawBitmap( src, 0, 0, paint);
	    //draw watermark into   
	    canvas.drawBitmap(newbm, 5, 5 , paint);
	    //save all clip   
	    canvas.save( Canvas.ALL_SAVE_FLAG );  
	    //store   
	    canvas.restore();   
	    return newb;   
	} 
	//Create photo icon end
}