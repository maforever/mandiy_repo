package com.lanrui.andiy.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrui.andiy.R;
import com.lanrui.andiy.adapter.IndexGridViewAdapter;
import com.lanrui.andiy.model.Model;
import com.lanrui.andiy.service.ModelService;
import com.lanrui.andiy.service.SystemPicService;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode.VmPolicy;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewManager;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.SlidingDrawer.OnDrawerScrollListener;

public class IndexActivity extends Activity {
	private LayoutInflater inflater = null;
	private Dialog introduce_dialog = null;
	private ImageView close = null;
	private ImageView img = null;
	private ImageView bulid = null;
	private ImageView func_btn = null;
	private TextView content = null;
	private TextView rule = null;
	private TextView more = null;
	private int path = 0;
	private RelativeLayout container = null;
	private ModelService modelService;
	private List<Integer> nos;
	private List<List<Model>> modelss;
	private GridView indexGridView = null;
	private boolean isFunc_btn_click = false;
	private IndexGridViewAdapter adapter = null;
	private List<String> selectedPaths = null;
	private List<Model> models = null;
	private ImageView title = null;
	private String type;
	private Intent intent;
	private ArrayList<Integer> selectedCardModelIds = new ArrayList<Integer>();
	private ArrayList<Integer> cardModelIds = new ArrayList<Integer>();
	private ProgressDialog dialog;
	private String textStr;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.x = -150;
		getWindow().setAttributes(lp);
		
		indexGridView = (GridView) findViewById(R.id.index_grid);
		
		
		modelService = new ModelService(this);
//		int count = (int) modelService.getCount();
		more = (TextView) findViewById(R.id.more);
		container = (RelativeLayout) findViewById(R.id.container);
		func_btn = (ImageView) findViewById(R.id.func_btn);
		func_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!isFunc_btn_click) {
					func_btn.setImageResource(R.drawable.btn_index_done);
					isFunc_btn_click = true;
					adapter.isShowDeleteBtn(true);
				}else {
					func_btn.setImageResource(R.drawable.index_btn_edit);
					isFunc_btn_click = false;
					adapter.isShowDeleteBtn(false);
					//indexGridViewAdapter();
					getModelss();
				}
			}
		});
		
		getModelss();
		
