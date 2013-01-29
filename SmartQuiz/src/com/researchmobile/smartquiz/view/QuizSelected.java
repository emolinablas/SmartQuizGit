package com.researchmobile.smartquiz.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.researchmobile.smartquiz.data.DataQuiz;
import com.researchmobile.smartquiz.entity.Result;
import com.researchmobile.smartquiz.utility.Convert;
import com.researchmobile.smartquiz.utility.MyDialog;
import com.researchmobile.smartquiz.ws.RequestWS;

public class QuizSelected extends Activity implements OnClickListener{
	private Spinner businessSpinner;
	private Spinner networkSpinner;
	private Spinner sectorSpinner;
	private Spinner storeSpinner;
	private Spinner supervisionSpinner;
	private Button quizBeginButton;
	private Button exitQuizButton;
	private Result result;
	
	private String idBusinessSelected;
	private String idNetworkSelected;
	private String idSectorSelected;
	private String idStoreSelected;
	private String idSupervisionSelected;
	private String supervisionSelected;
	
	private Convert convert;
	private RequestWS requestWS;
	private MyDialog myDialog;
	
	private ProgressDialog pd = null;
	//DataQuiz Temporal
	DataQuiz dataQuiz = new DataQuiz();
	//AdapterSpinner Teporal
	private ArrayAdapter<String> adapterSpinnerBusiness;
	private ArrayAdapter<String> adapterSpinnerNetwork;
	private ArrayAdapter<String> adapterSpinnerSector;
	private ArrayAdapter<String> adapterSpinnerStore;
	private ArrayAdapter<String> adapterSpinnerSupervision;
	
