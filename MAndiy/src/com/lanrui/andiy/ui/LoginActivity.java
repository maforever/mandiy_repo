package com.lanrui.andiy.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanrui.andiy.R;
import com.lanrui.andiy.oauth.qq.GetUserInfoClickListener;
import com.tencent.tauth.TencentOpenAPI;
import com.tencent.tauth.TencentOpenAPI2;
import com.tencent.tauth.TencentOpenHost;
import com.tencent.tauth.bean.OpenId;
import com.tencent.tauth.bean.UserInfo;
import com.tencent.tauth.http.Callback;
import com.tencent.tauth.http.TDebug;
import com.tencent.weibo.api.TAPI;
import com.tencent.weibo.api.UserAPI;
import com.tencent.weibo.constants.OAuthConstants;
import com.tencent.weibo.oauthv2.OAuthV2;
import com.tencent.weibo.webview.OAuthV2AuthorizeWebView;

public class LoginActivity extends Activity {
	private ArrayList<String> selectedPaths;
	private ArrayList<String> paths;
	private String flag;
	private Intent intent = null;
	private String can_anonymous = null;
	private ImageView btn_login_anonymous = null;
	private Intent intent2 = null;
	
	/*
	 * QQ
	 */
	private String scope = "get_user_info,get_user_profile,add_share,add_topic,list_album,upload_pic,add_album";//授权范围
	private static final String CALLBACK = "auth://tauth.qq.com/";
	public String mAppId = "222222";//"100353751";
	public String mOpenId, mAccessToken;
	private Button get_user_info = null; 
	private AuthReceiver authReceiver = null;
	private ImageView qqLogin_imageView = null;
	private String nickname = null;
	private String icon = null;
	