//		if(count == 0) {
//			func_btn.setVisibility(ViewGroup.GONE);
//			more.setVisibility(ViewGroup.VISIBLE);
//			more.setText("您还没有任何作品,\n赶快动手制作吧!");
//		}else {
//			func_btn.setVisibility(ViewGroup.VISIBLE);
//			nos = modelService.getNoGroupByNo();
//			modelss = modelService.getModelsByNos(nos);
//			indexGridViewAdapter();
//		}
		indexGridView.setOnItemClickListener(new OnItemClickListenerImple());
		
		
		
		
		inflater = this.getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_introduce, null);
		close = (ImageView) view.findViewById(R.id.close);
		img = (ImageView) view.findViewById(R.id.img);
		title = (ImageView) view.findViewById(R.id.title);
		content = (TextView) view.findViewById(R.id.content);
		rule = (TextView) view.findViewById(R.id.rule);
		introduce_dialog = new AlertDialog.Builder(this).setView(view).create();
		close.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				path = 0;
				introduce_dialog.dismiss();
			}
		});
		bulid = (ImageView) view.findViewById(R.id.build);
		bulid.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				switch (path) {
				case 1:
					introduce_dialog.dismiss();
					
					
					
					
					dialog = new ProgressDialog(IndexActivity.this);
					dialog.setMessage("加载图片请稍等");
					dialog.show();
					
					new Thread(new Runnable() {
						public void run() {
					
							Intent intent = new Intent(IndexActivity.this, SelectPicActivity.class);
							String textStr = "选择要印刷的照片";
							intent.putExtra("textStr", textStr);
//							IndexActivity.this.finish();
							IndexActivity.this.startActivity(intent);
							dialog.dismiss();
							
						}
					}).start();
					
					
					break;
				case 2:
					ArrayList<Integer> bookModelIds = new ArrayList<Integer>();
					bookModelIds.add(R.drawable.cardmodel_xk1);
					bookModelIds.add(R.drawable.cardmodel_xk2);
					bookModelIds.add(R.drawable.cardmodel_xk3);
					bookModelIds.add(R.drawable.cardmodel_xk4);
					bookModelIds.add(R.drawable.cardmodel_xk5);
					
					ArrayList<Integer> bookModelFirstPageIds = new ArrayList<Integer>();
					bookModelFirstPageIds.add(R.drawable.cardmodel_xk1);
					bookModelFirstPageIds.add(R.drawable.cardmodel_xk2);
					
					introduce_dialog.dismiss();
//					IndexActivity.this.finish();
					intent = new Intent(IndexActivity.this, SelectPicActivity.class);
					type = "book";
					intent.putExtra("type", type);
					textStr = "选择照片开始作书";
					intent.putExtra("textStr", textStr);
					intent.putIntegerArrayListExtra("bookModelIds", bookModelIds);
					intent.putIntegerArrayListExtra("bookModelFirstPageIds", bookModelFirstPageIds);
					IndexActivity.this.finish();
					IndexActivity.this.startActivity(intent);
					break;
				case 3:
					
					introduce_dialog.dismiss();
//					IndexActivity.this.finish();
					intent = new Intent(IndexActivity.this, ChooseCardModelActivity.class);
					type = "card";
					intent.putExtra("type", type);
					IndexActivity.this.finish();
					IndexActivity.this.startActivity(intent);
					
					
					System.out.println(path);
					break;
				case 4:
					introduce_dialog.dismiss();
//					IndexActivity.this.finish();
					intent = new Intent(IndexActivity.this, ChoosePosterModelActivity.class);
					type = "poster";
					intent.putExtra("type", type);
					IndexActivity.this.finish();
					IndexActivity.this.startActivity(intent);
					break;
				}
			}
		});
	}
	
	public void getModelss() {
		int count = (int) modelService.getCount();
		if(count == 0) {
		func_btn.setVisibility(ViewGroup.GONE);
		more.setVisibility(ViewGroup.VISIBLE);
		more.setText("您还没有任何作品,\n赶快动手制作吧!");
	}else {
		func_btn.setVisibility(ViewGroup.VISIBLE);
		nos = modelService.getNoGroupByNo();
		modelss = modelService.getModelsByNos(nos);
		indexGridViewAdapter();
	}
		
	}
	
	private void initCardModelIds(String str) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = R.drawable.class.getDeclaredFields();
		
		for(int i = 0; i < fields.length; i ++) {
			if(fields[i].getName().startsWith(str)) {
				cardModelIds.add(fields[i].getInt(R.drawable.class));
			}
		}
	}
	
	public class OnItemClickListenerImple implements OnItemClickListener {
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
			ArrayList<String> selectedPaths = new ArrayList<String>();
			models = modelss.get(position);
			for(Model m : models) {
				selectedPaths.add(m.getPath());
				selectedCardModelIds.add(m.getCardmodelid());
//				Log.i("a", "getCardmodelid = " + m.getCardmodelid());
			}
			
			Log.i("a", " selectedCardModelIds size() =  " + selectedCardModelIds.size() );
			
			if(models.get(0).getType() != null && "card".equals(models.get(0).getType()) || "poster".equals(models.get(0).getType())) {
//				Log.i("a", " type ---- " + models.get(0).getType());
				try {
					initCardModelIds("cardmodel_");
				} catch (Exception e) {
					e.printStackTrace();
				}
				intent = new Intent(IndexActivity.this, BuildCardActivity.class);
				intent.putExtra("type", models.get(0).getType());
				intent.putIntegerArrayListExtra("cardModelIds", cardModelIds);
			}else {
				intent = new Intent(IndexActivity.this, PicPreviewActivity.class);
			}
			intent.putStringArrayListExtra("selectedPaths", selectedPaths);
			intent.putExtra("no", nos.get(position));
			intent.putExtra("flag", "from_index");
//			Log.i("a", " selectedCardModelIds size = " + selectedCardModelIds.size());
			intent.putIntegerArrayListExtra("selectedCardModelIds", selectedCardModelIds);
			IndexActivity.this.finish();                            
			startActivity(intent);
			
			
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(modelService != null) {
			Log.i("a", "indexActivity close");
			modelService.closeDB();
			modelService = null;
		}
		ActivityManager actMgr = (ActivityManager)getSystemService(ACTIVITY_SERVICE);

		actMgr.restartPackage(getPackageName());
	}
	
	private void indexGridViewAdapter() {
		adapter = new IndexGridViewAdapter(this, modelss, indexGridView);
		indexGridView.setAdapter(adapter);
	}

	public void indexBtnClick(View view) {
		switch (view.getId()) {
		case R.id.build_pic:
			img.setImageResource(R.drawable.introduce_img_pic_view);
			content.setText("张数：1~32张\n材质：300g哑粉");
			rule.setText("100X145mm(接近6寸相片)");
			path = 1;
			introduce_dialog.show();
			break;
		case R.id.build_book:
			img.setImageResource(R.drawable.introduce_img_album_view);
			content.setText("页数：30~46页\n装订：胶装\n封面：250g\n内页:xxxxxx");
			rule.setText("135X180mm");
			title.setImageResource(R.drawable.introduce_img_album_title);
			path = 2;
			introduce_dialog.show();
			break;
		case R.id.build_card:
			img.setImageResource(R.drawable.introduce_img_card_view);
			content.setText("张数:1~48张\n材质:300g");
			rule.setText("100X145mm");
			title.setImageResource(R.drawable.introduce_img_card_title);
			path = 3;
			introduce_dialog.show();
			break;
		case R.id.build_poster:
			img.setImageResource(R.drawable.introduce_img_poster_view);
			content.setText("张数:1~48张\n材质:300g");
			rule.setText("100X145mm");
			path = 4;
			introduce_dialog.show();
			break;
		}
	}
	
	public void show_menu_btn(View view) {
		IndexMenuActivity.prepare(IndexActivity.this,
				R.id.root);
		startActivity(new Intent(IndexActivity.this,
				IndexMenuActivity.class));
		overridePendingTransition(0, 0);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this).setTitle("确定退出程序？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					IndexActivity.this.finish();
//					android.os.Process.killProcess(android.os.Process.myPid());
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).create().show();
		}
		return true;
	}
}
