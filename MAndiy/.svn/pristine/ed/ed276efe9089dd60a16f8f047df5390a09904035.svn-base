package com.lanrui.andiy.adapter;

import java.util.List;

import com.lanrui.andiy.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ChooseCardModelAdapter extends BaseAdapter {
	private Context content;
	private List<Integer> cardModelIds;
	private ImageView[] imageViews;
	private LayoutInflater inflater = null;
	public ChooseCardModelAdapter(Context context, List<Integer> cardModelIds) {
		this.content = context;
		this.cardModelIds = cardModelIds;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public int getCount() {
		return cardModelIds.size();
	}

	public Object getItem(int position) {
		return cardModelIds.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.choose_cardmodel_item, null);
		ImageView imageView = (ImageView) convertView.findViewById(R.id.pic_preview);
		Bitmap bitmap = BitmapFactory.decodeResource(this.content.getResources(), cardModelIds.get(position));
		bitmap = Bitmap.createScaledBitmap(bitmap, 100,120, true);
		imageView.setImageBitmap(bitmap);
		return convertView;
	}
}




















