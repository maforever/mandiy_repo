package com.lanrui.andiy.ui;

import java.util.ArrayList;

import com.lanrui.andiy.R;
import com.lanrui.andiy.adapter.PhotoGridViewAdater;
import com.lanrui.andiy.service.ModelService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class CardSelectedPhotoActivity extends Activity {
	private String type;
	private ArrayList<String> selectedPaths = new ArrayList<String>();
	private ArrayList<Integer> selectedCardModelIds = new ArrayList<Integer>(); 
	private GridView gridView = null;
	private ArrayList<String> paths = new ArrayList<String>();
	private Intent intent = null;
	private String selectedPath;
	private ArrayList<Integer> cardModelIds = new ArrayList<Integer>();
	private ModelService modelService = null;
	private int no;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_selected_photo);
		
		modelService = new ModelService(this);
		
		no = this.getIntent().getIntExtra("no", 0);
		if(no == 0) {
			no = modelService.getNo();
		}
		
		selectedCardModelIds = this.getIntent().getIntegerArrayListExtra("selectedCardModelIds");
		selectedPaths = this.getIntent().getStringArrayListExtra("selectedPaths");
		type = this.getIntent().getStringExtra("type");
		cardModelIds = this.getIntent().getIntegerArrayListExtra("cardModelIds");
		
		if(selectedPaths == null) {
			selectedPaths = new ArrayList<String>();
		}
		
		paths = this.getIntent().getStringArrayListExtra("paths");
		gridView = (GridView) this.findViewById(R.id.card_grid_media);
		gridViewAdapter();
		gridView.setOnItemClickListener(new OnItemClickListenerImpl());
		
	}
	
	public class OnItemClickListenerImpl implements OnItemClickListener {
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
			selectedPath = paths.get(position);
			CardSelectedPhotoActivity.this.finish();
			intent = new Intent(CardSelectedPhotoActivity.this, BuildCardActivity.class);
			selectedPaths.add(selectedPath);
			intent.putStringArrayListExtra("paths", paths);
			intent.putIntegerArrayListExtra("selectedCardModelIds", selectedCardModelIds);
			intent.putStringArrayListExtra("selectedPaths", selectedPaths);
			intent.putExtra("no", no);
			intent.putExtra("type", type);
			intent.putIntegerArrayListExtra("cardModelIds", cardModelIds);
			startActivity(intent);
		}
	}
	
	private void gridViewAdapter() {
		PhotoGridViewAdater adater = new PhotoGridViewAdater(this, paths, null);
		gridView.setAdapter(adater);
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
			case R.id.card_back:
				CardSelectedPhotoActivity.this.finish();
				intent = new Intent(CardSelectedPhotoActivity.this, SelectPicActivity.class);
				intent.putStringArrayListExtra("paths", paths);
				intent.putIntegerArrayListExtra("selectedCardModelIds", selectedCardModelIds);
				intent.putStringArrayListExtra("selectedPaths", selectedPaths);
				intent.putExtra("type", type);
				startActivity(intent);
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		modelService.closeDB();
	}
	
}
