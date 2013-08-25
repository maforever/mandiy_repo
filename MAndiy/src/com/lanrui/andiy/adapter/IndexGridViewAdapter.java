package com.lanrui.andiy.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.lanrui.andiy.R;
import com.lanrui.andiy.model.Model;
import com.lanrui.andiy.service.ModelService;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class IndexGridViewAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<List<Model>> modelss;
	private View[] views;
	private ModelService modelService;
	private GridView gridView = null;
	ImageView imageView = null;
	Bitmap bitmap = null;
	TextView label = null;
	public IndexGridViewAdapter(Context context, List<List<Model>> modelss, GridView gridView) {
		this.gridView = gridView;
		this.modelss = modelss;
		this.context = context;
		modelService = new ModelService(context);
		Log.i("a", "modelss.size = " + modelss.size());
		views = new View[modelss.size()];
		for(int i = 0; i < modelss.size(); i ++) {
			View view = null;
			ImageView imageView = null;
			Bitmap bitmap = null;
			TextView label = null;
//			Log.i("a", "type ====== " + modelss.get(i).get(0).getType());
			
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if("pic".equals(modelss.get(i).get(0).getType())) {
				view = inflater.inflate(R.layout.index_style_pic, null);
//				Log.i("a", "path = " + modelss.get(i).get(0).getPath());
				imageView = (ImageView) view.findViewById(R.id.cover);
				label = (TextView) view.findViewById(R.id.label);
				String label_str = "照片" + modelss.get(i).size() + "张";
				label.setText(label_str);
				bitmap = decodeFile(new File(modelss.get(i).get(0).getPath()));
				imageView.setImageBitmap(bitmap);
			}else if("card".equals(modelss.get(i).get(0).getType())) {
				view = inflater.inflate(R.layout.index_style_card, null);
				imageView = (ImageView) view.findViewById(R.id.cover);
				label = (TextView) view.findViewById(R.id.label);
				String label_str = "卡片" + modelss.get(i).size() + "张";
				label.setText(label_str);
				bitmap = decodeFile(new File(modelss.get(i).get(0).getPath()));
				imageView.setImageBitmap(bitmap);
//				Log.i("a", "is card");
				
				
			}else if("book".equals(modelss.get(i).get(0).getType())) {
				
			}else if("poster".equals(modelss.get(i).get(0).getType())) {
				view = inflater.inflate(R.layout.index_style_poster, null);
				imageView = (ImageView) view.findViewById(R.id.cover);
				label = (TextView) view.findViewById(R.id.label);
				String label_str = "海报" + modelss.get(i).size() + "张";
				label.setText(label_str);
				bitmap = decodeFile(new File(modelss.get(i).get(0).getPath()));
				imageView.setImageBitmap(bitmap);
			}
			views[i] = view;
		}
		modelService.closeDB();
	}
	public int getCount() {
		return modelss.size();
	}

	public Object getItem(int position) {
		return modelss.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return views[position];
	}
	
	public void isShowDeleteBtn(boolean flag) {
		if(flag) {
			for(int i = 0 ; i < views.length; i ++) {
				ImageView delImage = (ImageView) views[i].findViewById(R.id.del);
				delImage.setVisibility(ViewGroup.VISIBLE);
				final int location = i;
				delImage.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Dialog dialog = new AlertDialog.Builder(context)
						.setTitle("删除照片")
						.setMessage("您确定要删除照片吗？")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								int no = modelss.get(location).get(0).getNo();
								modelService = new ModelService(context);
								modelService.deleteModelByNo(no);
								List<Integer> nos = modelService.getNoGroupByNo();
								modelss = modelService.getModelsByNos(nos);
								IndexGridViewAdapter adapter = new IndexGridViewAdapter(context, modelss, gridView);
								gridView.setAdapter(adapter);
								adapter.isShowDeleteBtn(true);
								modelService.closeDB();
							}
						})
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss(); 
							}
						})
						.create();
						dialog.show();
					}
				});
			}
		}else {
			Log.i("a", "isClick");
			for(View v : views) {
				ImageView delImage = (ImageView) v.findViewById(R.id.del);
				delImage.setVisibility(ViewGroup.GONE);
			}
		}
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
