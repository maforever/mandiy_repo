package com.lanrui.andiy.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CompressImage {

	public  static Bitmap compressImage(String path) {
		//对图片进行压缩  
		           BitmapFactory.Options options = new BitmapFactory.Options();  
		           options.inJustDecodeBounds = true;  
		             
		           //获取这个图片的宽和高  
		           Bitmap bitmap = BitmapFactory.decodeFile(path,options);//此时返回bm为空  
		           options.inJustDecodeBounds =false;  
		           //计算缩放比  
		           int be = (int)(options.outHeight / (float)200);  
		           if(be <= 0)  
		                be =1;  
		           options.inSampleSize =be;  
		           //重新读入图片，注意这次要把options.inJustDecodeBounds设为false哦  
		           bitmap = BitmapFactory.decodeFile(path,options);  
		           int w = bitmap.getWidth();  
		           int h=bitmap.getHeight();  
		           //System.out.println(w+" "+h);  
		           //myImageView.setImageBitmap(bitmap);  
		             return bitmap;
		             
		           //保存入sdCard  
//		           File file2= new File("/sdcard/dcim/Camera/test.jpg");  
//		           try {  
//		            FileOutputStream out = new FileOutputStream(file2);  
//		            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)){  
//		                out.flush();  
//		                out.close();  
//		            }  
		             
		             
//		        //读取sd卡  
//		           File file =new File("/sdcard/dcim/Camera/test.jpg");  
//		           int maxBufferSize = 16 * 1024;  
//		              
//		            int len = 0;  
//		            ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
//		             BufferedInputStream bufferedInputStream;  
//		            try {  
//		                bufferedInputStream = new BufferedInputStream(new FileInputStream(file));  
//		                int bytesAvailable = bufferedInputStream.available();  
//		                int bufferSize = Math.min(bytesAvailable, maxBufferSize);  
//		                byte[] buffer = new byte[bufferSize];  
//		                while ((len = bufferedInputStream.read(buffer)) != -1)  
//		                {  
//		                    outStream.write(buffer, 0, bufferSize);  
//		                }  
//		                 data = outStream.toByteArray();  
//		                outStream.close();  
//		                bufferedInputStream.close();  
//		                  
//		            } catch (FileNotFoundException e) {  
//		                e.printStackTrace();  
//		            } catch (IOException e) {  
//		                e.printStackTrace();  
//		            }  

	}
}
