package com.lanrui.andiy.test;

import java.util.ArrayList;
import java.util.List;

import com.lanrui.andiy.R;
import com.lanrui.andiy.model.Model;
import com.lanrui.andiy.service.ModelService;
import com.lanrui.andiy.util.DBOpenHelper;


import android.graphics.LightingColorFilter;
import android.test.AndroidTestCase;
import android.util.Log;

public class SQLiteTest extends AndroidTestCase {


	
	public void testCreate() {
		DBOpenHelper openHelper = new DBOpenHelper(this.getContext());
		openHelper.getWritableDatabase();
	}
	
	public void getCardModelId() {
		ModelService modelService = new ModelService(this.getContext());
		ArrayList<Integer> nos = new ArrayList<Integer>();
		nos.add(1);
		List<List<Model>> modelss = modelService.getModelsByNos(nos);
		List<Model> models = modelss.get(0);
		for(Model m : models) {
			Log.i("a", "m.cardModelId = " + m.getCardmodelid());
			Log.i("a", "m.path = " + m.getPath());
		}
	}
	
	public void testGetCount() {
		ModelService modelService = new ModelService(this.getContext());
		int count = (int)modelService.getCount();
		Log.i("a", "count = " + count + "");
	}
	
	public void testGetNo() {
		ModelService modelService = new ModelService(this.getContext());
		int no = modelService.getNo(); 
		Log.i("a", "no = " + no + "");
	}
	
	public void testAddModel() {
		ModelService modelService = new ModelService(this.getContext());
		int no = modelService.getNo(); 
		Model model = new Model();
		model.setType("pic");
		model.setNo(no);
		model.setPath("/mnt/photo/album");
		modelService.add(model);
	}
	
	
	
	public void testGetModelsByNos() {
		ModelService modelService = new ModelService(this.getContext());
		List<Integer> nos = modelService.getNoGroupByNo();
		List<List<Model>> modelss = modelService.getModelsByNos(nos);
		for(List<Model> models : modelss) {
			for(Model m : models) {
				Log.i("a", "id = " + m.getId() );
			}
			Log.i("a", "----------------------------------------");
		}
	}
	
	public void testBatchAddModel() {
		ModelService modelService = new ModelService(this.getContext());
		int no = modelService.getNo();
		Model model = null;
		List<Model> models = new ArrayList<Model>();
		for(int i = 0; i < 3; i++) {
			model = new Model();
			model.setPath("path_" + i);
			model.setType("photo");
			model.setNo(no);
			model.setCardmodelid(R.drawable.cardmodel_xk1);
			models.add(model);
		}
		
		modelService.batchModels(models);
	}
	
	public void testGetNoGroupBy() {
		ModelService modelService = new ModelService(this.getContext());
		List<Integer> nos = modelService.getNoGroupByNo();
		for(Integer no : nos) {
			Log.i("a", "no : " + no);
		}
	}
	
	public void testDeleteModelByNo() {
		ModelService modelService = new ModelService(this.getContext());
		modelService.deleteModelByNo(4);
	}
}

