	/*
	 * QQWeiBo
	 */
    //!!!请根据您的实际情况修改!!!      认证成功后浏览器会被重定向到这个url中  必须与注册时填写的一致
    private String redirectUri="http://www.tencent.com/zh-cn/index.shtml";                   
    //!!!请根据您的实际情况修改!!!      换为您为自己的应用申请到的APP KEY
    private String clientId = "801115505"; 
    //!!!请根据您的实际情况修改!!!      换为您为自己的应用申请到的APP SECRET
    private String clientSecret="be1dd1410434a9f7d5a2586bab7a6829";	
    private OAuthV2 oAuth;
    private UserAPI userAPI = null;
    private TAPI tAPI = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_login);
		
		selectedPaths = this.getIntent().getStringArrayListExtra("selectedPaths");
		paths = this.getIntent().getStringArrayListExtra("paths");
		flag = this.getIntent().getStringExtra("flag");
		can_anonymous = this.getIntent().getStringExtra("can_anonymous");
		
		btn_login_anonymous = (ImageView) this.findViewById(R.id.login_anonymous);
		
		if(can_anonymous != null  && "can_anonymous".equals(can_anonymous)) {
			btn_login_anonymous.setVisibility(ViewGroup.VISIBLE);
		}
		
		
		qqLogin_imageView = (ImageView) this.findViewById(R.id.login_qq);
		registerIntentReceivers();
		//qqLogin_imageView.setOnClickListener(new GetUserInfoClickListener(this));
	}

	
	
    private void registerIntentReceivers() {
    	authReceiver = new AuthReceiver();
    	IntentFilter filter = new IntentFilter();
    	filter.addAction(TencentOpenHost.AUTH_BROADCAST);
    	registerReceiver(authReceiver, filter);
	}
	
	
	public class AuthReceiver extends BroadcastReceiver 
    {
       private static final String TAG="AuthReceiver";
       public void onReceive(Context context, Intent intent)
       {
          Bundle exts = intent.getExtras();
          String raw =  exts.getString("raw");
          String access_token =  exts.getString("access_token");
          String expires_in =  exts.getString("expires_in");
          Log.i(TAG, String.format("raw: %s, access_token:%s, expires_in:%s", raw, access_token, expires_in));
          if (access_token != null) 
          {
           //获取到access token
           mAccessToken = access_token;
           //((TextView)findViewById(R.id.access_token)).setText(access_token);
//           TDebug.msg("正在获取OpenID...", getApplicationContext());
           //用access token 来获取open id
           //mTencentOpenAPI.openid(access_token, new OpenIDListener());
           TencentOpenAPI.openid(mAccessToken, new Callback(){


			public void onSuccess(final Object obj) {
				runOnUiThread(new Runnable() {
					public void run() {
						mOpenId = ((OpenId)obj).getOpenId();
						
						
						TencentOpenAPI.userInfo(mAccessToken, mAppId, mOpenId, new Callback() {
							
							public void onSuccess(final Object obj) {
								LoginActivity.this.runOnUiThread(new Runnable() {
									
									public void run() {
										//Log.i("a", obj.toString());
										try {
											UserInfo qqUserInfo = (UserInfo)obj;
											nickname = qqUserInfo.getNickName();
											icon = qqUserInfo.getIcon_100();
											System.out.println(nickname);
											System.out.println(icon);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
									//登陆成功首界面
								});
								LoginActivity.this.finish();
								intent2 = new Intent(LoginActivity.this, MyOrderHeaderActivity.class);
								intent2.putExtra("nickname", nickname);
								intent2.putExtra("icon", icon);
								LoginActivity.this.startActivity(intent2);
								
								
							}
							
							public void onFail(final int ret, final String msg) {
								LoginActivity.this.runOnUiThread(new Runnable() {
									
									public void run() {
										TDebug.msg(ret + ": " + msg, LoginActivity.this);
									}
								});
							}

							public void onCancel(int flag) {
								// TODO Auto-generated method stub
								
							}
						});
						
						
						
						
						
					}
				});
				

				
				
			}

			public void onCancel(int flag) {
				// TODO Auto-generated method stub
				
			}

			public void onFail(int ret, final String msg) {
				runOnUiThread(new Runnable() {
					public void run() {
						TDebug.msg(msg, getApplicationContext());
					}
				});
			}
        	   
           });
           
           
          }
       }
    }
	
	
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	if (authReceiver != null) {
        	unregisterIntentReceivers();
    	}
    }
    
	private void unregisterIntentReceivers() {
		LoginActivity.this.unregisterReceiver(authReceiver);
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			LoginActivity.this.finish();
			if(flag != null && "from_index".equals(flag)) {
				intent = new Intent(LoginActivity.this, PicPreviewActivity.class);
				intent.putStringArrayListExtra("selectedPaths", selectedPaths);
				intent.putStringArrayListExtra("paths", paths);
				intent.putExtra("flag", flag);
				startActivity(intent);
			}else if(flag != null && "from_indexmenuactivity".equals(flag)) {
				LoginActivity.this.finish();
//				intent = new Intent(LoginActivity.this, IndexActivity.class);
//				startActivity(intent);
			}
		break;
		
		case R.id.login_qq:
			TencentOpenAPI2.logIn(this.getApplicationContext(), mOpenId, scope, mAppId, "_self", CALLBACK, null, null);
		break;
		
		case R.id.login_qqweibo:
	        oAuth=new OAuthV2(redirectUri);
	        oAuth.setClientId(clientId);
	        oAuth.setClientSecret(clientSecret);
	        
	        userAPI = new UserAPI(OAuthConstants.OAUTH_VERSION_2_A);
	        
	        
	        intent = new Intent(LoginActivity.this, OAuthV2AuthorizeWebView.class);
	    	intent.putExtra("oauth", oAuth);
	    	this.startActivityForResult(intent, 2);	
	        
	        
	        

			
			break;
		
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 2) {
            if (resultCode==OAuthV2AuthorizeWebView.RESULT_CODE)    {
                oAuth=(OAuthV2) data.getExtras().getSerializable("oauth");
                if(oAuth.getStatus()==0)
                    Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                
    			new Thread(new Runnable() {
    				String response = null;
    				public void run() {
    					try {
    						response = userAPI.info(oAuth, "json");
    						JSONObject json = new JSONObject(response).getJSONObject("data");
    						nickname = json.getString("nick");
    						icon = json.getString("head");
    						userAPI.shutdownConnection();
    						
    						intent2 = new Intent(LoginActivity.this, MyOrderHeaderActivity.class);
    						intent2.putExtra("nickname", nickname);
    						intent2.putExtra("icon", icon);
    						LoginActivity.this.startActivity(intent2);
    						
    					} catch (Exception e) {
    						e.printStackTrace();
    					}
    				}
    			}).start();
                
                
            }
		}
	}
}




































