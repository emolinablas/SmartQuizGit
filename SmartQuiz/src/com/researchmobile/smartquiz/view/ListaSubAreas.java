package com.researchmobile.smartquiz.view;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.researchmobile.smartquiz.entity.Answer;
import com.researchmobile.smartquiz.entity.Indicator;
import com.researchmobile.smartquiz.entity.Result;
import com.researchmobile.smartquiz.entity.SubArea;
import com.researchmobile.smartquiz.utility.ConnectState;
import com.researchmobile.smartquiz.utility.MyDialog;
import com.researchmobile.smartquiz.ws.ConnectWS;
import com.researchmobile.smartquiz.ws.RequestWS;

public class ListaSubAreas extends Activity {

	private Result result;
	private RequestWS requestWS;
	private String idSupervisionSelected;

	private SimpleAdapter simpleAdapter;
	private ListView subAreaListView;
	private MyDialog myDialog;
	private String idAreaSelected;
	private ConnectState connectState;
	private ProgressDialog pd = null;
	private String codigo;
	private Answer answer2;
	private SubArea resulta;

	private String username;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subarea);

		componentPrepare();
		verificaContestadas();
		if (verificaContestadas()) {
			llenaListaSubAreas();
		} else {
			getMyDialog().simpleToast(this, "AREA COMPLETADA");
			JSONObject status = ConnectWS.enviaEstadoArea(getIdAreaSelected());
			Result resultar = new Result();
			resultar = getRequestWS().areaData(getIdSupervisionSelected());

			getResult().setArea(resultar.getArea());

			Intent intent = new Intent(ListaSubAreas.this, ListaArea.class);
			intent.putExtra("result", result);
			intent.putExtra("idSupervisionSelected", getIdSupervisionSelected());
			intent.putExtra("username", getUsername());
			startActivity(intent);
		}
	}

	private Boolean verificaContestadas() {
		int pendientes = 0;
		int tamano = getResult().getSubArea().length;
		for (int p = 0; p < tamano; p++) {
			if (getResult().getSubArea()[p].getEstado().equalsIgnoreCase("false")) {
				pendientes++;
			}
		}
		if (pendientes == 0) {
			return false;
		} else {
			return true;
		}
	}

	private void llenaListaSubAreas() {
		setSimpleAdapter(new SimpleAdapter(this,
				ListaSubAreas(getResult().getSubArea()),
				R.layout.fila_lista_sub_areas, new String[] { "codigo",
						"subarea", "estado" }, new int[] {
						R.id.fila_lista_sub_areas_codigo,
						R.id.fila_lista_sub_areas_subarea,
						R.id.fila_lista_sub_areas_estado }));

		getSubAreaListView().setAdapter(getSimpleAdapter());
		getSubAreaListView().setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				@SuppressWarnings("unchecked")
				HashMap<String, String> selected = (HashMap<String, String>) getSimpleAdapter().getItem(position);
				codigo = (String) selected.get("codigo");
				String estado = (String) selected.get("estado");
				if (estado.equalsIgnoreCase("PENDIENTE")) {
					try {
						new iniciarAsync().execute("");
					} catch (Exception exception) {
//						getMyDialog().simpleToast(ListaSubAreas.this, "ERROR: INTENTE DE NUEVO");
					}
				} else {
//					getMyDialog().AlertDialog(ListaSubAreas.this, "AVISO", "YA SUPERVISO ESTA AREA");
				}
			}
		});
	}

	protected void irQuizes() {
		if (getConnectState().isConnectedToInternet(ListaSubAreas.this)) {
			resulta = new SubArea();
			resulta = getRequestWS().indicatorList(codigo);
			if (resulta.getRespuesta() != null) {
				answer2 = new Answer();
				answer2 = parseaQuizes(resulta);
				for (int t = 0; t < resulta.getRespuesta().length; t++) {
					for (int u = 0; u < resulta.getRespuesta()[t].getIndicator().length; u++) {
						System.out.println("estas son las preguntas:--" + resulta.getRespuesta()[t].getIndicator()[u].getPregunta());
					}
				}
			} else {
//				getMyDialog().simpleToast(this, "LA SUBAREA NO POSEE PREGUNTAS");
			}

		} else {
//			getMyDialog().AlertDialog(ListaSubAreas.this, "ALERTA","EN ESTE MOMENTO NO CUENTA CON CONEXION A INTERNET");
		}

	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			getMyDialog().simpleToast(this, "DEBE TERMINAR DE SUPERVISAR EL AREA");
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	
	
    // Clase para ejecutar en Background
    class iniciarAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(ListaSubAreas.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	irQuizes();
               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                if (resulta.getRespuesta() != null){
                	nextActivity();
                }

         }
    }

    private void nextActivity(){
    	Intent intent = new Intent(ListaSubAreas.this, IndicatorList.class);
		intent.putExtra("answer", answer2);
		intent.putExtra("idQuizSelected", answer2.getId_quiz());
		intent.putExtra("idSubArea", codigo);
		intent.putExtra("idSupervisionSelected", getIdSupervisionSelected());
		intent.putExtra("result", getResult());
		intent.putExtra("idAreaSelected", getIdAreaSelected());
		intent.putExtra("username", getUsername());
		startActivity(intent);
    }

	private Answer parseaQuizes(SubArea subarea) {// metodo que convierte el array de quizes a uno solo.
		Answer answers = new Answer();
		int contadorIndicatores = 0;
		int tamanoIndicatores = 0;
		int tamanoQuizes = subarea.getRespuesta().length;
		for (int k = 0; k < tamanoQuizes; k++) {
			tamanoIndicatores = tamanoIndicatores + subarea.getRespuesta()[k].getIndicator().length;
		}

		Indicator indicatores[] = new Indicator[tamanoIndicatores];

		for (int i = 0; i < tamanoQuizes; i++) {
			int tamanoPreguntas = subarea.getRespuesta()[i].getIndicator().length;
			for (int j = 0; j < tamanoPreguntas; j++) {
				Indicator indicatorTemp = new Indicator();
				indicatorTemp.setDescription(subarea.getRespuesta()[i].getIndicator()[j].getDescription());
				indicatorTemp.setIdIndicator(subarea.getRespuesta()[i].getIndicator()[j].getIdIndicator());
				indicatorTemp.setIdQuiz(subarea.getRespuesta()[i].getIndicator()[j].getIdQuiz());
				indicatorTemp.setPregunta(subarea.getRespuesta()[i].getIndicator()[j].getPregunta());
				indicatorTemp.setUrlPhoto(subarea.getRespuesta()[i].getIndicator()[j].getUrlPhoto());
				indicatorTemp.setState(subarea.getRespuesta()[i].getIndicator()[j].getState());
				indicatorTemp.setUseCamera(subarea.getRespuesta()[i].getIndicator()[j].isUseCamera());
				indicatorTemp.setValue(subarea.getRespuesta()[i].getIndicator()[j].getValue());
				indicatorTemp.setQuizDescription(subarea.getRespuesta()[i].getIndicator()[j].getQuizDescription());
				indicatores[contadorIndicatores] = indicatorTemp;
				contadorIndicatores++;
			}
		}

		answers = subarea.getRespuesta()[0];
		answers.setIndicator(indicatores);

		return answers;
	}

	private void componentPrepare() {
		Bundle bundle = getIntent().getExtras();
		setResult((Result) bundle.get("result"));
		setIdSupervisionSelected((String) bundle.getString("idSupervisionSelected"));
		setIdAreaSelected((String) bundle.getString("idAreaSelected"));
		setUsername((String) bundle.getString("username"));
		System.out.println("Este es el usuario en la SUBAREA " + getUsername());
		setMyDialog(new MyDialog());
		setRequestWS(new RequestWS());
		setSubAreaListView((ListView) findViewById(R.id.sub_area_listview));
	}

	public ArrayList<HashMap<String, String>> ListaSubAreas(SubArea[] subarea) {
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < subarea.length; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("codigo", subarea[i].getIdSubArea());
			map.put("subarea", subarea[i].getDescription());
			map.put("estado", subarea[i].getEstado());
			if (subarea[i].getEstado().equalsIgnoreCase("false")) {
				map.put("estado", "PENDIENTE");
			} else {
				map.put("estado", "CONTESTADO");
			}
			mylist.add(map);
		}
		return mylist;
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

	public void setSubAreaListView(ListView subAreaListView) {
		this.subAreaListView = subAreaListView;
	}

	public ListView getSubAreaListView() {
		return subAreaListView;
	}

	public void setIdAreaSelected(String idAreaSelected) {
		this.idAreaSelected = idAreaSelected;
	}

	public String getIdAreaSelected() {
		return idAreaSelected;
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

}
