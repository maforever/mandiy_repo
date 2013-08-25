package com.lanrui.andiy.ui;

import com.lanrui.andiy.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SettingActivity extends Activity {
	private int radioButtonId;
	private Intent intent = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.setting);
		radioButtonId = this.getIntent().getIntExtra("radioButtonId", 0);
		Log.i("a", "radioButtonId = " + radioButtonId);
	}
	public void btnClick(View view) {
		SettingActivity.this.finish();
		intent = new Intent(SettingActivity.this,
				IndexActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
