package com.researchmobile.smartquiz.utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import com.researchmobile.smartquiz.ws.ConnectWS;

public class JsonConvert {
	private static String WS = "json.php?";
	JSONObject jsonObject;
	
	private boolean QuizWS(String usuario, String clave, Context context) {
		
		String finalURL = WS + "&action=action";
		
		jsonObject = ConnectWS.getJsonObject(finalURL);
		try{
			if (jsonObject.has("error")){
				
				String error = jsonObject.getString("error");
				
				if (error.equalsIgnoreCase("0")){
					return false;
				}else if (jsonObject.has("vendedor")){
					JSONObject jsonObjectVendedor = jsonObject.getJSONObject("vendedor");
				}
				return true;
			}
		} catch (Exception exception){
			return false;
	     }
		return false;
	}
	
	public String AnswerJson (){
		//Final String
		String result = null;
		//Final JSON
		JSONObject allDataJsonObject = new JSONObject();
        //Data Quiz JSON
        JSONObject dataQuizJsonObject = new JSONObject();
        //Array Answer JSON
        JSONArray answerJsonArray = new JSONArray();
        
        //Test Fecha
        String fecha = "24/06/2012";
        try {
        	dataQuizJsonObject.put("id_quiz", "1");
        	dataQuizJsonObject.put("hora_inicial", "10:00");
        	dataQuizJsonObject.put("hora_final", "11:00");
        	dataQuizJsonObject.put("latitud", "1.2334543");
        	dataQuizJsonObject.put("longitud", "-1.3424345");
        	dataQuizJsonObject.put("fecha", fecha);
        	
        	int answerCount = 4;
	        for (int i = 0; i < answerCount; i++){
	        	
	        		JSONObject answerTempJsonObject= new JSONObject();
		            answerTempJsonObject.put("id_indicador", i);
		            answerTempJsonObject.put("valor", "3");
		            answerJsonArray.put(answerTempJsonObject);
	        }
	        allDataJsonObject.put("quiz", dataQuizJsonObject);
	        allDataJsonObject.put("respuesta", answerJsonArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        result = allDataJsonObject.toString();
		return result;
	}
}
