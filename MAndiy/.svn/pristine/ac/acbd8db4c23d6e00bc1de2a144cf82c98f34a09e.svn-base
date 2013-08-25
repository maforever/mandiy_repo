package com.lanrui.andiy.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

import com.lanrui.andiy.R;
import com.lanrui.andiy.model.Model;
import com.lanrui.andiy.service.ModelService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class BuildCardActivity extends Activity {
	private int cardModelId;
	private PopupWindow popupWindow = null;
	private ViewFlipper viewFlipper = null;
	private Intent intent = null;
	private String selectedPath;
	private ImageView btn_next,btn_text, btn_template, btn_previous, tools_more, btn_delpage = null;
	private Animation left_in, left_out, right_in, right_out;
	private TextView page_num = null;
	private Bitmap desbm, bm = null;
	private ArrayList<Bitmap> desbms = new ArrayList<Bitmap>();
	private ArrayList<Integer> selectedCardModelIds = new ArrayList<Integer>();
	private ArrayList<String> selectedPaths = new ArrayList<String>();
	private ArrayList<Integer> cardModelIds = new ArrayList<Integer>();
	private ArrayList<String> imageViewTags = new ArrayList<String>();
	private String type;
	private int page_index = 1;
	private int location = 0;
	private ImageView imageView = null;
	private Model model = null;
	private Integer no;
	private ModelService modelService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.build_card);
		tools_more = (ImageView) this.findViewById(R.id.tools_more);
		btn_next = (ImageView) this.findViewById(R.id.btn_next);
		btn_previous = (ImageView) this.findViewById(R.id.btn_previous);
		btn_previous.setImageResource(R.drawable.btn_previous_off);
		btn_text = (ImageView) findViewById(R.id.text);
		btn_template = (ImageView) findViewById(R.id.template);
		btn_delpage = (ImageView) findViewById(R.id.del_page);
		page_num = (TextView) findViewById(R.id.page_num);
		
		cardModelId = this.getIntent().getIntExtra("cardModelId", 0);
		selectedPath = this.getIntent().getStringExtra("selectedPath");
		selectedPaths = this.getIntent().getStringArrayListExtra("selectedPaths");
		selectedCardModelIds = this.getIntent().getIntegerArrayListExtra("selectedCardModelIds");
		type = this.getIntent().getStringExtra("type");
		if(type != null && "poster".equals(type)) {
			btn_text.setVisibility(ViewGroup.GONE);
		}
		cardModelIds = this.getIntent().getIntegerArrayListExtra("cardModelIds");
		no = this.getIntent().getIntExtra("no", 0);
