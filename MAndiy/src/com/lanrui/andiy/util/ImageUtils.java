package com.lanrui.andiy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.ThumbnailUtils;

public class ImageUtils {
	private Context context = null;
	public ImageUtils(Context context) {
		this.context = context;
	}
	
	
	public Bitmap decodeImage2(File file) {
		BitmapFactory.Options ops = new BitmapFactory.Options();
		ops.inPreferredConfig = Bitmap.Config.RGB_565;
		ops.inSampleSize = 4;
		ops.inPurgeable = true;
		ops.inInputShareable = true;
		try {
			return BitmapFactory.decodeStream(new FileInputStream(file), null, ops);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//生成缩略图
	public Bitmap decodeImage(File file) {
		BitmapFactory.Options ops = new BitmapFactory.Options();
		ops.inJustDecodeBounds = true;
		Bitmap bm = null;
		try {
			//bm = BitmapFactory.decodeStream(new FileInputStream(file),null,ops);
			bm = BitmapFactory.decodeStream(new FileInputStream(file), null, ops);
		} catch (Exception e) {
			e.printStackTrace();
		}
		float realWidth = ops.outWidth;
		float realHeight = ops.outHeight;
		System.out.println("真实图片高度：" + realHeight + "宽度:" + realWidth);

		int scale = (int) ((realHeight > realWidth ? realHeight : realWidth) / 100);
		if (scale <= 0) {
			scale = 1;
		}
		ops.inSampleSize = scale;
		ops.inJustDecodeBounds = false;

		try {
			bm = BitmapFactory.decodeStream(new FileInputStream(file),null,ops);
			//bm = BitmapFactory.decodeStream(new FileInputStream(file));
			bm = ThumbnailUtils.extractThumbnail(bm, 120, 120, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			int w = bm.getWidth();
			int h = bm.getHeight();
			System.out.println("缩略图高度：" + h + "宽度:" + w);
			return bm;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bm;
	}
	
	

	/**
	 * 两张照片重合
	 */
	
	public  Bitmap getBigFrame(Bitmap bm, int is) {
		Bitmap bitmap = decodeBitmap(is);
		Drawable[] array = new Drawable[2];
		array[0] = new BitmapDrawable(bm);
		// Bitmap b = resize(bitmap, bm.getWidth(), bm.getHeight());
		array[1] = new BitmapDrawable(bitmap);
		LayerDrawable layer = new LayerDrawable(array);
		return drawableToBitmap(layer);
	}
	
	private Bitmap decodeBitmap(int res) {
		return BitmapFactory.decodeResource(context.getResources(), res);
		// return BitmapFactory.decodeStream(is);

	}
	private Bitmap drawableToBitmap(LayerDrawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;

	}
}
