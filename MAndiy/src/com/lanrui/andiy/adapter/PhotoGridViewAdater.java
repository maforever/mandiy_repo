package com.lanrui.andiy.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.lanrui.andiy.R;
import com.lanrui.andiy.util.ImageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PhotoGridViewAdater extends BaseAdapter {
	private Context context = null;
	private List<String> paths = null;
	private LayoutInflater inflater = null;
	private List<String> selectedPaths = null;
	private ImageUtils imageUtils = null;
	private Bitmap bm = null;
	private View[] views = null;
	public PhotoGridViewAdater(Context context, List<String> paths, List<String> selectedPaths) {
		this.context = context;
		imageUtils = new ImageUtils(context);
		this.paths = paths;
		this.selectedPaths = selectedPaths;
		views = new View[paths.size()];
		for(int i = 0; i < paths.size(); i ++) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.build_select_item_media, null);
			ImageView image = (ImageView) view.findViewById(R.id.media);
			asyncImageLoad(image, paths.get(i));
			views[i] = view;
		}
		
		
	}
	
	
	public void isShowSelectedFlag(ArrayList<String> selectedPaths) {
		
		for(int i = 0; i < paths.size(); i ++) {
			if(selectedPaths.contains(paths.get(i))) {
				views[i].findViewById(R.id.selected).setVisibility(ViewGroup.VISIBLE);
			}else {
				views[i].findViewById(R.id.selected).setVisibility(ViewGroup.GONE);
			}
		}
		
	}
	
	public int getCount() {
		return paths.size();
	}

	public Object getItem(int position) {
		return paths.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
//		if(convertView == null) {
//			convertView = inflater.inflate(R.layout.build_select_item_media, null);
//			ImageView image = (ImageView) convertView.findViewById(R.id.media);
////			bm = imageUtils.decodeImage2(new File(paths.get(position))); 
//			//decodeFile(new File(paths.get(position)));
////			image.setImageBitmap(bm);
//			asyncImageLoad(image, paths.get(position));
//			ImageView selectedImage = (ImageView) convertView.findViewById(R.id.selected);
//			if(selectedPaths != null && selectedPaths.size() > 0) {
//				
//				if(selectedPaths.contains(paths.get(position))) {
//					selectedImage.setVisibility(ViewGroup.VISIBLE);
//				}else {
//					selectedImage.setVisibility(ViewGroup.GONE);
//				}
//			}
//		}
		
		return views[position];
	}
	
	public class LoadImageAsyn extends AsyncTask<String, Integer, Bitmap> {
		private ImageView imageView = null;
		public LoadImageAsyn(ImageView imageView) {
			this.imageView = imageView;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			File file = new File(params[0]);
			return imageUtils.decodeImage(file);
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			if(result != null && imageView != null) {
				imageView.setImageBitmap(result);
			}
		}
	}
	
	private void asyncImageLoad(ImageView imageView, String path) {
		LoadImageAsyn asyn = new LoadImageAsyn(imageView);
		asyn.execute(path);
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


