//		Log.i("a", "cardModelId = " + cardModelId + "  selectedPath = "
//				+ selectedPath);
//		Log.i("a", "selectedCardModelIds.size() = " + selectedCardModelIds.size());
		View contentView = this.getLayoutInflater().inflate(
				R.layout.popwindow_menu, null);
		popupWindow = new PopupWindow(contentView,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		// popupWindow.setFocusable(true);
		// popupWindow.setBackgroundDrawable(new BitmapDrawable());

//		bm = decodeFile(new File(selectedPaths.get(0)));
//		desbm = addBigFrame(bm, selectedCardModelIds.get(0));
		viewFlipper = (ViewFlipper) this.findViewById(R.id.card_viewflipper);
		
//		for(String path : selectedPaths) {
//			Log.i("a", path);
//		}
		
//		Log.i("a", "id size " + selectedCardModelIds.get(0));
		
//		for(int id : selectedCardModelIds) {
//			Log.i("a", "id = " + id);
//		}
		
		for(int i = 0; i < selectedCardModelIds.size(); i ++) {
			LinearLayout layout = new LinearLayout(this);
			layout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			imageView.setScaleType(ScaleType.FIT_XY);
			bm = decodeFile(new File(selectedPaths.get(i)));
			desbm = addBigFrame(bm, selectedCardModelIds.get(i));
			//desbm = Bitmap.createScaledBitmap(desbm, imageView.getWidth(), imageView.getHeight(), true);
			imageView.setImageBitmap(desbm);
			desbms.add(desbm);
			layout.addView(imageView);
			viewFlipper.addView(layout);
			imageView.setTag("imageViewTags" + i);
			imageViewTags.add("imageViewTags" + i);
		}
		
		View lastview = getLayoutInflater().inflate(R.layout.build_page_last, null);
		viewFlipper.addView(lastview);
		
		page_num.setText("第1/" + selectedPaths.size() + "张");
		
//		View view = viewFlipper.getChildAt(0);
//		ImageView imageView = (ImageView) view.findViewById(R.id.viewflipper_imageview1);
//		imageView.setImageBitmap(desbm);
		left_in = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
		left_out = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
		right_in = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
		right_out = AnimationUtils.loadAnimation(this, R.anim.push_right_out);
		
		
//		Log.i("a", "viewflipper displaychildid = " + viewFlipper.getDisplayedChild());
//		Log.i("a", "viewflipper getChildCount = " + viewFlipper.getChildCount());
//		Log.i("a", "selectedCardModelIds.size = " + selectedCardModelIds.size());
//		Log.i("a", "selectedPaths.size = " + selectedPaths.size());
		viewFlipper.setOnTouchListener(new OnTouchListenerImpl());

		
		
		//往数据库写数据(res : cardmodelid)
		modelService = new ModelService(this);
		bacthAddCard();
		
		
	}
	
	
	public void bacthAddCard() {
		modelService.deleteModelByNo(no);
		for(int i = 0; i < selectedPaths.size(); i++) {
//			Log.i("no", "no ----------- " + no);
			model = new Model();
			model.setNo(no);
			model.setType(type);
			model.setCardmodelid(selectedCardModelIds.get(i));
			model.setPath(selectedPaths.get(i));
			modelService.add(model);
		}
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
						Toast.makeText(BuildCardActivity.this, "当前已经是第一页了", 1).show();
						break;
					}
				}else if(startX - endX > 10){
					if(viewFlipper.getDisplayedChild() != selectedPaths.size()) {
						viewflipper_shownext();
					}else {
						Toast.makeText(BuildCardActivity.this, "当前已经是最后一 页了", 1).show();
						break;
					}
				}
				break;
			}
			return true;
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

	/**
	 * 图片重合
	 * 
	 * @param view
	 */

	private Bitmap addBigFrame(Bitmap bm, int is) {
		Bitmap bitmap = decodeBitmap(is);
		Drawable[] array = new Drawable[2];
		array[0] = new BitmapDrawable(bm);
		// Bitmap b = resize(bitmap, bm.getWidth(), bm.getHeight());
		array[1] = new BitmapDrawable(bitmap);
		LayerDrawable layer = new LayerDrawable(array);
		return drawableToBitmap(layer);

	}

	private Bitmap drawableToBitmap(LayerDrawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;

	}

	private Bitmap resize(Bitmap bitmap, int width, int height) {
		bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
		return bitmap;
	}

	private Bitmap decodeBitmap(int res) {
		return BitmapFactory.decodeResource(getResources(), res);
		// return BitmapFactory.decodeStream(is);

	}

	/**
	 * 
	 * @param view
	 */

	public void viewflipper_shownext() {
		btn_previous.setImageResource(R.drawable.btn_previous);
		btn_previous.setEnabled(true);
		page_index = page_index + 1;
//		Log.i("a", "page_index = " + page_index);
		if(viewFlipper.getDisplayedChild() == viewFlipper.getChildCount() - 2) {
			btn_next.setImageResource(R.drawable.btn_next_off);
			btn_next.setEnabled(false);
			btn_text.setEnabled(false);
			btn_template.setEnabled(false);
			btn_delpage.setEnabled(false);
			btn_text.setImageResource(R.drawable.btn_edit_text_off);
			btn_template.setImageResource(R.drawable.btn_edit_template_off);
			btn_delpage.setImageResource(R.drawable.btn_edit_del_off);
			page_num.setText("菜单");
			viewFlipper.showNext();
			//viewFlipper.showNext();
			//Toast.makeText(this, "当前已经是最后一页了", 1).show();
		}else {
			//btn_previous.setImageResource(R.drawable.btn_previous_off);
			
			btn_text.setEnabled(true);
			btn_template.setEnabled(true);
			btn_delpage.setEnabled(true);
			btn_text.setImageResource(R.drawable.btn_edit_text);
			btn_template.setImageResource(R.drawable.btn_edit_template);
			btn_delpage.setImageResource(R.drawable.btn_edit_del);
			
			
			viewFlipper.showNext();
			page_num.setText("第" + page_index + "/" + selectedPaths.size() + "张");
			viewFlipper.setInAnimation(left_in);
			viewFlipper.setOutAnimation(right_out);
		}
	}
	
	public void viewflipper_showpervious() {
		btn_next.setImageResource(R.drawable.btn_next);
		btn_next.setEnabled(true);
		page_index = page_index - 1;
		btn_text.setEnabled(true);
		btn_template.setEnabled(true);
		btn_delpage.setEnabled(true);
		btn_text.setImageResource(R.drawable.btn_edit_text);
		btn_template.setImageResource(R.drawable.btn_edit_template);
		btn_delpage.setImageResource(R.drawable.btn_edit_del);
		if(viewFlipper.getDisplayedChild() == 1) {
			btn_previous.setImageResource(R.drawable.btn_previous_off);
			btn_previous.setEnabled(false);
			viewFlipper.showPrevious();
			//Toast.makeText(this, "当前已经是第一页了", 1).show();
			page_num.setText("第" + page_index + "/" + selectedPaths.size() + "张");
		}else {
			page_num.setText("第" + page_index + "/" + selectedPaths.size() + "张");
			viewFlipper.showPrevious();
			viewFlipper.setInAnimation(right_in);
			viewFlipper.setOutAnimation(left_out);
		}
	}
	
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.index_from_buildCard:
			BuildCardActivity.this.finish();
			intent = new Intent(BuildCardActivity.this,
					IndexActivity.class);
			startActivity(intent);
			break;
		case R.id.tools_more:
			tools_more.setVisibility(ViewGroup.GONE);
			View parent = this.findViewById(R.id.book_frame);
			popupWindow.showAtLocation(parent, Gravity.RIGHT | Gravity.TOP,
					-10, 20);
			break;
		case R.id.btn_next:
			viewflipper_shownext();
