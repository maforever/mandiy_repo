package com.lanrui.andiy.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.lanrui.andiy.R;
import com.lanrui.andiy.model.Model;
import com.lanrui.andiy.service.ModelService;
import com.lanrui.andiy.util.ImageUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.ImageView.ScaleType;

public class BuildBookActivity extends Activity {
	private PopupWindow popupWindow = null;
	private ViewFlipper viewFlipper = null;
	private ImageView btn_next,btn_sort, btn_template, btn_previous, tools_more, btn_delpage = null;
	private Animation left_in, left_out, right_in, right_out;
	private TextView page_num = null;
	private Bitmap desbm, bm = null;
	private ArrayList<Bitmap> desbms = new ArrayList<Bitmap>();
	private ArrayList<Integer> selectedBookModelIds = new ArrayList<Integer>();
	private ArrayList<String> selectedPaths = new ArrayList<String>();
	private Integer selectedBookFristPage;
	private ArrayList<Integer> bookModelIds = new ArrayList<Integer>();
	private ArrayList<Integer> bookModelFirstPageIds = new ArrayList<Integer>();
	private ArrayList<String> imageViewTags = new ArrayList<String>();
	private String type;
	private int page_index = 0;
	private int location = 0;
	private ImageView imageView = null;
	private Model model = null;
	private Integer no;
	private ModelService modelService;
	private LinearLayout layout = null;
	private ImageUtils imageUtils = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.build_book);
		
		selectedPaths = this.getIntent().getStringArrayListExtra("selectedPaths");
		bookModelIds = this.getIntent().getIntegerArrayListExtra("bookModelIds");
		bookModelFirstPageIds = this.getIntent().getIntegerArrayListExtra("bookModelFirstPageIds");
		
		
		tools_more = (ImageView) this.findViewById(R.id.tools_more);
		btn_previous = (ImageView) this.findViewById(R.id.btn_previous);
		btn_template = (ImageView) this.findViewById(R.id.template);
		btn_delpage = (ImageView) this.findViewById(R.id.del_page);
		btn_next = (ImageView) this.findViewById(R.id.btn_next);
		page_num = (TextView) this.findViewById(R.id.page_num);
		btn_sort = (ImageView) this.findViewById(R.id.sort);
		
		btn_previous.setImageResource(R.drawable.btn_previous_off);
		imageUtils = new ImageUtils(this);
		
		View contentView = this.getLayoutInflater().inflate(
				R.layout.popwindow_menu, null);
		popupWindow = new PopupWindow(contentView,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		
		viewFlipper = (ViewFlipper) this.findViewById(R.id.book_viewflipper);
		
		for(int i = 0; i < selectedPaths.size(); i++) {
			layout = new LinearLayout(this);
			layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			imageView.setScaleType(ScaleType.FIT_XY);
			bm = decodeFile(new File(selectedPaths.get(i)));
			if(i == 0) {
				desbm = imageUtils.getBigFrame(bm, bookModelFirstPageIds.get(0));
			}else {
				desbm = imageUtils.getBigFrame(bm, bookModelIds.get(i));
			}
			imageView.setImageBitmap(desbm);
			layout.addView(imageView);
			viewFlipper.addView(layout);
		}
		
		View view = this.getLayoutInflater().inflate(R.layout.build_page_last, null);
		viewFlipper.addView(view);
		
		page_num.setText("封面");
		
		left_in = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
		left_out = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
		right_in = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
		right_out = AnimationUtils.loadAnimation(this, R.anim.push_right_out);
		
		viewFlipper.setOnTouchListener(new OnTouchListenerImpl());
	}
	
	public class OnTouchListenerImpl implements OnTouchListener {
		private float startX, endX;
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = event.getX();
				break;

			case MotionEvent.ACTION_UP:
				endX = event.getX();
				if(startX - endX < -10) {
					if(viewFlipper.getDisplayedChild() != 0) {
						viewflipper_showpervious();
					}else {
						Toast.makeText(BuildBookActivity.this, "当前已经是第一页了", 1).show();
						break;
					}
				}else if(startX - endX > 10){
					if(viewFlipper.getDisplayedChild() != selectedPaths.size()) {
						viewflipper_shownext();
					}else {
						Toast.makeText(BuildBookActivity.this, "当前已经是最后一 页了", 1).show();
						break;
					}
				}
				break;
			}
			return true;
		}
	}
	
	
	public void viewflipper_shownext() {
		btn_previous.setImageResource(R.drawable.btn_previous);
		btn_previous.setEnabled(true);
		page_index = page_index + 1;
//		Log.i("a", "page_index = " + page_index);
		if(viewFlipper.getDisplayedChild() == viewFlipper.getChildCount() - 2) {
			btn_next.setImageResource(R.drawable.btn_next_off);
			btn_next.setEnabled(false);
			btn_template.setEnabled(false);
			btn_delpage.setEnabled(false);
			btn_sort.setEnabled(false);
			btn_template.setImageResource(R.drawable.btn_edit_template_off);
			btn_delpage.setImageResource(R.drawable.btn_edit_del_off);
			page_num.setText("菜单");
			viewFlipper.showNext();
			//viewFlipper.showNext();
			//Toast.makeText(this, "当前已经是最后一页了", 1).show();
		}else {
			//btn_previous.setImageResource(R.drawable.btn_previous_off);
			
			btn_template.setEnabled(true);
			btn_delpage.setEnabled(true);
			btn_template.setImageResource(R.drawable.btn_edit_template);
			btn_delpage.setImageResource(R.drawable.btn_edit_del);
			
			viewFlipper.showNext();
			page_num.setText("第" + page_index + "/" + (selectedPaths.size() - 1) + "张");
			viewFlipper.setInAnimation(left_in);
			viewFlipper.setOutAnimation(right_out);
		}
	}
	
	public void viewflipper_showpervious() {
		btn_next.setImageResource(R.drawable.btn_next);
		btn_next.setEnabled(true);
		page_index = page_index - 1;
		btn_template.setEnabled(true);
		btn_delpage.setEnabled(true);
		btn_template.setImageResource(R.drawable.btn_edit_template);
		btn_delpage.setImageResource(R.drawable.btn_edit_del);
		if(viewFlipper.getDisplayedChild() == 1) {
			btn_previous.setImageResource(R.drawable.btn_previous_off);
			btn_previous.setEnabled(false);
			viewFlipper.showPrevious();
			//Toast.makeText(this, "当前已经是第一页了", 1).show();
			page_num.setText("封面");
		}else {
			page_num.setText("第" + page_index + "/" + (selectedPaths.size() - 1) + "张");
			viewFlipper.showPrevious();
			viewFlipper.setInAnimation(right_in);
			viewFlipper.setOutAnimation(left_out);
		}
	}
	
	private Bitmap decodeFile(File f) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = 70;

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			while (o.outWidth / scale / 2 >= REQUIRED_SIZE
					&& o.outHeight / scale / 2 >= REQUIRED_SIZE)
				scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}
	

	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.btn_next:
			viewflipper_shownext();
			break;
		case R.id.btn_previous:
			viewflipper_showpervious();
			break;
		case R.id.index:
			BuildBookActivity.this.finish();
			Intent intent = new Intent(BuildBookActivity.this, IndexActivity.class);
			startActivity(intent);
		break;
		case R.id.tools_more:
			tools_more.setVisibility(ViewGroup.GONE);
			View parent = this.findViewById(R.id.book_frame);
			popupWindow.showAtLocation(parent, Gravity.RIGHT | Gravity.TOP, -10, 20);
			break;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(MotionEvent.ACTION_DOWN == event.getAction()) {
			if(popupWindow.isShowing()) {
				popupWindow.dismiss();
				tools_more.setVisibility(ViewGroup.VISIBLE);
			}
		}
		return true;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(popupWindow.isShowing()) {
			popupWindow.dismiss();
			popupWindow = null;
		}
	}
}
