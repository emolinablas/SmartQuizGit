package com.researchmobile.smartquiz.ws;

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
import com.researchmobile.smartquiz.entity.Result;
import com.researchmobile.smartquiz.entity.Sector;
import com.researchmobile.smartquiz.entity.SendPhoto;
import com.researchmobile.smartquiz.entity.Store;
import com.researchmobile.smartquiz.entity.SubArea;
import com.researchmobile.smartquiz.entity.Supervision;

public class RequestWS {
	private static String WS_LOGIN = "login?";
	private static String WS_DATA = "informacionGeneral?";
	private static String WS_INDICATOR = "listadoQuiz?";
	private static String WS_RESPUESTAS = "registrarRespuestas?";
	private static String WS_AREA= "listadoAreas?";	
    private static String WS_SUBAREA="listadoSubAreas?";
	private static String TIPO_USUARIO = "3";
	//private static String JSON_RED = "cadenas";
	//private static String JSON_STORE = "tiendas";
	private static String JSON_AREA = "areas";
	private static String JSON_SUBAREA = "subAreas";
	private static String JSON_QUIZ = "quizes";
	
	private static String JSON_BUSINESS = "empresas";
	private static String JSON_NETWORK = "cadenas";
	private static String JSON_SECTOR = "sectores";
	private static String JSON_STORE = "tiendas";
	private static String JSON_SUPERVISION = "supervisiones";

	
	
	private static String JSON_NAME = "nombre";
	private static String JSON_USER = "usuario";
	private static String JSON_RESULT = "resultado";
	private static String JSON_MESSAGE = "mensaje";
	
	private static String JSON_CUESTIONS = "preguntas";
	
	private Result result;
	private Answer[] answer;
	private SubArea subArea;
	private Area area;
	
	
	JSONObject jsonObject;
//JSONObject jsonObjectQuiz;
	//Verificar Login del WebService
	@SuppressWarnings("unused")
	public Result login(String usuario, String clave, Context context) {
		setResult(new Result());
		String finalURL = WS_LOGIN + "nick=" + usuario + "&password=" + clave + "&tipoUsuario=" + TIPO_USUARIO;
		
		jsonObject = ConnectWS.getJsonObject(finalURL);
		
		try{
			if (jsonObject.has(JSON_RESULT)){
				getResult().setResult(jsonObject.getBoolean(JSON_RESULT));
				getResult().setMessage(jsonObject.getString(JSON_MESSAGE));
				
				if(getResult().isResult())
				{
					getResult().setName(jsonObject.getString(JSON_NAME));
					if (jsonObject.has(JSON_USER))
					{
						getResult().setUser(jsonObject.getString(JSON_USER));
					}
					
				}
				
			}
			}catch (Exception exception){
				
			}
		return getResult();
	}
	
