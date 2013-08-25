package com.lanrui.andiy.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.lanrui.andiy.R;
import com.lanrui.andiy.util.ImageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class SelectedPhotoGridViewAdater extends BaseAdapter {
	private Context context = null;
	private List<String> selectedPaths = null;
	private LayoutInflater inflater = null;
	private ImageUtils imageUtils = null;
	private ArrayList<Drawable> drawables = null;
	public SelectedPhotoGridViewAdater(Context context, List<String> selectedPaths, ArrayList<Drawable> drawables) {
		this.drawables = drawables;
		this.context = context;
		this.selectedPaths = selectedPaths;
		//imageUtils = new ImageUtils(context);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public int getCount() {
		return drawables.size();
	}

	public Object getItem(int position) {
		return drawables.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.build_select_selected, null);
		ImageView image = (ImageView) convertView.findViewById(R.id.image);
//		Bitmap bitmap = imageUtils.decodeImage(new File(selectedPaths.get(position))); 
				//decodeFile(new File(selectedPaths.get(position)));
		image.setBackgroundDrawable(drawables.get(position));
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
