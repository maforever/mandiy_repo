package com.lanrui.andiy.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.lanrui.andiy.R;
import com.lanrui.andiy.adapter.SystemImageAdaptor;
import com.lanrui.andiy.model.SystemImages;
import com.lanrui.andiy.service.SystemPicService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectPicActivity extends Activity {
	private ArrayList<String> paths = null;
	private ArrayList<String> selectedPaths = new ArrayList<String>();
	private ArrayList<Integer> cardModelIds = new ArrayList<Integer>();
	private TextView titlename = null;
	private ImageView cancel = null;
	private ListView listView = null;
	private SystemPicService picService = null;
	private Set<String> all_pic_path = new HashSet<String>();
	private List<SystemImages> lists = new ArrayList<SystemImages>();
	private String type ;
	private int cardModelId;
	private ArrayList<Integer> selectedCardModelIds = new ArrayList<Integer>();
	private Intent intent;
	private int no;
	private ArrayList<Integer> bookModelIds = new ArrayList<Integer>();
	private ArrayList<Integer> bookModelFirstPageIds = new ArrayList<Integer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action_bar_select_pic);
		String titleStr = (String) this.getIntent().getExtras().get("textStr");
		type = this.getIntent().getStringExtra("type");
		cardModelId = this.getIntent().getIntExtra("cardModelId", 0);
		selectedCardModelIds = this.getIntent().getIntegerArrayListExtra("selectedCardModelIds");
		cardModelIds = this.getIntent().getIntegerArrayListExtra("cardModelIds");
		no = this.getIntent().getIntExtra("no", 0);
		bookModelIds = this.getIntent().getIntegerArrayListExtra("bookModelIds");
		bookModelFirstPageIds = this.getIntent().getIntegerArrayListExtra("bookModelFirstPageIds");
		titlename = (TextView) findViewById(R.id.title_name);
		titlename.setText(titleStr);
		cancel = (ImageView) findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SelectPicActivity.this.finish();
				Intent intent = new Intent(SelectPicActivity.this, IndexActivity.class);
				startActivity(intent);
			}
		});
		
		//paths = getIntent().getStringArrayListExtra("paths");
		selectedPaths = getIntent().getStringArrayListExtra("selectedPaths");
		
		picService = new SystemPicService(SelectPicActivity.this);
		listView = (ListView) findViewById(R.id.pic_list);
		all_pic_path = picService.getAllPicPath();
		for(String path : all_pic_path) {
			File file = new File(path);
			File[] images = file.listFiles();
			if(images != null) {
				int count = 0;
				SystemImages systemImage = new SystemImages();
				for(File f : images) {
					if(isImageFile(f.getName())) {
						String folder_name = path.substring(path.lastIndexOf("/") + 1, path.length());
						
						systemImage.setFolder_name(folder_name);
						count += 1;
						systemImage.getPath().add(f.getPath());
						systemImage.setCount(count);
					}
				}
				lists.add(systemImage);
			}
			
		}
		

//		for(SystemImages images : lists) {
//			System.out.println(images.getFolder_name());
//			System.out.println(images.getCount());
//			for(String path : images.getPath()) {
//				System.out.println(path);
//			}
//		}
		
		listViewAdaptor();
		listView.setOnItemClickListener(new OnItemClickListenerImpl());
	}
	
	public final class OnItemClickListenerImpl implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
		
				SystemImages systemImages = (SystemImages) parent.getItemAtPosition(position);
				if(type != null && "card".equals(type) || "poster".equals(type)) {
					intent = new Intent(SelectPicActivity.this, CardSelectedPhotoActivity.class);
					intent.putExtra("cardModelId", cardModelId);
					intent.putExtra("type", type);
					intent.putIntegerArrayListExtra("selectedCardModelIds", selectedCardModelIds);
					intent.putIntegerArrayListExtra("cardModelIds", cardModelIds);
				}else {
					intent = new Intent(SelectPicActivity.this, SelectPhotoActivity.class);
				}
				if("book".equals(type)) {
					intent.putIntegerArrayListExtra("bookModelIds", bookModelIds);
					intent.putIntegerArrayListExtra("bookModelFirstPageIds", bookModelFirstPageIds);
				}
				intent.putExtra("no", no);
				intent.putStringArrayListExtra("paths", systemImages.getPath());
				intent.putStringArrayListExtra("selectedPaths", selectedPaths);
				intent.putExtra("type", type);
				SelectPicActivity.this.finish();
				startActivity(intent);
			
		}
	}
	
	 private void listViewAdaptor() {
		 SystemImageAdaptor adaptor = new SystemImageAdaptor(this, lists);
		 listView.setAdapter(adaptor);
	}

	private static boolean isImageFile(String fileName) {  
	        String fileEnd = fileName.substring(fileName.lastIndexOf(".") + 1,  
	                fileName.length());  
	        if (fileEnd.equalsIgnoreCase("jpg")) {  
	            return true;  
	        } else if (fileEnd.equalsIgnoreCase("png")) {  
	            return true;  
	        } else if (fileEnd.equalsIgnoreCase("bmp")) {  
	            return true;  
	        } else {  
	            return false;  
	        }  
	    }  
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		SelectPicActivity.this.finish();
	}
}
