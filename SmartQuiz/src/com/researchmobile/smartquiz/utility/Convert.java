package com.researchmobile.smartquiz.utility;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.researchmobile.smartquiz.entity.Answer;
import com.researchmobile.smartquiz.entity.Area;
import com.researchmobile.smartquiz.entity.Business;
import com.researchmobile.smartquiz.entity.Indicator;
import com.researchmobile.smartquiz.entity.Network;
import com.researchmobile.smartquiz.entity.Quiz;
import com.researchmobile.smartquiz.entity.Sector;
import com.researchmobile.smartquiz.entity.Store;
import com.researchmobile.smartquiz.entity.SubArea;
import com.researchmobile.smartquiz.entity.Supervision;

public class Convert {
	private String off_image = "rm_off";
	private String green_image = "rm_green";
	private String yellow_image = "rm_yellow";
	private String red_image = "rm_red";
	private String photo = "foto_rm";
	private String check_photo = "check_photo";
	
	public String answerString (String subArea){
		//Answer answerTemp = answer;
		String result = "supervisionSubArea=" + subArea;
		return result;
	}
	
	public String indicatorJsonString (Answer answer){
		Answer answerTemp = answer;
		
		//Informacion general del quiz
		//Array de respuestas
        JSONArray indicator = new JSONArray();
        
        try {
        	
	        for (int i = 0; i < answer.getIndicator().length; i++){
	        		JSONObject indicatorDetail= new JSONObject();
		            indicatorDetail.put("id", String.valueOf(answer.getIndicator()[i].getIdIndicator()));
		            indicatorDetail.put("calificacion", String.valueOf(answer.getIndicator()[i].getState()));
		            
		            indicator.put(indicatorDetail);
	        }
	                
	        //encabezado.put(dataEncabezado);
	               
	        
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String result = indicator.toString();
		return result;
	}
	// NUEVOS CONVERSORES DE SPINER CON DATOS A DESCRIPCION ------------------
	
	public String[] dataBusinessSpinner(Business[] business){
		int tamano = business.length;
		String[] businessString = new String[tamano];
		
		for (int i = 0; i<tamano; i++){
			businessString[i] = business[i].getDescription();
		}
		return businessString;
	}
	
	public String[] dataNetworkSpinner(Business[] business, Network[] network, String businessSelected){
		String idBusiness = "";
		for (int i = 0; i < business.length; i++){
			if (business[i].getDescription().equalsIgnoreCase(businessSelected)){
				idBusiness = business[i].getIdBusiness();
				System.out.println(idBusiness);
			}
		}
		
		int tamano = network.length;
		String[] networkStringTemp = new String[tamano];
		
		int contador = 0;
		for (int i = 0; i<tamano; i++){
			
				if (network[i].getIdBusiness().equalsIgnoreCase(idBusiness)){
					
					networkStringTemp[contador] = network[i].getDescription();
					contador++;
				}
		}
		
		String[] networkString = new String[contador];
		
		for (int i = 0; i<contador; i++){
			System.out.println("llego");
			networkString[i] = networkStringTemp[i];
		}
		
		
		return networkString;
	}
	
	
	public String[] dataSectorSpinner(Sector[] sector){
		int tamano = sector.length;
		String[] sectorString = new String[tamano];
		
		for (int i = 0; i<tamano; i++){
			sectorString[i] = sector[i].getDescription();
		}
		return sectorString;
	}
	
	public String[] dataStoreSpinner(Network[] network, Sector[] sector, Store[] store,  String networkSelected, String sectorSelected){
		
		// Busco el Id de cadena
		String idNetwork = "";
		for (int i = 0; i < network.length; i++){
			if (network[i].getDescription().equalsIgnoreCase(networkSelected)){
				idNetwork = network[i].getIdNetwork();
				System.out.println(idNetwork);
			}
		}
		
		// Busco el Id de sector
		String idSector = "";
		for (int i = 0; i < sector.length; i++){
			if (sector[i].getDescription().equalsIgnoreCase(sectorSelected)){
				idSector = sector[i].getIdSector();
				System.out.println(idSector);
			}
		}
		
		int tamano = store.length;
		String[] storeStringTemp = new String[tamano];
		
		int contador = 0;
		for (int i = 0; i<tamano; i++){
			
				if (store[i].getIdNetwork().equalsIgnoreCase(idNetwork) && store[i].getIdSector().equalsIgnoreCase(idSector)){
					
					storeStringTemp[contador] = store[i].getDescription();
					contador++;
				}
		}
		
		String[] storeString = new String[contador];
		
		for (int i = 0; i<contador; i++){
			System.out.println("llego");
			storeString[i] = storeStringTemp[i];
		}
		
		
		return storeString;
	

	}
	
	
	public String[] dataSupervisionSpinner(Store[] store, Supervision[] supervision, String storeSelected){
		String idStore = "";
		System.out.println("ESTA ES LA TIENDA QUE ENCONTRO");
		System.out.println(storeSelected);
		for (int i = 0; i < store.length; i++){
			if (store[i].getDescription().equalsIgnoreCase(storeSelected)){
				idStore = store[i].getIdStore();
				System.out.println(idStore);
				System.out.println("SE ENCONTRO LA TIENDA");
			}
			
		}
		
		int tamano = supervision.length;
		String[] supervisionStringTemp = new String[tamano];
		
		int contador = 0;
		for (int i = 0; i<tamano; i++){
			
				if (supervision[i].getIdStore().equalsIgnoreCase(idStore)){
					
					supervisionStringTemp[contador] = supervision[i].getDescription();
					contador++;
					System.out.println("SI ENCONTRO LA SUPERVISION");
				}
		}
		
		String[] supervisionString = new String[contador];
		
		for (int i = 0; i<contador; i++){
			System.out.println("llego a la SUPERVISION");
			supervisionString[i] = supervisionStringTemp[i];
		}
	
		
		if (contador == 0){
			contador++;
			supervisionString = new String[contador];
			supervisionString[0] = "";
		}else{
			supervisionString = new String[contador];
			for (int i = 0; i<contador; i++){
				System.out.println("llego");
				supervisionString[i] = supervisionStringTemp[i];
			}
		}
		return supervisionString;
	}
	
	
	
	
	public ArrayList<HashMap<String, Object>> indicatorList (Indicator[] indicator, Context context){

		//Define Image State
		int stateOff = context.getResources().getIdentifier(off_image, "drawable", context.getPackageName()); 
		int stateNice = context.getResources().getIdentifier(green_image, "drawable", context.getPackageName());
		int stateGood = context.getResources().getIdentifier(yellow_image, "drawable", context.getPackageName());
		int stateAlert = context.getResources().getIdentifier(red_image, "drawable", context.getPackageName());
		int photoCapture = context.getResources().getIdentifier(photo, "drawable", context.getPackageName());
		
		ArrayList<HashMap<String, Object>> myList = new ArrayList<HashMap<String, Object>>();
		
		for (int i = 0; i < indicator.length; i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("idQuiz", indicator[i].getIdIndicator());
			
			//Selected photo quiz
			if (indicator[i].isUseCamera()){
				map.put("photo", indicator[i].getUrlPhoto());
			}else{
				map.put("photo", photoCapture);
			}
			
			//Selected state image
			if (indicator[i].getState() == 0){
				map.put("image", stateOff);
			} else if (indicator[i].getState() == 1){
				map.put("image", stateNice);
			} else if (indicator[i].getState() == 2){
				map.put("image", stateGood);
			} else {
				map.put("image", stateAlert);
			}
			
		    map.put("quiz",indicator[i].getDescription());
		    map.put("pathPhoto", indicator[i].getUrlPhoto());
		    myList.add(map);
		}
		return myList;
	}
	
	public ArrayList<HashMap<String, Object>> indicatorListTest (Indicator[] indicator, Context context){

		//Define Image State
		int stateOff = context.getResources().getIdentifier(off_image, "drawable", context.getPackageName()); 
		int stateNice = context.getResources().getIdentifier(green_image, "drawable", context.getPackageName());
		int stateGood = context.getResources().getIdentifier(yellow_image, "drawable", context.getPackageName());
		int stateAlert = context.getResources().getIdentifier(red_image, "drawable", context.getPackageName());
		int photoCapture = context.getResources().getIdentifier(photo, "drawable", context.getPackageName());
		int checkPhoto = context.getResources().getIdentifier(check_photo, "drawable", context.getPackageName());
		
		ArrayList<HashMap<String, Object>> myList = new ArrayList<HashMap<String, Object>>();
		
		String primQuiz = indicator[0].getIdQuiz();
		for (int i = 0; i < indicator.length; i++){
			String idquiz = indicator[i].getIdQuiz();
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			System.out.println("LLENANDO LISTA " + primQuiz + " " + indicator[i].getQuizDescription());
			
			if(primQuiz.equalsIgnoreCase(idquiz) && i != 0)
			{
				map.put("quizPertenece", "");
				primQuiz = indicator[i].getIdQuiz();
			
			}
			else{
				map.put("quizPertenece", indicator[i].getQuizDescription());
				primQuiz = indicator[i].getIdQuiz();
			}
			map.put("idQuiz", indicator[i].getIdIndicator());
			
			//Selected photo quiz
			if (indicator[i].isUseCamera()){
				map.put("photo", checkPhoto);
			}else{
				map.put("photo", photoCapture);
			}
			
			//Selected state image
			if (indicator[i].getState() == 0){
				map.put("image", stateOff);
			} else if (indicator[i].getState() == 1){
				map.put("image", stateAlert);
			} else if (indicator[i].getState() == 2){
				map.put("image", stateGood);
			} else {
				map.put("image", stateNice);
			}
			
		    map.put("quiz",indicator[i].getPregunta());
		    map.put("pathPhoto", indicator[i].getUrlPhoto());
		    myList.add(map);
		}
		return myList;
	}
	
/*	public String[] dataStoreSpinner(Red[] red, Store[] store, String networkSelected){
		String idNetwork = "";
		for (int i = 0; i < red.length; i++){
			if (red[i].getDescription().equalsIgnoreCase(networkSelected)){
				idNetwork = red[i].getIdRed();
				System.out.println(idNetwork);
			}
		}
		
		int tamano = store.length;
		String[] storeStringTemp = new String[tamano];
		
		int contador = 0;
		for (int i = 0; i<tamano; i++){
			
				if (store[i].getIdRed().equalsIgnoreCase(idNetwork)){
					
					storeStringTemp[contador] = store[i].getDescription();
					contador++;
				}
		}
		
		String[] storeString = new String[contador];
		
		for (int i = 0; i<contador; i++){
			System.out.println("llego");
			storeString[i] = storeStringTemp[i];
		}
		
		
		return storeString;
	}*/
	
	public String[] dataAreaSpinner(Area[] area){
		int tamano = area.length;
		String[] areaString = new String[tamano];
		
		for (int i = 0; i<tamano; i++){
			areaString[i] = area[i].getDescription();
		}
		return areaString;
	}
	
	public String[] dataSubAreaSpinner(Area[] area, SubArea[] subArea, String areaSelected){
		String idArea = "";
		for (int i = 0; i < area.length; i++){
			if (area[i].getDescription().equalsIgnoreCase(areaSelected)){
				idArea = area[i].getIdArea();
				System.out.println(idArea);
			}
		}
		
		int tamano = subArea.length;
		String[] subAreaStringTemp = new String[tamano];
		
		int contador = 0;
		for (int i = 0; i<tamano; i++){
			
				if (subArea[i].getIdArea().equalsIgnoreCase(idArea)){
					subAreaStringTemp[contador] = subArea[i].getDescription();
					contador++;
				}
		}
		
		String[] subAreaString = new String[contador];
		
		for (int i = 0; i<contador; i++){
			System.out.println("llego");
			subAreaString[i] = subAreaStringTemp[i];
		}
		
		
		return subAreaString;
	}
	
	public String[] dataQuizSpinner(SubArea[] subAreas, Quiz[] quiz, String subAreaSelected){
		String idSubArea = "";
		for (int i = 0; i < subAreas.length; i++){
			if (subAreas[i].getDescription().equalsIgnoreCase(subAreaSelected)){
				idSubArea = subAreas[i].getIdSubArea();
				System.out.println(idSubArea);
			}
		}
		
		int tamano = quiz.length;
		
		String[] quizStringTemp = new String[tamano];
		
		int contador = 0;
		for (int i = 0; i<tamano; i++){
			System.out.println(quiz[i].getIdSubArea());
			
				if (quiz[i].getIdSubArea().equalsIgnoreCase(idSubArea)){
					quizStringTemp[contador] = quiz[i].getDescription();
					contador++;
				}
		}
		
		String[] quizString = null;
		if (contador == 0){
			contador++;
			quizString = new String[contador];
			quizString[0] = "";
		}else{
			quizString = new String[contador];
			for (int i = 0; i<contador; i++){
				System.out.println("llego");
				quizString[i] = quizStringTemp[i];
			}
		}
		return quizString;
	}
}
