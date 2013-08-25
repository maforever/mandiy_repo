package com.lanrui.andiy.ui;



import com.lanrui.andiy.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class WelcomeActivity extends Activity {

	private ViewFlipper viewFlipper = null;
	private float startx, endx;
	private ImageView start_btn = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_welcome);
		start_btn = (ImageView) findViewById(R.id.start_btn);
		start_btn.setOnClickListener(new OnClickListenerImpl());
		viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
	}

	public final class OnClickListenerImpl implements View.OnClickListener {
		public void onClick(View v) {
			Intent intent = new Intent(WelcomeActivity.this, IndexActivity.class);
			WelcomeActivity.this.startActivity(intent);
			WelcomeActivity.this.finish();
		}
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
    	if(event.getAction() == MotionEvent.ACTION_DOWN) {
    		startx = event.getX();
    	}else if(event.getAction() == MotionEvent.ACTION_UP) {
    		int viewFlipperId = viewFlipper.getDisplayedChild();
    		System.out.println(viewFlipperId);
    		endx = event.getX();
    		if(endx > startx) {
//    			viewFlipper.setInAnimation(enter_lr_animation);
//    			viewFlipper.setOutAnimation(out_lr_animation);
    			if(viewFlipperId != 3) {
    				viewFlipper.showNext();
    			}
    		}else if(endx < startx) {
//    			viewFlipper.setInAnimation(enter_rl_animation);
//    			viewFlipper.setOutAnimation(out_rl_animation);
    			if(viewFlipperId != 0) {
    				viewFlipper.showPrevious();
    			}
    		}
    		return true;
    	}
    	return super.onTouchEvent(event);
	}
}