	//Obtener listado de preguntas
public SubArea indicatorList(String idSubArea) {
		
		setSubArea(new SubArea());
		String finalURL = WS_INDICATOR + "supervisionSubArea=" + idSubArea;
		
		jsonObject = ConnectWS.getJsonObjectQuizes(finalURL);
		
		try{
		
				//System.out.println("ENTRO PORQUE SI VIENEN LOS QUIZES");
			    getSubArea().setResult(Boolean.parseBoolean(jsonObject.getString("resultado")));
				getSubArea().setMessage(jsonObject.getString("mensaje"));
				
				System.out.println(jsonObject.getString("mensaje"));
				if(getSubArea().isResult()){
					
					if(jsonObject.has("quizes")){
						
						JSONArray jsonArrayQuiz = jsonObject.getJSONArray("quizes");
						int tamano = jsonArrayQuiz.length();
						System.out.println("cantidad de quizes");
						System.out.println(tamano);
						
						Answer respuestas[] = new Answer[tamano];
						
						for(int j=0; j<tamano;j++)
						{
							Answer answerTemp = new Answer();
							System.out.println("SI ENTRA AL FOR");
							JSONObject jsonObjectQuiz = jsonArrayQuiz.getJSONObject(j);
							
							System.out.println(jsonObjectQuiz.getString("nombre"));
						    answerTemp.setId_quiz(jsonObjectQuiz.getString("quiz"));
							System.out.println(jsonObjectQuiz.getString("descripcion"));
							System.out.println(jsonObjectQuiz.getString("quiz"));
							answerTemp.setResult(true);
							answerTemp.setMessage("Se reciobio el quiz");
							answerTemp.setDescription(jsonObjectQuiz.getString("descripcion"));
							answerTemp.setName(jsonObjectQuiz.getString("nombre"));
							System.out.println(jsonObjectQuiz.getString("nombre"));
							
							
							if(jsonObjectQuiz.has("preguntas"))
							{   			
							System.out.println("ENCONTRO LAS PREGUNTAS");
						
								if(answerTemp.isResult()){
									if (jsonObjectQuiz.has("preguntas")){
										JSONArray jsonArrayIndicator = jsonObjectQuiz.getJSONArray("preguntas");
										int tamanos = jsonArrayIndicator.length();
										System.out.println("AHORA SI ENTRO CON LAS PREGUNTAS");
										Indicator indicator[] = new Indicator[tamanos];
										for (int i = 0; i < tamanos; i++){
											Indicator indicatorTemp = new Indicator();
											JSONObject jsonObjectIndicator = jsonArrayIndicator.getJSONObject(i);
											indicatorTemp.setPregunta(jsonObjectIndicator.getString("pregunta"));
										//	System.out.println("preguntas de JSON " +jsonObjectIndicator.getString("pregunta"));
											indicatorTemp.setIdIndicator(jsonObjectIndicator.getString("idPregunta"));
										//	indicatorTemp.setDescription(jsonObjectIndicator.getString("descripcion"));
											indicatorTemp.setDescription("");
											indicatorTemp.setValue("0");
											indicatorTemp.setState(0);
											indicatorTemp.setUseCamera(false);
											indicatorTemp.setIdQuiz(jsonObjectQuiz.getString("quiz"));
											indicatorTemp.setQuizDescription(jsonObjectQuiz.getString("descripcion"));
											indicator[i] = indicatorTemp;
											System.out.println(indicator[i].getPregunta());
										}
										
										answerTemp.setIndicator(indicator);
								/*		System.out.println("IDS DE INDICADORES ");
										System.out.println(answerTemp.getIndicator()[0].getIdIndicator());
										System.out.println(answerTemp.getIndicator()[1].getIdIndicator());
										System.out.println(answerTemp.getIndicator()[2].getIdIndicator());*/
										
									}
									 
								}
								
							}
							respuestas[j] = answerTemp;
							
								
							
							
						}
						getSubArea().setRespuesta(respuestas);
				/*		System.out.println("ESTAS SON LAS PREGUNTAS QUE VOY A MANDAR");
						System.out.println(getSubArea().getRespuesta()[0].getIndicator()[0].getPregunta());
						System.out.println(getSubArea().getRespuesta()[0].getIndicator()[1].getPregunta());
						System.out.println(getSubArea().getRespuesta()[0].getIndicator()[2].getPregunta());
						System.out.println(getSubArea().getRespuesta()[1].getIndicator()[0].getPregunta());
						System.out.println(getSubArea().getRespuesta()[1].getIndicator()[1].getPregunta());
						System.out.println(getSubArea().getRespuesta()[1].getIndicator()[2].getPregunta());
						
						System.out.println(getSubArea().getRespuesta()[0].getIndicator()[0].getIdIndicator());
						System.out.println(getSubArea().getRespuesta()[0].getIndicator()[1].getIdIndicator());
						System.out.println(getSubArea().getRespuesta()[0].getIndicator()[2].getIdIndicator());
						System.out.println(getSubArea().getRespuesta()[1].getIndicator()[0].getIdIndicator());
						System.out.println(getSubArea().getRespuesta()[1].getIndicator()[1].getIdIndicator());
						System.out.println(getSubArea().getRespuesta()[1].getIndicator()[2].getIdIndicator());*/
						
					}
				}
				
				
			
			
			
			}catch (Exception exception){
				System.out.println("OCURRIO UN ERROR AL CARGAR EL JSON A EL AREA");
			}
		return getSubArea();
	}
	
