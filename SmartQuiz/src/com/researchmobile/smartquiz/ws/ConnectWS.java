package com.researchmobile.smartquiz.ws;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ConnectWS {
	private static final String finalURL = "http://54.242.188.248:9010/ServiciosGenerales/";
	private static final String finalURLQuiz = "http://54.242.188.248:9010/ServiciosQuiz/";
//	private static final String finalUrlArea = "http://200.6.241.218:9010/ServiciosSupervision/";
	
	public static JSONArray getJsonArray(String url) { //Clase que obtienen el objeto JSON
		
		JSONArray jsonArray = null;
		try{
			URL urlConnect = new URL(finalURL + url);    //Cadena de Conexión URL
			//System.out.println(finalURL + url);
	        HttpURLConnection urlConnection = (HttpURLConnection)urlConnect.openConnection(); //Haciendo la conexión 
	        InputStream inputStream = urlConnection.getInputStream();   //Obteniendo inputStream
	        String responseInputStream = convertStreamToString(inputStream);    //Convirtiendo el inputStream a String
	        jsonArray = new JSONArray(responseInputStream);   //asignando el Objeto JSON a response
	        
	    }catch (Exception exception){
			
		}
		return jsonArray;
	}
	
	public static JSONObject getJsonObject(String url) { //Clase que obtienen el objeto JSON
		
		JSONObject jsonObject = null;
		try{
			URL urlConnect = new URL(finalURL + url);    //Cadena de Conexión URL
			System.out.println(finalURL + url);
	        HttpURLConnection urlConnection = (HttpURLConnection)urlConnect.openConnection(); //Haciendo la conexión 
	        InputStream inputStream = urlConnection.getInputStream();   //Obteniendo inputStream
	        String responseInputStream = convertStreamToString(inputStream);    //Convirtiendo el inputStream a String
	        jsonObject = new JSONObject(responseInputStream);   //asignando el Objeto JSON a response
	        
	    }catch (Exception exception){
			//Conectarse a la Base de Datos
		}
		return jsonObject;
	}
	
	public static JSONObject getJsonObjectQuizes(String url) { //Clase que obtienen el objeto JSON
		
		JSONObject jsonObject = null;
		try{
			URL urlConnect = new URL(finalURLQuiz + url);    //Cadena de Conexión URL
			System.out.println(urlConnect);
	        HttpURLConnection urlConnection = (HttpURLConnection)urlConnect.openConnection(); //Haciendo la conexión
	        System.out.println(urlConnection.getResponseCode());
	        InputStream inputStream = urlConnection.getInputStream();   //Obteniendo inputStream      
	        String responseInputStream = convertStreamToString(inputStream);    //Convirtiendo el inputStream a String4
	        System.out.println(responseInputStream);
	        jsonObject = new JSONObject(responseInputStream);   //asignando el Objeto JSON a response
	        
	        
	    }catch (Exception exception){
	    	
		}
		return jsonObject;
	}
	
	public static JSONObject enviaRespuestas(String url){
		JSONObject jsonObject = null;
		
		try{
			
			URL urlCon = new URL("http", "54.242.188.248", 9010, "/ServiciosQuiz/" + url); 
			URL urlConnect = new URL(finalURLQuiz + url);    //Cadena de Conexión URL
			HttpURLConnection urlConnection = (HttpURLConnection)urlCon.openConnection(); //Haciendo la conexión
			System.out.println(urlCon);
			InputStream inputStream = urlConnection.getInputStream();   //Obteniendo inputStream
			String responseInputStream = convertStreamToString(inputStream);    //Convirtiendo el inputStream a String
	        jsonObject = new JSONObject(responseInputStream);   //asignando el Objeto JSON a response
			
	    }catch (Exception exception){
			System.out.println(exception);
		}
		return jsonObject;
	}
	
	public static JSONObject enviaEstadoSubArea(String url){
		JSONObject jsonObject = null;
		
		try{
			
			//http://200.6.241.218:9010/ServiciosSupervision/actualizarEstadoSubArea?supervisionSubArea=1
			URL urlCon = new URL("http", "54.242.188.248", 9010, "/ServiciosSupervision/actualizarEstadoSubArea?supervisionSubArea=" + url); 
			URL urlConnect = new URL(finalURLQuiz + url);    //Cadena de Conexión URL
			HttpURLConnection urlConnection = (HttpURLConnection)urlCon.openConnection(); //Haciendo la conexión
			System.out.println(urlCon);
			InputStream inputStream = urlConnection.getInputStream();   //Obteniendo inputStream
			String responseInputStream = convertStreamToString(inputStream);    //Convirtiendo el inputStream a String
	        jsonObject = new JSONObject(responseInputStream);   //asignando el Objeto JSON a response
			
	    }catch (Exception exception){
			System.out.println(exception);
		}
		return jsonObject;
	}
	
	public static JSONObject enviaEstadoArea(String url){
		JSONObject jsonObject = null;
		
		try{
			
			//http://200.6.241.218:9010/ServiciosSupervision/actualizarEstadoSubArea?supervisionSubArea=1
			URL urlCon = new URL("http", "54.242.188.248", 9010, "/ServiciosSupervision/actualizarEstadoArea?supervisionArea=" + url); 
			URL urlConnect = new URL(finalURLQuiz + url);    //Cadena de Conexión URL
			HttpURLConnection urlConnection = (HttpURLConnection)urlCon.openConnection(); //Haciendo la conexión
			System.out.println(urlCon);
			InputStream inputStream = urlConnection.getInputStream();   //Obteniendo inputStream
			String responseInputStream = convertStreamToString(inputStream);    //Convirtiendo el inputStream a String
	        jsonObject = new JSONObject(responseInputStream);   //asignando el Objeto JSON a response
			
	    }catch (Exception exception){
			System.out.println(exception);
		}
		return jsonObject;
	}
	
	public static JSONObject enviaEstadoSupervision(String url){
		JSONObject jsonObject = null;
		
		try{
			
			//http://200.6.241.218:9010/ServiciosSupervision/actualizarEstadoSubArea?supervisionSubArea=1
			URL urlCon = new URL("http", "54.242.188.248", 9010, "/ServiciosSupervision/finalizarSupervision?" + url); 
		//	URL urlConnect = new URL(finalURLQuiz + url);    //Cadena de Conexión URL
			HttpURLConnection urlConnection = (HttpURLConnection)urlCon.openConnection(); //Haciendo la conexión
			System.out.println(urlCon);
			InputStream inputStream = urlConnection.getInputStream();   //Obteniendo inputStream
			String responseInputStream = convertStreamToString(inputStream);    //Convirtiendo el inputStream a String
	        jsonObject = new JSONObject(responseInputStream);   //asignando el Objeto JSON a response
			
	    }catch (Exception exception){
			System.out.println(exception);
		}
		return jsonObject;
	}
	
	public static JSONObject enviaSupervision(String url){
		JSONObject jsonObject = null;
		
		try{
			
			URL urlCon = new URL("http", "54.242.188.248", 9010, "/ServiciosSupervision/" + url); 
			URL urlConnect = new URL(finalURLQuiz + url);    //Cadena de Conexión URL
			HttpURLConnection urlConnection = (HttpURLConnection)urlCon.openConnection(); //Haciendo la conexión
			System.out.println(urlCon);
			InputStream inputStream = urlConnection.getInputStream();   //Obteniendo inputStream
			String responseInputStream = convertStreamToString(inputStream);    //Convirtiendo el inputStream a String
			System.out.println(responseInputStream);
	        jsonObject = new JSONObject(responseInputStream);   //asignando el Objeto JSON a response
			
	    }catch (Exception exception){
			System.out.println(exception);
		}
		return jsonObject;
	}
	
	public static JSONObject enviaArea(String url){
		JSONObject jsonObject = null;
		
		try{
			
			URL urlCon = new URL("http", "54.242.188.248", 9010, "/ServiciosSupervision/" + url); 
			URL urlConnect = new URL(finalURLQuiz + url);    //Cadena de Conexión URL
			HttpURLConnection urlConnection = (HttpURLConnection)urlCon.openConnection(); //Haciendo la conexión
			System.out.println(urlCon);
			InputStream inputStream = urlConnection.getInputStream();   //Obteniendo inputStream
			String responseInputStream = convertStreamToString(inputStream);    //Convirtiendo el inputStream a String
			System.out.println(responseInputStream);
	        jsonObject = new JSONObject(responseInputStream);   //asignando el Objeto JSON a response
			
	    }catch (Exception exception){
			System.out.println(exception);
		}
		return jsonObject;
	}

	public static JSONObject enviaImagen(String path, String id){
		
		JSONObject jsonObject = null;
		String myJpgPath = path;
		System.out.println(myJpgPath);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		Bitmap bm = BitmapFactory.decodeFile(myJpgPath, options);
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream(); 
        bm.compress(Bitmap.CompressFormat.JPEG, 20, stream); 
        byte[] byteArray = stream.toByteArray();
        
        try {
            //InputStream is = this.getAssets().open(myJpgPath);
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://54.242.188.248:9010/ServiciosQuiz/cargarFoto?");
            byte[] data = byteArray;
            InputStreamBody isb = new InputStreamBody(new ByteArrayInputStream(byteArray),"imagen");
            StringBody sb1 = new StringBody(id);
            //StringBody sb2 = new StringBody("someTextGoesHere too");
            MultipartEntity multipartContent = new MultipartEntity();
            multipartContent.addPart("imagen", isb);
            multipartContent.addPart("respuesta", sb1);
            //multipartContent.addPart("two", sb2);
            postRequest.setEntity(multipartContent);
            HttpResponse res = httpClient.execute(postRequest);
            InputStream inputStream = res.getEntity().getContent();   //Obteniendo inputStream
			String responseInputStream = convertStreamToString(inputStream);    //Convirtiendo el inputStream a String
	        jsonObject = new JSONObject(responseInputStream);   //asignando el Objeto JSON a response
            res.getEntity().getContent().close();
            return jsonObject;
        } catch (Throwable e)
        {
            // handle exception here
        }
        return jsonObject;
	}
	
	
	private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder(); 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}