//			btn_previous.setImageResource(R.drawable.btn_previous);
//			btn_previous.setEnabled(true);
//			page_index = page_index + 1;
//			Log.i("a", "page_index = " + page_index);
//			if(viewFlipper.getDisplayedChild() == viewFlipper.getChildCount() - 2) {
//				btn_next.setImageResource(R.drawable.btn_next_off);
//				btn_next.setEnabled(false);
//				page_num.setText("菜单");
//				viewFlipper.showNext();
//				//viewFlipper.showNext();
//				//Toast.makeText(this, "当前已经是最后一页了", 1).show();
//			}else {
//				//btn_previous.setImageResource(R.drawable.btn_previous_off);
//				viewFlipper.showNext();
//				page_num.setText("第" + page_index + "/" + selectedPaths.size() + "张");
//				viewFlipper.setInAnimation(left_in);
//				viewFlipper.setOutAnimation(right_out);
//			}
			break;
		case R.id.btn_previous:
			viewflipper_showpervious();
//			btn_next.setImageResource(R.drawable.btn_next);
//			btn_next.setEnabled(true);
//			page_index = page_index - 1;
//			if(viewFlipper.getDisplayedChild() == 1) {
//				btn_previous.setImageResource(R.drawable.btn_previous_off);
//				btn_previous.setEnabled(false);
//				viewFlipper.showPrevious();
//				//Toast.makeText(this, "当前已经是第一页了", 1).show();
//				page_num.setText("第" + page_index + "/" + selectedPaths.size() + "张");
//			}else {
//				page_num.setText("第" + page_index + "/" + selectedPaths.size() + "张");
//				viewFlipper.showPrevious();
//				viewFlipper.setInAnimation(right_in);
//				viewFlipper.setOutAnimation(left_out);
//			}
			break;
			
		case R.id.add_page:
			BuildCardActivity.this.finish();
			if(type != null && "poster".equals(type)) {
				intent = new Intent(BuildCardActivity.this, ChoosePosterModelActivity.class);
			}else {
				intent = new Intent(BuildCardActivity.this, ChooseCardModelActivity.class);
			}
			intent.putStringArrayListExtra("selectedPaths", selectedPaths);
			intent.putIntegerArrayListExtra("selectedCardModelIds", selectedCardModelIds);
			intent.putExtra("no", no);
			intent.putExtra("type", type);
			startActivity(intent);
			break;
			
		case R.id.del_page:
			Dialog dialog = new AlertDialog.Builder(BuildCardActivity.this)
			.setTitle("删除照片")
			.setMessage("确定删除该页?")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					
					if(viewFlipper.getDisplayedChild() == viewFlipper.getChildCount() - 2) {
						page_num.setText("菜单");
					}else {
						page_num.setText("第" + page_index + "/" + selectedPaths.size() + "张");
					}
					
					
					viewFlipper.removeViewAt(viewFlipper.getDisplayedChild());
					selectedPaths.remove(viewFlipper.getDisplayedChild());
					selectedCardModelIds.remove(viewFlipper.getDisplayedChild());
					Log.i("a", "page_index = " + page_index);
					
					
					bacthAddCard();
					
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			})
			.create();
			
			if(selectedPaths.size() == 1) {
				Toast.makeText(BuildCardActivity.this, "制作卡片不能少于一张", 0).show();
				break;
			}else {
				dialog.show();
			}
			break;
			
		case R.id.template:
			imageView = (ImageView) viewFlipper.getCurrentView().findViewWithTag(imageViewTags.get(viewFlipper.getDisplayedChild()));
			view = imageView.getRootView();
			bm = decodeFile(new File(selectedPaths.get(viewFlipper.getDisplayedChild())));
			if(location >= cardModelIds.size()) {
				location = 0;
			}
			desbm = addBigFrame(bm, cardModelIds.get(location));
			imageView.setImageBitmap(desbm);
			Log.i("a",  location + "");
			viewFlipper.bringChildToFront(view);
			
			
			selectedPath = selectedPaths.get(viewFlipper.getDisplayedChild());
			
			if(selectedCardModelIds.size() <= 1) {
				selectedCardModelIds = new ArrayList<Integer>();
				selectedPaths = new ArrayList<String>();
			}else {
				selectedCardModelIds.remove(viewFlipper.getDisplayedChild());
				selectedPaths.remove(viewFlipper.getDisplayedChild());
			}
			selectedCardModelIds.add(viewFlipper.getDisplayedChild(), cardModelIds.get(location));
			selectedPaths.add(viewFlipper.getDisplayedChild(), selectedPath);
			
			bacthAddCard();
			
			location += 1;
			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			if (popupWindow.isShowing()) {
				popupWindow.dismiss();
				tools_more.setVisibility(ViewGroup.VISIBLE);
			}
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (popupWindow.isShowing()) {
			popupWindow.dismiss();
			popupWindow = null;
		}
		modelService.closeDB();
	}
}
