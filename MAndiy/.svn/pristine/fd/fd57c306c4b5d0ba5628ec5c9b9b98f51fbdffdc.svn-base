package com.lanrui.andiy.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.lanrui.andiy.R;
import com.lanrui.andiy.adapter.ChooseCardModelAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class ChooseCardModelActivity extends Activity {
	private String type;
	private ArrayList<Integer> cardModelIds = new ArrayList<Integer>();
	private GridView gridView = null;
	private int cardModelId;
	private ArrayList<Integer> selectedCardModelIds = new ArrayList<Integer>();
	private ArrayList<String> selectedPaths;
	private int no;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_card_model);
		try {
			initCardModelIds("cardmodel_");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		for(int id : cardModelIds) {
//			Log.i("a", "model cardmodel id = " + id);
//		}
		
		
//		Log.i("a", "model size = " + cardModelIds.size());
		type = this.getIntent().getStringExtra("type");
		selectedCardModelIds = this.getIntent().getIntegerArrayListExtra("selectedCardModelIds");
		selectedPaths = this.getIntent().getStringArrayListExtra("selectedPaths");
		no = this.getIntent().getIntExtra("no", 0);
		gridView = (GridView) findViewById(R.id.choose_card_grid);
		gridViewAdapter();
		gridView.setOnItemClickListener(new OnItemClickListenerImpl());
	}
	
	public class OnItemClickListenerImpl implements OnItemClickListener {
		public void onItemClick(AdapterView<?> adapterView, View view, int postion,long id) {
			cardModelId = cardModelIds.get(postion);
			Intent intent = new Intent(ChooseCardModelActivity.this, SelectPicActivity.class);
			String textStr = "Ñ¡Ôñ¿¨Æ¬ÕÕÆ¬";
			intent.putExtra("type", type);
			intent.putExtra("cardModelId", cardModelId);
			if(selectedCardModelIds != null && selectedCardModelIds.size() > 0) {
				selectedCardModelIds.add(cardModelId);
//				Log.i("a", "selectedCardModelIds size = " + selectedCardModelIds.size());
			}else {
				selectedCardModelIds = new ArrayList<Integer>();
				selectedCardModelIds.add(cardModelId);
			}                                
			intent.putIntegerArrayListExtra("selectedCardModelIds", selectedCardModelIds);
			intent.putStringArrayListExtra("selectedPaths", selectedPaths);
			intent.putIntegerArrayListExtra("cardModelIds", cardModelIds);
			intent.putExtra("no", no);
			ChooseCardModelActivity.this.finish();
			startActivity(intent);
		}
	}

	private void gridViewAdapter() {
		ChooseCardModelAdapter adapter = new ChooseCardModelAdapter(getApplicationContext(), cardModelIds);
		gridView.setAdapter(adapter);
	}



	private void initCardModelIds(String str) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = R.drawable.class.getDeclaredFields();
		
		for(int i = 0; i < fields.length; i ++) {
			if(fields[i].getName().startsWith(str)) {
				cardModelIds.add(fields[i].getInt(R.drawable.class));
			}
		}
	}



	public void btnClick(View view) {
		ChooseCardModelActivity.this.finish();
		Intent intent = new Intent(ChooseCardModelActivity.this, IndexActivity.class);
		startActivity(intent);
	}
}
