package com.lanrui.andiy.ui;

import java.util.ArrayList;

import com.lanrui.andiy.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class LoginActivity extends Activity {
	private ArrayList<String> selectedPaths;
	private ArrayList<String> paths;
	private String flag;
	private Intent intent = null;
	private String can_anonymous = null;
	private ImageView btn_login_anonymous = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_login);
		
		selectedPaths = this.getIntent().getStringArrayListExtra("selectedPaths");
		paths = this.getIntent().getStringArrayListExtra("paths");
		flag = this.getIntent().getStringExtra("flag");
		can_anonymous = this.getIntent().getStringExtra("can_anonymous");
		
		btn_login_anonymous = (ImageView) this.findViewById(R.id.login_anonymous);
		
		if(can_anonymous != null  && "can_anonymous".equals(can_anonymous)) {
			btn_login_anonymous.setVisibility(ViewGroup.VISIBLE);
		}
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			LoginActivity.this.finish();
			if(flag != null && "from_index".equals(flag)) {
				intent = new Intent(LoginActivity.this, PicPreviewActivity.class);
				intent.putStringArrayListExtra("selectedPaths", selectedPaths);
				intent.putStringArrayListExtra("paths", paths);
				intent.putExtra("flag", flag);
				startActivity(intent);
			}else if(flag != null && "from_indexmenuactivity".equals(flag)) {
				LoginActivity.this.finish();
//				intent = new Intent(LoginActivity.this, IndexActivity.class);
//				startActivity(intent);
			}
		break;
		}
	}
}
