package com.researchmobile.smartquiz.view;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.researchmobile.smartquiz.entity.Area;
import com.researchmobile.smartquiz.entity.Result;
import com.researchmobile.smartquiz.utility.ConnectState;
import com.researchmobile.smartquiz.utility.MyDialog;
import com.researchmobile.smartquiz.ws.ConnectWS;
import com.researchmobile.smartquiz.ws.RequestWS;

public class ListaArea extends Activity  {
	private Result result;
	private RequestWS requestWS;
	private String idSupervisionSelected;
	
	private SimpleAdapter simpleAdapter;
	private ListView areaListView;
	private MyDialog myDialog;
	private ConnectState connectState;
	private String username;
	private TextView supervisionTextView;
	private double latitude;
    private double longitude;
    private String codigo;
    private ProgressDialog pd = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.area);

		componentPrepare();
		try {
			llenaListaAreas();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void llenaListaAreas() throws JSONException {
		// TODO Auto-generated method stub
		setSimpleAdapter(new SimpleAdapter(this,
				ListaAreas(getResult().getArea()), R.layout.fila_lista_areas,
				new String[] { "codigo", "area", "estado" }, new int[] {
						R.id.fila_lista_areas_codigo,
						R.id.fila_lista_areas_area,
						R.id.fila_lista_areas_estado }));

		getAreaListView().setAdapter(getSimpleAdapter());

		getAreaListView().setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				@SuppressWarnings("unchecked")
				HashMap<String, String> selected = (HashMap<String, String>) getSimpleAdapter().getItem(position);
				codigo = (String) selected.get("codigo");
				String estado = (String) selected.get("estado");

				if (estado.equalsIgnoreCase("PENDIENTE")) {
					new subareasAsync().execute("");
				} else {
//					getMyDialog().AlertDialog(ListaArea.this, "AVISO", "YA SUPERVISO ESTA AREA");
				}
			}
		});
	}
	
	// Clase para ejecutar en Background
    class subareasAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(ListaArea.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	buscarSubAreas(codigo);
               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                irSubAreas();
         }
    }

    private void buscarSubAreas(String codigo){
    	if (getConnectState().isConnectedToInternet(ListaArea.this)) {
			try {
				Result resulta = new Result();
				resulta = getRequestWS().subAreaData(codigo);
				getResult().setSubArea(resulta.getSubArea());
				
			} catch (Exception exception) {
//				getMyDialog().simpleToast(this, "ERROR: INTENTE DE NUEVO");
			}
		} else {
//			getMyDialog().AlertDialog(ListaArea.this, "ALERTA", "EN ESTE MOMENTO NO CUENTA CON CONEXION A INTERNET");
		}
    }
    
	protected void irSubAreas() {
				Intent intent = new Intent(ListaArea.this, ListaSubAreas.class);
				intent.putExtra("result", result);
				intent.putExtra("idSupervisionSelected", getIdSupervisionSelected());
				intent.putExtra("idAreaSelected", codigo);
				intent.putExtra("username", getUsername());
				startActivity(intent);
	}

	private void componentPrepare() {
		Bundle bundle = getIntent().getExtras();
		setResult((Result) bundle.get("result"));
		setIdSupervisionSelected((String) bundle.getString("idSupervisionSelected"));

		setUsername((String) bundle.getString("username"));
		setMyDialog(new MyDialog());
		setRequestWS(new RequestWS());
		setAreaListView((ListView) findViewById(R.id.area_list_view));
	}
	
