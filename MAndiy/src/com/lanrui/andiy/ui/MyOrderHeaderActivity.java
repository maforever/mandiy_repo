package com.lanrui.andiy.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanrui.andiy.R;
import com.lanrui.andiy.util.HttpUtils;
import com.lanrui.andiy.util.MD5;

public class MyOrderHeaderActivity extends Activity {
	private String nickname;
	private String iconUrl;
	private Bitmap iconBm = null;
	private TextView name = null;
	private ImageView head = null;
	private MyHandler handler = null;
	private File qqIcon_cache = null; 
	
	private class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 0) {
				head.setImageURI((Uri)msg.obj);
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_order_head);
		
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			qqIcon_cache = new File(Environment.getExternalStorageDirectory(), "yin8_head_cache");
			if(!qqIcon_cache.exists()) qqIcon_cache.mkdirs();
		}else {
			Toast.makeText(MyOrderHeaderActivity.this, "«ÎºÏ≤ÈSDCARD", 0).show();
		}
		
		
		handler = new MyHandler();
		name = (TextView) this.findViewById(R.id.name);
		head = (ImageView) this.findViewById(R.id.head);
		nickname = this.getIntent().getStringExtra("nickname");
		iconUrl = this.getIntent().getStringExtra("icon");
		
		name.setText(nickname);
		
		new loadIconThread().start();
		
		//Toast.makeText(this, nickname + "-----" + iconUrl, 0).show();
		
	}
	
	private class loadIconThread extends Thread {
		@Override
		public void run() {
			Uri uri = getIconCache(iconUrl);
			handler.sendMessage(handler.obtainMessage(0, uri));
		}

	}
	private Uri getIconCache(String iconUrl) {
		String filename = MD5.getMD5(iconUrl) + ".jpg";
		File image = new File(qqIcon_cache, filename);
		if(image.exists()) {
			return Uri.fromFile(image);
		}
		try {
			InputStream is = HttpUtils.getImageInputFromNet(iconUrl);
			if(is != null) {
				FileOutputStream fos = new FileOutputStream(image);
				byte[] buffer = new byte[1024];
				int len = 0;
				while( (len = is.read(buffer)) != -1 ) {
					fos.write(buffer, 0, len);
				}
				is.close();
				fos.close();
				return Uri.fromFile(image);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}





















