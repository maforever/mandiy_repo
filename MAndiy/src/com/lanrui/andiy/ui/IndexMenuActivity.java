package com.lanrui.andiy.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrui.andiy.R;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;
import android.widget.SlidingDrawer;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.SlidingDrawer.OnDrawerScrollListener;

public class IndexMenuActivity extends Activity {
	private ImageView mCover;
	private Animation mStartAnimation;
	private Animation mStopAnimation;
	private static final int DURATION_MS = 400;
	private static Bitmap sCoverBitmap = null;
	private RadioGroup radioGroup = null;
	private Intent intent = null;
	private RadioButton radioButton = null;
//	String loveyouString[] = new String[] { "���Ӱ׹�����", "˧�� ˧�� ˧�� ˧��", "˧��",
//			"��Ů", "˧�� ˧�� ˧��", "˧�� ˧�� ˧�� ˧��", "˧�� ˧�� ˧��", "���ϵ�����Ц����" };

	public static void prepare(Activity activity, int id) {
		if (sCoverBitmap != null) {
			sCoverBitmap.recycle();
		}
		// ��ָ����С����һ��͸����32λλͼ������������һ��canvas����
		sCoverBitmap = Bitmap.createBitmap(
				activity.findViewById(id).getWidth(), activity.findViewById(id)
						.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(sCoverBitmap);
		// ��ָ����view��������view��Ⱦ�����ֻ����ϣ����������һ��activity���ֵ�һ�����գ��������bitmap�Ͼ�����һ��activity�Ŀ���
		activity.findViewById(id).draw(canvas);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���Բ������ϲ㸲����һ��imageview
		setContentView(R.layout.index_menu);
		initAnim();
		mCover = (ImageView) findViewById(R.id.slidedout_cover);
		mCover.setImageBitmap(sCoverBitmap);
		mCover.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				close();
			}
		});
		
		
		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioGroup.setOnCheckedChangeListener(new CheckedListener());
		
//		Log.i("a", "radioButtonId = " + radioButtonId);
		
		
		
//		RadioButton radioButton = (RadioButton) radioGroup.findViewById(R.id.menu_setting);
//		radioButton.setBackgroundResource(R.drawable.selector_menu_item);
//		radioButton.setBackgroundColor(this.getResources().getColor(R.color.menu_item_checked));
		
		
		open();
		
	}
	
	public void btnClick(View view) {
		close();
	}
	
    private final class CheckedListener implements OnCheckedChangeListener {
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if(checkedId == R.id.menu_index2) {
				Toast.makeText(IndexMenuActivity.this, "menu_index", Toast.LENGTH_LONG).show();
				close();
			}else if(checkedId == R.id.menu_order) {
				close();
				IndexMenuActivity.this.finish();
				intent = new Intent(IndexMenuActivity.this, LoginActivity.class);
				intent.putExtra("flag", "from_indexmenuactivity");
				intent.putExtra("can_anonymous", "can_anonymous");
				startActivity(intent);
				//Toast.makeText(IndexMenuActivity.this, "menu_order", Toast.LENGTH_LONG).show();
			}else if(checkedId == R.id.menu_gold) {
				IndexMenuActivity.this.finish();
				intent = new Intent(IndexMenuActivity.this, LoginActivity.class);
				intent.putExtra("flag", "from_indexmenuactivity");
				startActivity(intent);
				//Toast.makeText(IndexMenuActivity.this, "menu_gold", Toast.LENGTH_LONG).show();
			}else if(checkedId == R.id.menu_upload) {
				Toast.makeText(IndexMenuActivity.this, "menu_upload", Toast.LENGTH_LONG).show();
			}else if(checkedId == R.id.menu_message) {
				IndexMenuActivity.this.finish();
				intent = new Intent(IndexMenuActivity.this, LoginActivity.class);
				intent.putExtra("flag", "from_indexmenuactivity");
				intent.putExtra("can_anonymous", "can_anonymous");
				startActivity(intent);
				//Toast.makeText(IndexMenuActivity.this, "menu_message", Toast.LENGTH_LONG).show();
			}else if(checkedId == R.id.menu_setting) {
				close();
				IndexMenuActivity.this.finish();
				intent = new Intent(IndexMenuActivity.this, SettingActivity.class);
				startActivity(intent);
//				Toast.makeText(IndexMenuActivity.this, "menu_setting", Toast.LENGTH_LONG).show();
			}
		}
    }

	public void initAnim() {

		// �����˾��Բ��֣����Բ�����view�����ϽǴ�(0,0)��ʼ
		@SuppressWarnings("deprecation")
		final android.widget.AbsoluteLayout.LayoutParams lp = new android.widget.AbsoluteLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 0, 0);
		findViewById(R.id.slideout_placeholder).setLayoutParams(lp);

		// ��Ļ�Ŀ���
		int displayWidth = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getWidth();
		// �ұߵ�λ������60dpת����px
		int sWidth = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 60, getResources()
						.getDisplayMetrics());
		// �����������ƶ���ƫ����
		final int shift = displayWidth - sWidth;

		// �����ƶ���λ�ƶ��������ƶ�shift���룬y���򲻱�
		mStartAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE,
				0, TranslateAnimation.ABSOLUTE, shift,
				TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, 0);

		// ����ʱ��λ�ƶ���
		mStopAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0,
				TranslateAnimation.ABSOLUTE, -shift,
				TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, 0);
		// ����ʱ��
		mStartAnimation.setDuration(DURATION_MS);
		// �������ʱͣ���ڽ���λ��
		mStartAnimation.setFillAfter(true);
		mStartAnimation.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
				// ��������ʱ�ص�
				// ��imageview�̶���λ�ƺ��λ��
				mCover.setAnimation(null);
				@SuppressWarnings("deprecation")
				final android.widget.AbsoluteLayout.LayoutParams lp = new android.widget.AbsoluteLayout.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT,
						shift, 0);
				mCover.setLayoutParams(lp);
			}
		});

		mStopAnimation.setDuration(DURATION_MS);
		mStopAnimation.setFillAfter(true);
		mStopAnimation.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
				finish();
				overridePendingTransition(0, 0);
			}
		});

	}

	public void open() {
		mCover.startAnimation(mStartAnimation);
	}

	public void close() {
		mCover.startAnimation(mStopAnimation);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// �����ؼ�ʱҲҪ��������
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			close();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}