	private String username;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_selected);
        
        componentPrepare();
        fillAdapter();
    }
    
	private void fillAdapter() {
		try{
			adapterSpinnerBusiness();
			networkFilter();
			adapterSpinnerSector();
			storeFilter();
			supervisionFilter();
		}catch(Exception exception){
			
		}
	}

	private void networkFilter() {
		// TODO Auto-generated method stub
		getBusinessSpinner().setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							android.view.View v, int position, long id) {

						String businessSelected = getBusinessSpinner().getSelectedItem().toString();
						adapterSpinnerNetwork(businessSelected);
						
					}

					public void onNothingSelected(AdapterView<?> parent) {
						// textView.setText("");
					}
				});
		
	}
	
	/*private void sectorFilter() {
		// TODO Auto-generated method stub
		getNetworkSpinner().setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							android.view.View v, int position, long id) {

						String networkSelected = getNetworkSpinner().getSelectedItem().toString();
						adapterSpinnerSector();
						
					}

					public void onNothingSelected(AdapterView<?> parent) {
						// textView.setText("");
					}
				});
		
	}*/

	private void storeFilter() {
		getSectorSpinner().setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							android.view.View v, int position, long id) {

						String sectorSelected = getSectorSpinner().getSelectedItem().toString();
						String networkSelected = getNetworkSpinner().getSelectedItem().toString();
						adapterSpinnerStore(sectorSelected, networkSelected);
						
					}

					public void onNothingSelected(AdapterView<?> parent) {
						// textView.setText("");
					}
				});
		
		getNetworkSpinner().setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							android.view.View v, int position, long id) {

						String sectorSelected = getSectorSpinner().getSelectedItem().toString();
						String networkSelected = getNetworkSpinner().getSelectedItem().toString();
						adapterSpinnerStore(sectorSelected, networkSelected);
						
					}

					public void onNothingSelected(AdapterView<?> parent) {
						// textView.setText("");
					}
				});
		
	}
	
	private void supervisionFilter() {
		// TODO Auto-generated method stub
		getStoreSpinner().setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							android.view.View v, int position, long id) {

						String storeSelected = getStoreSpinner().getSelectedItem().toString();
						adapterSpinnerSupervision(storeSelected);
						
					}

					public void onNothingSelected(AdapterView<?> parent) {
						// textView.setText("");
					}
				});
		
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Preventing default implementation previous to
			// android.os.Build.VERSION_CODES.ECLAIR
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
    
    @Override
	public void onClick(View view) {
		if (view == getQuizBeginButton()){
			new iniciarAsync().execute("");
		}else if (view == getExitQuizButton()){
			Intent intentLogin = new Intent(QuizSelected.this, Login.class);
			startActivity(intentLogin);
		}
	}
    
    // Clase para ejecutar en Background
    class iniciarAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(QuizSelected.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	iniciar();
               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                nextActivity();
         }
    }
    
    private void iniciar(){
    	String supervisionSelected = getSupervisionSpinner().getSelectedItem().toString();
		if (supervisionSelected.equalsIgnoreCase("") || supervisionSelected == null){
//			getMyDialog().AlertDialog(QuizSelected.this, "ALERTA", "DEBE SELECCIONAR UNA SUPERVISION");
		}else{
		
			try{
				
			int tamanoSupervision = getResult().getSupervision().length;
			for (int i = 0; i < tamanoSupervision; i++){
				if (getResult().getSupervision()[i].getDescription().equalsIgnoreCase(supervisionSelected)){
						setIdSupervisionSelected(getResult().getSupervision()[i].getIdSupervision());
						setSupervisionSelected(getResult().getSupervision()[i].getDescription());
				}
			}
			Result resulta = new Result();
			resulta = getRequestWS().areaData(getIdSupervisionSelected());
			getResult().setArea(resulta.getArea());
			}
			catch (Exception exception){
				System.out.println("ERROR INTENTE DE NUEVO");
			}
		}
    }
    
    private void nextActivity(){
    	Intent intent = new Intent(QuizSelected.this, ListaArea.class);
		intent.putExtra("result", result);
		intent.putExtra("idSupervisionSelected", getIdSupervisionSelected());
		intent.putExtra("SupervisionSelected", getSupervisionSelected());
		System.out.println("Este es el usuario antes de salir del QuizSelected " + getUsername());
		intent.putExtra("username", getUsername());
		startActivity(intent);
    }
    private void adapterSpinnerBusiness() {
		//Llenar Spinner de Empresa
    	try{
    		setAdapterSpinnerBusiness(new ArrayAdapter<String>(
    				this, 
    				android.R.layout.simple_spinner_dropdown_item, 
    				getConvert().dataBusinessSpinner((getResult().getBussines()))));
    		getBusinessSpinner().setAdapter(getAdapterSpinnerBusiness());
    	}catch(Exception exception){
    		Toast.makeText(getBaseContext(), "No cuenta con supervisiones", Toast.LENGTH_SHORT).show();
    	}
	}
	
	private void adapterSpinnerNetwork(String businessSelected){
		//Llenar Spinner de Cadenas
		try{
			setAdapterSpinnerNetwork(new ArrayAdapter<String>(
					this, 
					android.R.layout.simple_spinner_dropdown_item, 
					getConvert().dataNetworkSpinner(getResult().getBussines(), getResult().getNetwork(), businessSelected)));
			getNetworkSpinner().setAdapter(getAdapterSpinnerNetwork());
		}catch(Exception exception){
			
		}
	}
	
	private void adapterSpinnerSector(){
		//Llenar Spinner de Sector
		setAdapterSpinnerSector(new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_spinner_dropdown_item, 
				getConvert().dataSectorSpinner(getResult().getSector())));
		getSectorSpinner().setAdapter(getAdapterSpinnerSector());
	
	}
	
	private void adapterSpinnerStore(String sectorSelected, String networkSelected){
		setAdapterSpinnerStore(new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_spinner_dropdown_item, 
				getConvert().dataStoreSpinner(getResult().getNetwork(), getResult().getSector(), getResult().getStore(), networkSelected, sectorSelected)));
		getStoreSpinner().setAdapter(getAdapterSpinnerStore());
	}
	
	private void adapterSpinnerSupervision(String storeSelected){	
		setAdapterSpinnerSupervision(new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_spinner_dropdown_item, 
				getConvert().dataSupervisionSpinner(getResult().getStore(), getResult().getSupervision(), storeSelected)));
		getSupervisionSpinner().setAdapter(getAdapterSpinnerSupervision());
	}
	private void componentPrepare() {
		//Datos recibidos del login
		Bundle bundle = getIntent().getExtras();
		setResult((Result)bundle.get("result"));
		System.out.println("Este es el usuario que llega en el BUNDLE QuizSelected" + (String)bundle.getString("username"));
		setUsername((String)bundle.getString("username"));
		//Inicializar los spinner
		setBusinessSpinner((Spinner)findViewById(R.id.quiz_selected_empresa_spinner));
		setNetworkSpinner((Spinner)findViewById(R.id.quiz_selected_cadena_spinner));
		setSectorSpinner((Spinner)findViewById(R.id.quiz_selected_sector_spinner));
		setStoreSpinner((Spinner)findViewById(R.id.quiz_selected_tienda_spinner));
		setSupervisionSpinner((Spinner)findViewById(R.id.quiz_selected_supervision_spinner));
		
		setConvert(new Convert());
		setRequestWS(new RequestWS());
		
		setMyDialog(new MyDialog());
		
		//Inicializar los botones
		setQuizBeginButton((Button)findViewById(R.id.quiz_selected_begin_button));
		getQuizBeginButton().setOnClickListener(this);
		
		setExitQuizButton((Button)findViewById(R.id.quiz_selected_exit_button));
		getExitQuizButton().setOnClickListener(this);
		
		//Inicializar id para filtrar datos
		setIdBusinessSelected("0");
		setIdNetworkSelected("0");
		setIdSectorSelected("0");
		setIdStoreSelected("0");
		setIdSupervisionSelected("0");
	}
	public Spinner getNetworkSpinner() {
		return networkSpinner;
	}
	public void setNetworkSpinner(Spinner networkSpinner) {
		this.networkSpinner = networkSpinner;
	}

	public Button getQuizBeginButton() {
		return quizBeginButton;
	}
	public void setQuizBeginButton(Button quizBeginButton) {
		this.quizBeginButton = quizBeginButton;
	}
	public ArrayAdapter<String> getAdapterSpinnerNetwork() {
		return adapterSpinnerNetwork;
	}
	public void setAdapterSpinnerNetwork(ArrayAdapter<String> adapterSpinnerNetwork) {
		this.adapterSpinnerNetwork = adapterSpinnerNetwork;
	}
	public ArrayAdapter<String> getAdapterSpinnerStore() {
		return adapterSpinnerStore;
	}
	public void setAdapterSpinnerStore(ArrayAdapter<String> adapterSpinnerStore) {
		this.adapterSpinnerStore = adapterSpinnerStore;
	}
	
	public Button getExitQuizButton() {
		return exitQuizButton;
	}

	public void setExitQuizButton(Button exitQuizButton) {
		this.exitQuizButton = exitQuizButton;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Convert getConvert() {
		return convert;
	}

	public void setConvert(Convert convert) {
		this.convert = convert;
	}


	public String getIdStoreSelected() {
		return idStoreSelected;
	}

	public void setIdStoreSelected(String idStoreSelected) {
		this.idStoreSelected = idStoreSelected;
	}

	

	public RequestWS getRequestWS() {
		return requestWS;
	}

	public void setRequestWS(RequestWS requestWS) {
		this.requestWS = requestWS;
	}

	public MyDialog getMyDialog() {
		return myDialog;
	}

	public void setMyDialog(MyDialog myDialog) {
		this.myDialog = myDialog;
	}

	public void setBusinessSpinner(Spinner businessSpinner) {
		this.businessSpinner = businessSpinner;
	}

	public Spinner getBusinessSpinner() {
		return businessSpinner;
	}

	public void setSectorSpinner(Spinner sectorSpinner) {
		this.sectorSpinner = sectorSpinner;
	}

	public Spinner getSectorSpinner() {
		return sectorSpinner;
	}

	public void setStoreSpinner(Spinner storeSpinner) {
		this.storeSpinner = storeSpinner;
	}

	public Spinner getStoreSpinner() {
		return storeSpinner;
	}

	public void setSupervisionSpinner(Spinner supervisionSpinner) {
		this.supervisionSpinner = supervisionSpinner;
	}

	public Spinner getSupervisionSpinner() {
		return supervisionSpinner;
	}

	public void setIdBusinessSelected(String idBusinessSelected) {
		this.idBusinessSelected = idBusinessSelected;
	}

	public String getIdBusinessSelected() {
		return idBusinessSelected;
	}

	public void setIdNetworkSelected(String idNetworkSelected) {
		this.idNetworkSelected = idNetworkSelected;
	}

	public String getIdNetworkSelected() {
		return idNetworkSelected;
	}

	public void setIdSectorSelected(String idSectorSelected) {
		this.idSectorSelected = idSectorSelected;
	}

	public String getIdSectorSelected() {
		return idSectorSelected;
	}

	public void setIdSupervisionSelected(String idSupervisionSelected) {
		this.idSupervisionSelected = idSupervisionSelected;
	}

	public String getIdSupervisionSelected() {
		return idSupervisionSelected;
	}

	public void setAdapterSpinnerBusiness(ArrayAdapter<String> adapterSpinnerBusiness) {
		this.adapterSpinnerBusiness = adapterSpinnerBusiness;
	}

	public ArrayAdapter<String> getAdapterSpinnerBusiness() {
		return adapterSpinnerBusiness;
	}

	public void setAdapterSpinnerSector(ArrayAdapter<String> adapterSpinnerSector) {
		this.adapterSpinnerSector = adapterSpinnerSector;
	}

	public ArrayAdapter<String> getAdapterSpinnerSector() {
		return adapterSpinnerSector;
	}

	public void setAdapterSpinnerSupervision(
			ArrayAdapter<String> adapterSpinnerSupervision) {
		this.adapterSpinnerSupervision = adapterSpinnerSupervision;
	}

	public ArrayAdapter<String> getAdapterSpinnerSupervision() {
		return adapterSpinnerSupervision;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setSupervisionSelected(String supervisionSelected) {
		this.supervisionSelected = supervisionSelected;
	}

	public String getSupervisionSelected() {
		return supervisionSelected;
	}
	
}