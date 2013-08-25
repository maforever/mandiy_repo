package com.lanrui.andiy.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.lanrui.andiy.R;
import com.lanrui.andiy.model.SystemImages;
import com.lanrui.andiy.util.CompressImage;
import com.lanrui.andiy.util.ImageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SystemImageAdaptor extends BaseAdapter {
	private LayoutInflater inflater = null;
	private Context context = null;
	private List<SystemImages> lists = null;
	private ImageUtils imageUtils = null;
	public SystemImageAdaptor(Context context, List<SystemImages> lists) {
		this.context = context;
		this.lists = lists;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageUtils = new ImageUtils(context);
	}
	
	public int getCount() {
		return lists.size();
	}

	public Object getItem(int position) {
		return lists.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.select_pic_item, null);
		ImageView imageView = (ImageView) convertView.findViewById(R.id.select_image);
		

//		Bitmap bitmap = BitmapFactory.decodeFile(lists.get(1).getPath().get(1));
//        Bitmap newBit = Bitmap  
//                .createScaledBitmap(bitmap, 10, 10, false);
		Log.i("a", lists.get(position).getPath().get(0));
		//Bitmap bitmap = CompressImage.compressImage((lists.get(position).getPath().get(0)));
		Bitmap bitmap = imageUtils.decodeImage(new File(lists.get(position).getPath().get(0))) ;
				//decodeFile(new File(lists.get(position).getPath().get(0)));
        imageView.setImageBitmap(bitmap);
//        bitmap.recycle();
//        bitmap=null;
		TextView textView1 = (TextView) convertView.findViewById(R.id.pic_document);
		TextView textView2 = (TextView) convertView.findViewById(R.id.pic_num);
		textView1.setText(lists.get(position).getFolder_name());
		textView2.setText("(" + lists.get(position).getCount() + ")");
		return convertView;
	}
	
	
	private Bitmap decodeFile(File f){
	    try {
	        //Decode image size
	        BitmapFactory.Options o = new BitmapFactory.Options();
	        o.inJustDecodeBounds = true;
	        BitmapFactory.decodeStream(new FileInputStream(f),null,o);

	        //The new size we want to scale to
	        final int REQUIRED_SIZE=70;

	        //Find the correct scale value. It should be the power of 2.
	        int scale=1;
	        while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
	            scale*=2;

	        //Decode with inSampleSize
	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize=scale;
	        return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
	    } catch (FileNotFoundException e) {}
	    return null;
	}
	

}
