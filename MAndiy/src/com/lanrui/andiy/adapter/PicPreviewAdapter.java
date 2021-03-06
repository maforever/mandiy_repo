package com.lanrui.andiy.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.lanrui.andiy.R;
import com.lanrui.andiy.model.Model;
import com.lanrui.andiy.service.ModelService;
import com.lanrui.andiy.ui.PicPreviewActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class PicPreviewAdapter extends BaseAdapter {
	private Context context;
	private List<String> selectedPaths;
	private LayoutInflater inflater;
	private View[] views = null;
	private View view = null;
	private ImageView imageView = null;
	private Bitmap bitmap = null;
	private GridView gridView = null;
	private ModelService modelService = null;
	private int no;
	public PicPreviewAdapter(Context context, List<String> selectedPaths, GridView gridView, int no, ModelService modelService) {
		this.no = no;
		this.context = context;
		views = new View[selectedPaths.size()];
		this.modelService = modelService;
		this.selectedPaths = selectedPaths;
		this.gridView = gridView;
		for(int i = 0; i < selectedPaths.size(); i ++) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.picpreview_gridview_item, null);
			imageView = (ImageView) view.findViewById(R.id.pic_preview);
			bitmap = decodeFile(new File(selectedPaths.get(i)));
			imageView.setImageBitmap(bitmap);
			views[i] = view;
		}
	}
	public int getCount() {
		return selectedPaths.size();
	}

	public Object getItem(int position) {
		return selectedPaths.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
//		convertView = inflater.inflate(R.layout.picpreview_gridview_item, null);
//		ImageView imageView = (ImageView) convertView.findViewById(R.id.pic_preview);
//		Bitmap bitmap = decodeFile(new File(selectedPaths.get(position)));
//		imageView.setImageBitmap(bitmap);
//		return convertView;
		return views[position];
	}
	
	public void isShowDelBtn(boolean flag) {
		if(flag) {
			for(int i = 0; i < views.length; i ++) {
				ImageView del_img =(ImageView) views[i].findViewById(R.id.pic_pre_delete);
				del_img.setVisibility(ViewGroup.VISIBLE);
				final int location = i;
				del_img.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Dialog dialog = new AlertDialog.Builder(PicPreviewAdapter.this.context)
						.setTitle("ɾ����Ƭ")
						.setMessage(PicPreviewAdapter.this.context.getResources().getString(R.string.dialog_del_pic))
						.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								selectedPaths.remove(location);
								
								modelService.deleteModelByNo(no);
								Model model = null;
								for(String path : selectedPaths) {
									model = new Model();
									model.setPath(path);
									model.setNo(no);
									model.setType("pic");
									modelService.add(model);
								}
								
								PicPreviewAdapter adapter = new PicPreviewAdapter(PicPreviewAdapter.this.context, selectedPaths, gridView, no, modelService);
								gridView.setAdapter(adapter);
								adapter.isShowDelBtn(true);
								
							}
						})
						.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
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
			for(View v : views) {
				ImageView del_img =(ImageView) v.findViewById(R.id.pic_pre_delete);
				del_img.setVisibility(ViewGroup.GONE);
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
