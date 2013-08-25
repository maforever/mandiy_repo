package com.lanrui.andiy.oauth.qq;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.lanrui.andiy.ui.LoginActivity;
import com.tencent.tauth.TencentOpenAPI;
import com.tencent.tauth.http.Callback;
import com.tencent.tauth.http.TDebug;

/**
 * @author email:csshuai2009@gmail.com qq:65112183
 * @version 创建时间�?011-9-16 上午11:15:55
 * 类说�?
 */
public class GetUserInfoClickListener implements OnClickListener {
	private LoginActivity mActivity;

	public GetUserInfoClickListener(LoginActivity activity) {
		mActivity = activity;
	}

	public void onClick(View v) {
		Log.i("a", "click");
		TencentOpenAPI.userInfo(mActivity.mAccessToken, mActivity.mAppId, mActivity.mOpenId, new Callback() {
			
			public void onSuccess(final Object obj) {
				mActivity.runOnUiThread(new Runnable() {
					
					public void run() {
						Log.i("a", obj.toString());
					}
				});
			}
			
			public void onFail(final int ret, final String msg) {
				mActivity.runOnUiThread(new Runnable() {
					
					public void run() {
						TDebug.msg(ret + ": " + msg, mActivity);
					}
				});
			}

			public void onCancel(int flag) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
