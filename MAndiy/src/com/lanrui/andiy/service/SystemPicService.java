package com.lanrui.andiy.service;

import java.util.HashSet;
import java.util.Set;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class SystemPicService {
	
	private ContentResolver cr;  
	private Context context = null;
	private HashSet<String> all_pic_path = null;
	public SystemPicService(Context context) {
		this.context = context;
	}
	
	public Set<String> getAllPicPath() {
		all_pic_path = new HashSet<String>();
		Uri uri = Uri.parse("content://media/external/images/media");
		ContentResolver resolver = context.getContentResolver();
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = resolver.query(uri, proj, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		while (cursor.moveToNext()) {
			String path = cursor.getString(column_index);
			if(!path.contains("thumbnail")) {
				String parent_path = path.substring(0, path.lastIndexOf("/"));
				all_pic_path.add(parent_path);
			}
		}
		cursor.close();
		return all_pic_path;
	}
}
