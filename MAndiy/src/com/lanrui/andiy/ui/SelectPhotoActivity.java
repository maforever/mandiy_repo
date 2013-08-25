package com.lanrui.andiy.ui;

import java.util.ArrayList;
import java.util.List;

import com.lanrui.andiy.R;
import com.lanrui.andiy.adapter.PhotoGridViewAdater;
import com.lanrui.andiy.adapter.SelectedPhotoGridViewAdater;
import com.lanrui.andiy.model.Model;
import com.lanrui.andiy.service.ModelService;
import com.lanrui.andiy.util.ImageUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SelectPhotoActivity extends Activity {
	private ArrayList<String> paths = null;
	private ArrayList<String> selectedPaths = new ArrayList<String>();
	private ArrayList<Integer> selectedCardModelIds = new ArrayList<Integer>();
	private GridView gridView = null;
	private TextView textView = null;
	private String tips;
	private ImageView selected,imageView = null;
	private GridView selectedGridView = null;
	private String flag;
	private int no;
	private ModelService modelService = null;
	private ArrayList<Model> models = null;
	private String type;
	private int cardModelId;
	private String selectednPath;
	private Intent intent;
	private ArrayList<Integer> bookModelIds = new ArrayList<Integer>();
	private ArrayList<Integer> bookModelFirstPageIds = new ArrayList<Integer>();
	private PhotoGridViewAdater photoGridViewAdater = null;
	private ArrayList<Drawable> drawbles = new ArrayList<Drawable>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.build_select_multi);

		modelService = new ModelService(this);
			
		flag = getIntent().getStringExtra("flag");
		paths = getIntent().getStringArrayListExtra("paths");
		type = getIntent().getStringExtra("type");
		cardModelId = getIntent().getIntExtra("cardModelId", 0);
		selectedCardModelIds = getIntent().getIntegerArrayListExtra(
				"selectedCardModelIds");
		no = getIntent().getIntExtra("no", 0);
		bookModelIds = this.getIntent().getIntegerArrayListExtra("bookModelIds");
		bookModelFirstPageIds = this.getIntent().getIntegerArrayListExtra("bookModelFirstPageIds");
		
		textView = (TextView) findViewById(R.id.nums_tips);
		tips = this.getResources().getString(R.string.pic_seleced_notice);

		selectedGridView = new GridView(this);
		int width = getResources().getDimensionPixelOffset(
				R.dimen.media_selected_image_width);
		int height = getResources().getDimensionPixelOffset(
				R.dimen.media_selected_image_height);
		// Toast.makeText(this, "width = " + width, 1).show();
		// Toast.makeText(this, "height=" + height, 1).show();
		selectedGridView.setLayoutParams(new LayoutParams(width * 32, height));
		selectedGridView.setNumColumns(32);
		LinearLayout layout = (LinearLayout) findViewById(R.id.selected);
		layout.addView(selectedGridView);
		
		
		
		selectedPaths = this.getIntent().getStringArrayListExtra(
				"selectedPaths");
		if (selectedPaths != null && selectedPaths.size() > 0) {
			// Log.i("a", selectedPaths.size() + "");
			selectedGridViewAdapter();
		} else {
			selectedPaths = new ArrayList<String>();
		}

		if (flag != null && "back_from_preview".equals(flag)) {
			// Log.i("a", "back_from_preview");
			selectedPaths = getIntent()
					.getStringArrayListExtra("selectedPaths");
			selectedGridViewAdapter();
			tips = String.format(tips, selectedPaths.size(),
					32 - selectedPaths.size());
		} else {
			tips = String.format(tips, 0, 32);
		}
		textView.setText(tips);

		gridView = (GridView) findViewById(R.id.grid_media);
		gridViewadtapter();
		gridView.setOnItemClickListener(new OnItemClickListenerImpl());

		selectedGridView.setOnItemClickListener(new SelectedOnItemClickListenerImpl());

	}

	public final class SelectedOnItemClickListenerImpl implements
			OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			view = parent.getChildAt(position);
			view.setBackgroundDrawable(new ColorDrawable());
			
			final int location = position;
			selectedPaths.remove(SelectPhotoActivity.this.selectedPaths
					.get(location));
			drawbles.remove(position);
			photoGridViewAdater.isShowSelectedFlag(selectedPaths);
			selectedGridViewAdapter();
			
			tips = getResources().getString(R.string.pic_seleced_notice);
			tips = String.format(tips, selectedPaths.size(),
					32 - selectedPaths.size());
			textView.setText(tips);
		}
	}

	public final class OnItemClickListenerImpl implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			if (type != null && "card".equals(type)) {
				selectednPath = paths.get(position);
				Intent intent = new Intent(SelectPhotoActivity.this,
						BuildCardActivity.class);
				selectedPaths.add(selectednPath);
				intent.putExtra("cardModelId", cardModelId);
				intent.putExtra("selectedPath", selectednPath);
				intent.putIntegerArrayListExtra("selectedCardModelIds",
						selectedCardModelIds);
				intent.putExtra("selectedPaths", selectedPaths);
				intent.putExtra("type", type);
				SelectPhotoActivity.this.finish();
				startActivity(intent);
			} else {
				selected = (ImageView) view.findViewById(R.id.selected);
				imageView = (ImageView) view.findViewById(R.id.media);
				selected.setVisibility(ViewGroup.VISIBLE);
				selectedPaths.add(paths.get(position));
				tips = getResources().getString(R.string.pic_seleced_notice);
				tips = String.format(tips, selectedPaths.size(),
						32 - selectedPaths.size());
				// Toast.makeText(getApplicationContext(), tips, 1).show();
				textView.setText(tips);
				
				drawbles.add(imageView.getDrawable());
				
				selectedGridViewAdapter();
				
				
			}
		}
	}

	private void selectedGridViewAdapter() {
		SelectedPhotoGridViewAdater adater = new SelectedPhotoGridViewAdater(
				SelectPhotoActivity.this, selectedPaths, drawbles);
		selectedGridView.setAdapter(adater);
	}

	private void gridViewadtapter() {
		photoGridViewAdater = new PhotoGridViewAdater(
				SelectPhotoActivity.this, paths, selectedPaths);
		gridView.setAdapter(photoGridViewAdater);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (modelService != null) {
			modelService.closeDB();
			modelService = null;
		}
	}

	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			SelectPhotoActivity.this.finish();
			Intent intent2 = new Intent(SelectPhotoActivity.this,
					SelectPicActivity.class);
			intent2.putStringArrayListExtra("paths", paths);
			intent2.putStringArrayListExtra("selectedPaths", selectedPaths);
			startActivity(intent2);

			break;
		case R.id.next_step:


			

			if (type != null && "book".equals(type)) {
				int imageNums = selectedPaths.size();
				if (imageNums < 3) {
					Toast.makeText(this, "至少选择三张照片才能作书", 1).show();
				}else {
				Intent intent = new Intent(SelectPhotoActivity.this,
						BuildBookActivity.class);
				intent.putStringArrayListExtra("selectedPaths", selectedPaths);
				intent.putStringArrayListExtra("paths", paths);
				intent.putExtra("type", type);
				intent.putIntegerArrayListExtra("bookModelIds", bookModelIds);
				intent.putIntegerArrayListExtra("bookModelFirstPageIds", bookModelFirstPageIds);
				//Log.i("a", "not return");
				SelectPhotoActivity.this.finish();
				startActivity(intent);
				}
			} else { // pic
				
				Model model = null;
				models = new ArrayList<Model>();
				if (no == 0) {
					no = modelService.getNo();
				}
				modelService.deleteModelByNo(no);
				for (String selectedPath : selectedPaths) {
					model = new Model();
					model.setNo(no);
					model.setPath(selectedPath);
					model.setType("pic");
					models.add(model);
				}
				modelService.batchModels(models);
				
				
				
				Intent intent = new Intent(SelectPhotoActivity.this,
						PicPreviewActivity.class);
				intent.putStringArrayListExtra("selectedPaths", selectedPaths);
				intent.putStringArrayListExtra("paths", paths);
				intent.putExtra("no", no);
				SelectPhotoActivity.this.finish();
				startActivity(intent);
			}
			break;
		}
	}
}