	public boolean sendData(String dataQuiz, Indicator[] indicator) {
		
		String finalURL = WS_RESPUESTAS + dataQuiz;
		boolean result = false;
		
		jsonObject = ConnectWS.enviaRespuestas(finalURL);
		if (jsonObject.has("resultado")){
			
			try {
				String resultado = jsonObject.getString("resultado");
				if (resultado.equalsIgnoreCase("true")){
					
					if (jsonObject.has("resultados")){
						JSONArray idListJson = jsonObject.getJSONArray("resultados");
						SendPhoto[] sendPhoto = new SendPhoto[idListJson.length()];
						
						for (int i = 0; i < idListJson.length(); i++){
							SendPhoto sendPhotoTemp = new SendPhoto();
							JSONObject jsonObjectList = idListJson.getJSONObject(i);
							sendPhotoTemp.setId(jsonObjectList.getString("id"));
							sendPhotoTemp.setPregunta((jsonObjectList.getString("pregunta")));
							sendPhoto[i] = sendPhotoTemp;
						}
						
						for (int i = 0; i < sendPhoto.length; i++){
							for (int j = 0; j < indicator.length; j++){
								if (sendPhoto[i].getPregunta().equalsIgnoreCase(indicator[j].getIdIndicator())){
									sendPhoto[i].setUrlPhoto(indicator[j].getUrlPhoto());
								}
							}
						}
						
						
						//enviar imagenes
						int tamano = sendPhoto.length;
						for (int i = 0; i < tamano; i++){
							if (sendPhoto[i].getUrlPhoto() != null){
								jsonObject = ConnectWS.enviaImagen(sendPhoto[i].getUrlPhoto(), sendPhoto[i].getId());
								if (jsonObject.has("resultado")){
									result = Boolean.parseBoolean(jsonObject.getString("resultado"));
									String mensaje = jsonObject.getString("mensaje");
									if (result){
										System.out.println(mensaje);
									}else{
										System.out.println(mensaje);
									}
								
							}
						}
						
					}
					}
					
					
					
					
					
					
					return true;
				}else{
					return false;
				}
				
			} catch (JSONException e) {
				System.out.println("error 12");
				e.printStackTrace();
			}
		}else{
			
			return false;
		}
		return false;
		
		
	}
	
	public Result areaData(String supervisionSelected)
	{
		setResult(new Result());
		String finalURL = WS_AREA + "supervision=" + supervisionSelected;
		
		jsonObject = ConnectWS.enviaSupervision(finalURL);
		
		try{
			if (jsonObject.has(JSON_AREA)) {
				JSONArray jsonArrayArea = jsonObject.getJSONArray(JSON_AREA);
				int tamano = jsonArrayArea.length();

				Area area[] = new Area[tamano];
				for (int i = 0; i < jsonArrayArea.length(); i++) {
					Area areaTemp = new Area();
					JSONObject jsonObjectArea = jsonArrayArea.getJSONObject(i);
					areaTemp.setIdArea(jsonObjectArea
							.getString("supervisionArea"));
					areaTemp.setDescription(jsonObjectArea.getString("nombre"));
					areaTemp.setEstado(jsonObjectArea.getString("estado"));
					area[i] = areaTemp;
				}
				getResult().setArea(area);
				System.out.println("AREA GUARDADA");
			}
			   
			
		}catch(Exception exception){
			System.out.println("NO GUARDA AREAS");
		
		}
		return getResult();
				
	}
	
	public Result subAreaData(String codigo) {
		// TODO Auto-generated method stub
setResult(new Result());
		
		
		String finalURL = WS_SUBAREA + "superArea=" + codigo;
		
		jsonObject = ConnectWS.enviaArea(finalURL);
		
		try{	
				if (jsonObject.has(JSON_SUBAREA)) {
				
				JSONArray jsonArraySubArea = jsonObject.getJSONArray(JSON_SUBAREA); 
				int tamano = jsonArraySubArea.length();
				
				SubArea subArea[] = new SubArea[tamano];
				
				for (int i = 0; i < jsonArraySubArea.length(); i++){
					SubArea subAreaTemp = new SubArea();
					JSONObject jsonObjectSubArea = jsonArraySubArea.getJSONObject(i);
					subAreaTemp.setIdSubArea(jsonObjectSubArea.getString("supervisionSubArea"));
					subAreaTemp.setDescription(jsonObjectSubArea.getString("nombre"));
					subAreaTemp.setIdArea(jsonObjectSubArea.getString("supervisionArea"));
					subAreaTemp.setEstado(jsonObjectSubArea.getString("estado"));
					
					subArea[i] = subAreaTemp;
					
				}
				getResult().setSubArea(subArea);
				System.out.println("SUBAREA[] GUARDADA");

			}
			   
			
		}catch(Exception exception){
			System.out.println("NO GUARDA AREAS");
			return null;
		
		}
		return getResult();
				
	}
	
