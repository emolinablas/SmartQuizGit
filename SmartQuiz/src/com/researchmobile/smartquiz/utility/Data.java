package com.researchmobile.smartquiz.utility;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.researchmobile.smartquiz.entity.Indicator;
import com.researchmobile.smartquiz.entity.Test;

public class Data {
	private String off_image = "rm_off";
	private String green_image = "rm_green";
	private String yellow_image = "rm_yellow";
	private String red_image = "rm_red";
	private String photo = "foto_rm";
	
	public Test[] dataTest (){
		Test[] test = new Test[5];
		//for (int i = 0; i < test.length; i++){
			Test testTemp = new Test();
			testTemp.setIdTest(String.valueOf(1));
			testTemp.setTest("1 ILUMINACION ADECUADA");
			testTemp.setState(0);
			testTemp.setUrlPhoto(null);
			testTemp.setUseCamera(false);
			test[0] = testTemp;
			
			Test testTemp1 = new Test();
			testTemp1.setIdTest(String.valueOf(2));
			testTemp1.setTest("2 CORRIENTE ESTABLE");
			testTemp1.setState(0);
			testTemp1.setUrlPhoto(null);
			testTemp1.setUseCamera(false);
			test[1] = testTemp1;
			
			Test testTemp2 = new Test();
			testTemp2.setIdTest(String.valueOf(3));
			testTemp2.setTest("3 AMBIENTE AGRADABLE");
			testTemp2.setState(0);
			testTemp2.setUrlPhoto(null);
			testTemp2.setUseCamera(false);
			test[2] = testTemp2;
			
			Test testTemp3 = new Test();
			testTemp3.setIdTest(String.valueOf(4));
			testTemp3.setTest("4 SUFICIENTE AMPLITUD");
			testTemp3.setState(0);
			testTemp3.setUrlPhoto(null);
			testTemp3.setUseCamera(false);
			test[3] = testTemp3;
			
			Test testTemp4 = new Test();
			testTemp4.setIdTest(String.valueOf(5));
			testTemp4.setTest("5 LIMPIEZA ADECUADA");
			testTemp4.setState(0);
			testTemp4.setUrlPhoto(null);
			testTemp4.setUseCamera(false);
			test[4] = testTemp4;
			
		//}
		return test;
	}
	
	public Indicator[] dataIndicatorState (String idIndicator, Indicator[] indicator){
		
		for (int i = 0; i < indicator.length; i++){
			if (indicator[i].getIdIndicator().equalsIgnoreCase(idIndicator)){
				int state = indicator[i].getState();
				//state--;
				if (state <= 1){
					state = 3;
					indicator[i].setState(state);
				}else{
					state--;
					indicator[i].setState(state);
				}
				System.out.println(indicator[i].getState());
			}
		}
		return indicator;
	}
	
	public Test[] dataTestState (String idTest, Test[] test){
		
		for (int i = 0; i < test.length; i++){
			if (test[i].getIdTest().equalsIgnoreCase(idTest)){
				int state = test[i].getState();
				state++;
				if (state < 4){
					test[i].setState(state);
				}else{
					state = 1;
					test[i].setState(state);
				}
			}
		}
		return test;
	}
	
	public ArrayList<HashMap<String, Object>> list (Test[] test, Context context){

		//Define Image State
		int stateOff = context.getResources().getIdentifier(off_image, "drawable", context.getPackageName()); 
		int stateNice = context.getResources().getIdentifier(green_image, "drawable", context.getPackageName());
		int stateGood = context.getResources().getIdentifier(yellow_image, "drawable", context.getPackageName());
		int stateAlert = context.getResources().getIdentifier(red_image, "drawable", context.getPackageName());
		int photoCapture = context.getResources().getIdentifier(photo, "drawable", context.getPackageName());
		
		ArrayList<HashMap<String, Object>> myList = new ArrayList<HashMap<String, Object>>();
		
		for (int i = 0; i < test.length; i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("idTest", test[i].getIdTest());
			
			//Selected photo quiz
			if (test[i].isUseCamera()){
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				Bitmap bm = BitmapFactory.decodeFile("sdcard/" + test[i].getUrlPhoto(), options);
				Drawable drawable = new BitmapDrawable(context.getResources(), bm);
				//context.getResources().

				map.put("photo", test[i].getUrlPhoto());
			}else{
				map.put("photo", photoCapture);
			}
			
			//Selected state image
			if (test[i].getState() == 0){
				map.put("image", stateOff);
			} else if (test[i].getState() == 1){
				map.put("image", stateNice);
			} else if (test[i].getState() == 2){
				map.put("image", stateGood);
			} else {
				map.put("image", stateAlert);
			}
			
		    map.put("test",test[i].getTest());
		    map.put("pathPhoto", test[i].getUrlPhoto());
		    myList.add(map);
		}
		return myList;
	}

	public Indicator[] dataTestPhoto(String idActive, Indicator[] indicator, String path) {
		for (int i = 0; i < indicator.length; i++){
			if (indicator[i].getIdIndicator().equalsIgnoreCase(idActive)){
				indicator[i].setUseCamera(true);
				indicator[i].setUrlPhoto("/sdcard" + path);
			}
		}
		return indicator;
	}
}