public ArrayList<HashMap<String, String>> ListaAreas (Area[] area) throws JSONException{int bandera = 0;
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		for (int i=0; i<area.length; i++){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("codigo", area[i].getIdArea());
	        map.put("area", area[i].getDescription());
	        map.put("estado", area[i].getEstado());
	        
	        if(area[i].getEstado().equalsIgnoreCase("false")){
	        	map.put("estado", "PENDIENTE");
	        	bandera = 1;
	        }else{
	        	map.put("estado", "CONTESTADO");
	        }
	        
	        mylist.add(map);
	    }
		if(bandera==0){
        	String url = "supervision=" + getIdSupervisionSelected() + "&longitud=" + "0.0000" + "&latitud="  + "0.0";
        	JSONObject objeto = ConnectWS.enviaEstadoSupervision(url);	 
        	if(objeto.has("resultado")){
        		getMyDialog().simpleToast(this, "SUPERVISION FINALIZADA" );
        		
        		RequestWS requestWS = new RequestWS();
        		Result resultTemp = new Result();
        		resultTemp = requestWS.allData(getUsername());
        		getResult().setBussines(resultTemp.getBussines());
        		getResult().setNetwork(resultTemp.getNetwork());
        		getResult().setSector(resultTemp.getSector());
        		getResult().setStore(resultTemp.getStore());
        		getResult().setSupervision(resultTemp.getSupervision());
        		
        		Intent intent = new Intent(ListaArea.this, QuizSelected.class);
        		intent.putExtra("result", getResult());
        		intent.putExtra("username", getUsername());
        		startActivity(intent);
        	}else{
        		getMyDialog().simpleToast(this, objeto.getString("mensaje") );
        	}
        }
		return mylist;
		
	}

public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK) {
		RequestWS requestWS = new RequestWS();
		Result resultTemp = new Result();
		resultTemp = requestWS.allData(getUsername());
		getResult().setBussines(resultTemp.getBussines());
		getResult().setNetwork(resultTemp.getNetwork());
		getResult().setSector(resultTemp.getSector());
		getResult().setStore(resultTemp.getStore());
		getResult().setSupervision(resultTemp.getSupervision());
		
		Intent intent = new Intent(ListaArea.this, QuizSelected.class);
		intent.putExtra("result", getResult());
		intent.putExtra("username", getUsername());
		startActivity(intent);
		
		return true;
	}

	return super.onKeyDown(keyCode, event);
}

private boolean gpsReference() {
    
    
    LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
    if(!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER ))
    {
        
    	Intent myIntent = new Intent( Settings.ACTION_SECURITY_SETTINGS );
        startActivity(myIntent);
    	
        return false;
        
    }else{
        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        try{
            setLatitude(loc.getLatitude());
            setLongitude(loc.getLongitude());
            return true;
            
        }catch(Exception exception){
        }
    }
    return true;
}

	public class MyLocationListener implements LocationListener {
	    public void onLocationChanged(Location location) {
	                    
	    }
	
	    @Override
	    public void onProviderDisabled(String provider) {
	        
	    }
	
	    @Override
	    public void onProviderEnabled(String provider) {
	        // TODO Auto-generated method stub
	        
	    }
	
	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	        // TODO Auto-generated method stub
	    }
	}
	
	public void setRequestWS(RequestWS requestWS) {
		this.requestWS = requestWS;
	}

	public RequestWS getRequestWS() {
		return requestWS;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Result getResult() {
		return result;
	}

	public void setAreaListView(ListView areaListView) {
		this.areaListView = areaListView;
	}

	public ListView getAreaListView() {
		return areaListView;
	}

	public void setSimpleAdapter(SimpleAdapter simpleAdapter) {
		this.simpleAdapter = simpleAdapter;
	}

	public SimpleAdapter getSimpleAdapter() {
		return simpleAdapter;
	}

	public void setMyDialog(MyDialog myDialog) {
		this.myDialog = myDialog;
	}

	public MyDialog getMyDialog() {
		return myDialog;
	}

	public void setIdSupervisionSelected(String idSupervisionSelected) {
		this.idSupervisionSelected = idSupervisionSelected;
	}

	public String getIdSupervisionSelected() {
		return idSupervisionSelected;
	}

	public void setConnectState(ConnectState connectState) {
		this.connectState = connectState;
	}

	public ConnectState getConnectState() {
		return connectState;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setSupervisionTextView(TextView supervisionTextView) {
		this.supervisionTextView = supervisionTextView;
	}

	public TextView getSupervisionTextView() {
		return supervisionTextView;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLongitude() {
		return longitude;
	}
}