	//Obtener datos del WebService
	public Result allData(String user){
		setResult(new Result());
		
		String finalURL = WS_DATA + "supervisor=" + user;
		
		jsonObject = ConnectWS.getJsonObject(finalURL);
		
		try{
//Obtener datos de Empresa
			if (jsonObject.has(JSON_BUSINESS)){
				
				JSONArray jsonArrayBusiness = jsonObject.getJSONArray(JSON_BUSINESS); 
				int tamano = jsonArrayBusiness.length();
				
				 Business  business[] = new Business[tamano];
				
				for (int i = 0; i < jsonArrayBusiness.length(); i++){
					Business businessTemp = new Business();
					JSONObject jsonObjectBusiness = jsonArrayBusiness.getJSONObject(i);
					businessTemp.setIdBusiness(jsonObjectBusiness.getString("id"));
					businessTemp.setDescription(jsonObjectBusiness.getString("nombre"));
					
					business[i] = businessTemp;
				}
				getResult().setBussines(business);
				System.out.println("EMPRESA[] GUARDADA");
			}
			
//Obtener datos de Cadena
			if (jsonObject.has(JSON_NETWORK)) {
				JSONArray jsonArrayNetwork = jsonObject.getJSONArray(JSON_NETWORK); 
				int tamano = jsonArrayNetwork.length();
				
				Network network[] = new Network[tamano];
				
				for (int i = 0; i < jsonArrayNetwork.length(); i++){
					Network networkTemp = new Network();
					JSONObject jsonObjectNetwork = jsonArrayNetwork.getJSONObject(i);
					networkTemp.setIdNetwork(jsonObjectNetwork.getString("id"));
					networkTemp.setDescription(jsonObjectNetwork.getString("nombre"));
					networkTemp.setIdBusiness(jsonObjectNetwork.getString("empresa"));
					
					network[i] = networkTemp;
				}
				getResult().setNetwork(network);
				System.out.println("CADENA GUARDADA");

			}
			
			//Obtener datos de Sector
			if (jsonObject.has(JSON_SECTOR)){
				
				JSONArray jsonArraySector = jsonObject.getJSONArray(JSON_SECTOR); 
				int tamano = jsonArraySector.length();
				
				 Sector  sector[] = new Sector[tamano];
				
				for (int i = 0; i < jsonArraySector.length(); i++){
					Sector sectorTemp = new Sector();
					JSONObject jsonObjectSector = jsonArraySector.getJSONObject(i);
					sectorTemp.setIdSector(jsonObjectSector.getString("id"));
					sectorTemp.setDescription(jsonObjectSector.getString("nombre"));
					
					sector[i] = sectorTemp;
				}
				getResult().setSector(sector);
				System.out.println("SECTORES[] GUARDADA");
			}
			
//Obtener datos de Tienda
			if (jsonObject.has(JSON_STORE)) {
				JSONArray jsonArrayStore = jsonObject.getJSONArray(JSON_STORE); 
				int tamano = jsonArrayStore.length();
				
				Store store[] = new Store[tamano];
				
				for (int i = 0; i < jsonArrayStore.length(); i++){
					Store storeTemp = new Store();
					JSONObject jsonObjectStore = jsonArrayStore.getJSONObject(i);
					storeTemp.setIdStore(jsonObjectStore.getString("id"));
					storeTemp.setDescription(jsonObjectStore.getString("nombre"));
					storeTemp.setIdNetwork(jsonObjectStore.getString("cadena"));
					storeTemp.setIdSector(jsonObjectStore.getString("sector"));
					
					store[i] = storeTemp;
				}
				getResult().setStore(store);
				System.out.println("TIENDA GUARDADA");

			}
			
//Obtener datos de Supervision
			if (jsonObject.has(JSON_SUPERVISION)){
				
				JSONArray jsonArraySupervision = jsonObject.getJSONArray(JSON_SUPERVISION); 
				int tamano = jsonArraySupervision.length();
				
				 Supervision  supervision[] = new Supervision[tamano];
				
				for (int i = 0; i < jsonArraySupervision.length(); i++){
					Supervision supervisionTemp = new Supervision();
					JSONObject jsonObjectSupervision = jsonArraySupervision.getJSONObject(i);
					supervisionTemp.setIdSupervision(jsonObjectSupervision.getString("id"));
					supervisionTemp.setDescription(jsonObjectSupervision.getString("nombre"));
					supervisionTemp.setIdStore(jsonObjectSupervision.getString("tienda"));
					
					supervision[i] = supervisionTemp;
				}
				getResult().setSupervision(supervision);
				System.out.println("SUPERVISION[] GUARDADA");
			}
			else
			{
				System.out.println("NO VIENE EL JSON");
				
			}
			
//Obtener datos de Area			
			if (jsonObject.has(JSON_AREA)) {
				
				JSONArray jsonArrayArea = jsonObject.getJSONArray(JSON_AREA); 
				int tamano = jsonArrayArea.length();
				
				Area area[] = new Area[tamano];
				
				for (int i = 0; i < jsonArrayArea.length(); i++){
					Area areaTemp = new Area();
					JSONObject jsonObjectArea = jsonArrayArea.getJSONObject(i);
					areaTemp.setIdArea(jsonObjectArea.getString("id"));
					areaTemp.setDescription(jsonObjectArea.getString("nombre"));
					
					area[i] = areaTemp;
				}
				getResult().setArea(area);
				System.out.println("AREA[] GUARDADA");
			}
//Obtener datos de SubArea
			if (jsonObject.has(JSON_SUBAREA)) {
				
				JSONArray jsonArraySubArea = jsonObject.getJSONArray(JSON_SUBAREA); 
				int tamano = jsonArraySubArea.length();
				
				SubArea subArea[] = new SubArea[tamano];
				
				for (int i = 0; i < jsonArraySubArea.length(); i++){
					SubArea subAreaTemp = new SubArea();
					JSONObject jsonObjectSubArea = jsonArraySubArea.getJSONObject(i);
					subAreaTemp.setIdSubArea(jsonObjectSubArea.getString("id"));
					subAreaTemp.setDescription(jsonObjectSubArea.getString("nombre"));
					subAreaTemp.setIdArea(jsonObjectSubArea.getString("area"));
					
					subArea[i] = subAreaTemp;
					
				}
				getResult().setSubArea(subArea);
				System.out.println("SUBAREA[] GUARDADA");

			}
			
			if (jsonObject.has(JSON_QUIZ)) {
				JSONArray jsonArrayQuiz = jsonObject.getJSONArray(JSON_QUIZ); 
				int tamano = jsonArrayQuiz.length();
				
				Quiz quiz[] = new Quiz[tamano];
				
				for (int i = 0; i < jsonArrayQuiz.length(); i++){
					Quiz quizTemp = new Quiz();
					JSONObject jsonObjectQuiz = jsonArrayQuiz.getJSONObject(i);
					quizTemp.setId_quiz(jsonObjectQuiz.getString("id"));
					quizTemp.setDescription(jsonObjectQuiz.getString("nombre"));
					quizTemp.setIdSubArea(jsonObjectQuiz.getString("subArea"));
					
					quiz[i] = quizTemp;
				}
				getResult().setQuiz(quiz);
				System.out.println("QUIZ[] GUARDADA");

			}
			return getResult();
		
		}catch(Exception exception){
			System.out.println("NO GUARDA NADA");
			return null;
		}
		
		
		
	}
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}


	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public void setAnswer(Answer[] answer) {
		this.answer = answer;
	}

	public Answer[] getAnswer() {
		return answer;
	}

	public void setSubArea(SubArea subArea) {
		this.subArea = subArea;
	}

	public SubArea getSubArea() {
		return subArea;
	}





}
