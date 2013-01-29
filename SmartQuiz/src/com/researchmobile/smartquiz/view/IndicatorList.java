package com.researchmobile.smartquiz.view;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.researchmobile.smartquiz.entity.Answer;
import com.researchmobile.smartquiz.entity.Indicator;
import com.researchmobile.smartquiz.entity.Result;
import com.researchmobile.smartquiz.entity.SubArea;
import com.researchmobile.smartquiz.utility.ConnectState;
import com.researchmobile.smartquiz.utility.Convert;
import com.researchmobile.smartquiz.utility.Data;
import com.researchmobile.smartquiz.utility.MyDate;
import com.researchmobile.smartquiz.utility.MyDialog;
import com.researchmobile.smartquiz.utility.Usuario;
import com.researchmobile.smartquiz.ws.ConnectWS;
import com.researchmobile.smartquiz.ws.RequestWS;

public class IndicatorList extends Activity implements OnClickListener{
	final static int CAMERA_RESULT = 0;
	
	private Answer answer;
	private MyDialog myDialog;
	private Convert convert;
	private RequestWS requestWS;
	private MyDate myDate;
	private Result result;
	private Usuario usuario;
	
	private SimpleAdapter simpleAdapter;
	private ArrayList<HashMap<String, Object>> arrayList;
	private Data dataList = new Data();
	private ListView testListView;
	private String idActive;
	private ImageView activeImage;
	private int position;
	private String subIdArea;
	private String idSupervisionSelected;
	private String idAreaSelected;
	private ConnectState connectState;
	
	private Button sendQuizButton;
	
	private String username;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        componentPrepare();
      //  getAnswer().setStartTime(getMyDate().Hora());
        
        getSendQuizButton().setOnClickListener(this);
        fillData(getPosition());
        selectItem();        
    }
    
    @Override
	public void onClick(View view) {
		if (view == getSendQuizButton()){
			final ProgressDialog pd = new ProgressDialog(IndicatorList.this);
	        pd.setTitle("Enviando...");
	        pd.setMessage("Favor Espere...");
	        pd.setCancelable(true);
	        pd.show();
			sendDataQuiz();
		}
		
	}
    
    private void sendDataQuiz() {
    	
    	if (getConnectState().isConnectedToInternet(IndicatorList.this)){
			
    	//	final ProgressDialog pd = ProgressDialog.show(this, "Conectando...", "Favor espere...", false);
    		
    		getAnswer().setEndTime(getMyDate().Hora());
    		String parametros = getConvert().answerString(getSubIdArea());
    		String respuestas = getConvert().indicatorJsonString(getAnswer());
    		String linkFinal = parametros + "&respuestas=" + respuestas;
    		System.out.println(linkFinal);
    		if (getRequestWS().sendData(linkFinal, getAnswer().getIndicator())){
    			//allDataRequest();
    			JSONObject status = ConnectWS.enviaEstadoSubArea(getSubIdArea());
    			
    			try{
    			if(Boolean.parseBoolean(status.getString("resultado"))){
    				
    				
    			}
    			}catch (Exception exception){
    				System.out.println("OCURRIO UN ERROR AL ACTUALIZAR EL ESTADO");
    			}
    			//pd.dismiss();
    			getMyDialog().simpleToast(this, "QUIZ ENVIADO");
    			
    			Result resulta = new Result();
    			resulta = getRequestWS().subAreaData(getIdAreaSelected());
    			getResult().setSubArea(resulta.getSubArea());
    			
    			
    			Intent intent = new Intent(IndicatorList.this, ListaSubAreas.class);
    			intent.putExtra("result", result);
    			intent.putExtra("idSupervisionSelected", getIdSupervisionSelected());
    			intent.putExtra("idAreaSelected", getIdAreaSelected());
    			System.out.println("Este es el usuario en el LISTADO DE PREGUNTAS ANTES DEL INTENT " + getUsername());
    			intent.putExtra("username", getUsername());
    			startActivity(intent);
    			
    			
    			//Intent intent = new Intent(IndicatorList.this, ListaSubAreas.class);
    			//intent.putExtra("result", getResult());
    			//startActivity(intent);
    			//Toast.makeText(getBaseContext(), "si funciono", Toast.LENGTH_LONG).show();
    		}else{
    			//Toast.makeText(getBaseContext(), "no funciono", Toast.LENGTH_LONG).show();
    			getMyDialog().simpleToast(this, "NO SE PUEDE ENVIAR");
    		}
    				
    				}
    			else{
    				getMyDialog().AlertDialog(IndicatorList.this, "ALERTA", "EN ESTE MOMENTO NO CUENTA CON CONEXION A INTERNET");
    			}
    			
    	
		
		
	}
    
    private void allDataRequest() {
		loginWS();
		
	}
    
    private void loginWS() {
		RequestWS requestWS = new RequestWS();
		
		setResult(requestWS.login(getUsuario().getUsuario(), getUsuario().getClave(), this));
		getMyDialog().simpleToast(this, getResult().getMessage());
		if(getResult().isResult()){
			requestData();
		}
		
	}

	private void requestData() {
		RequestWS requestWS = new RequestWS();
		Result resultTemp = new Result();
		resultTemp = requestWS.allData(getResult().getUser());
		getResult().setRed(resultTemp.getRed());
		getResult().setStore(resultTemp.getStore());
		getResult().setArea(resultTemp.getArea());
		getResult().setSubArea(resultTemp.getSubArea());
		getResult().setQuiz(resultTemp.getQuiz());
	}

	private void componentPrepare() {
    	
    	Bundle bundle = getIntent().getExtras();
    	setAnswer((Answer)bundle.get("answer"));
    	getAnswer().setId_quiz((String)bundle.getString("idQuizSelected"));
    	setIdSupervisionSelected((String)bundle.getString("idSupervisionSelected"));
    	setSubIdArea((String)bundle.getString("idSubArea"));
    	setResult((Result)bundle.get("result"));
    	setIdAreaSelected((String)bundle.getString("idAreaSelected"));
    	setUsername((String)bundle.getString("username"));
    	System.out.println("Este es el usuario en el LISTADO DE PREGUNTAS " + getUsername());
    	setMyDate(new MyDate());
    	setRequestWS(new RequestWS());
    	setResult(new Result());
    	setUsuario(new Usuario());

        setPosition(0);
        setConvert(new Convert());
        setMyDialog(new MyDialog());
        setTestListView((ListView)findViewById(R.id.testListView));
        
        setActiveImage((ImageView)findViewById(R.id.selectImage));
        setSendQuizButton((Button)findViewById(R.id.list_quiz_send_button));
        
	}

	private void selectItem() {
		getTestListView().setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              
          	  HashMap<String, Object> selected = getArrayList().get(position); // Get the HashMap of the clicked item
          	  setIdActive((String)selected.get("idQuiz")); // Get Attribute name of the HashMap
          	  String path = (String)selected.get("pathPhoto");
          	  
          	  verImagen(path);
          	  
          	  Indicator[] indicatorTemp = getDataList().dataIndicatorState(getIdActive(), getAnswer().getIndicator());
          	  getAnswer().setIndicator(indicatorTemp);
          	  setPosition(getTestListView().getFirstVisiblePosition());
          	  fillData(getPosition());
            }

			
        });
	}
	/*
	private Bitmap photoView(String path) {
		String myJpgPath = path;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 0;
		Bitmap bm = BitmapFactory.decodeFile(myJpgPath, options);
		
		return bm;
	}
*/
	private void verImagen(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
      	options.inSampleSize = 0;
      	Bitmap bm = BitmapFactory.decodeFile(path, options);
      	System.out.println("VER IMAGEN, path= " + path);  
      	getActiveImage().setImageBitmap(bm);
		
	}
	
	private void fillData(int position) {
		
		setArrayList(getConvert().indicatorListTest(getAnswer().getIndicator(), this));
		
		
		setSimpleAdapter(new SimpleAdapter(this, getArrayList(), R.layout.row_test,
                new String[] {"quizPertenece","idQuiz", "photo", "image", "quiz", "pathPhoto"}, 
                new int[] {R.id.quiz_pertenece,R.id.idTest, R.id.photo, R.id.state_image, R.id.test_textview, R.id.pathPhoto_textView}));
        
		getTestListView().setAdapter(getSimpleAdapter());
		registerForContextMenu(getTestListView());
		getTestListView().setSelection(position);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	  if (v.getId()==R.id.testListView) {
		  
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    menu.setHeaderTitle(getAnswer().getIndicator()[info.position].getDescription());
	    setIdActive(getAnswer().getIndicator()[info.position].getIdIndicator());
	    String[] menuItems = {"CAPTURAR FOTO", "CANCELAR"};
	    for (int i = 0; i<menuItems.length; i++) {
	      menu.add(Menu.NONE, i, i, menuItems[i]);
	    }
	  }
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  int menuItemIndex = item.getItemId();
	  if (menuItemIndex == 0){
		  capturarFoto();
		  fillData(getPosition());
	  }else{
		  Toast.makeText(getBaseContext(), "CANCELADO", Toast.LENGTH_LONG).show();
	  }
	  
	  return true;
	}

	private void capturarFoto() {
		
		//Activar la camara
		
	  	Intent cIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		//startActivityForResult(cIntent, CAMERA_RESULT); 
		
		//asignar nombre y direccion a la imagen
		String path = "/quiz"+getIdActive()+".jpg";
		//crear nuevo archivo (imagen)
		File f = new File(Environment.getExternalStorageDirectory() + path);
		Uri uri = Uri.fromFile(f);
		System.out.println("capturar foto, path= " + path);
		cIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(cIntent, CAMERA_RESULT);
		verImagen(path);
		Indicator[] indicator = getDataList().dataTestPhoto(getIdActive(), getAnswer().getIndicator(), path);
		getAnswer().setIndicator(indicator);
    	//setTest(testTemp);
    	// Photo View on Header
    	setPosition(getTestListView().getFirstVisiblePosition());
    	
		
	}
	
/*	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == CAMERA_RESULT) {  
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            getActiveImage().setImageBitmap(photo);
        }  
    }*/
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Preventing default implementation previous to
			// android.os.Build.VERSION_CODES.ECLAIR
			getMyDialog().simpleToast(this, "DEBE TERMINAR DE SUPERVISAR");
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	public SimpleAdapter getSimpleAdapter() {
		return simpleAdapter;
	}

	public void setSimpleAdapter(SimpleAdapter simpleAdapter) {
		this.simpleAdapter = simpleAdapter;
	}

	public ArrayList<HashMap<String, Object>> getArrayList() {
		return arrayList;
	}

	public void setArrayList(ArrayList<HashMap<String, Object>> arrayList) {
		this.arrayList = arrayList;
	}

	public Data getDataList() {
		return dataList;
	}

	public void setDataList(Data dataList) {
		this.dataList = dataList;
	}

	public ListView getTestListView() {
		return testListView;
	}

	public void setTestListView(ListView testListView) {
		this.testListView = testListView;
	}

	public String getIdActive() {
		return idActive;
	}

	public void setIdActive(String idActive) {
		this.idActive = idActive;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Button getSendQuizButton() {
		return sendQuizButton;
	}

	public void setSendQuizButton(Button sendQuizButton) {
		this.sendQuizButton = sendQuizButton;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public MyDialog getMyDialog() {
		return myDialog;
	}

	public void setMyDialog(MyDialog myDialog) {
		this.myDialog = myDialog;
	}

	public Convert getConvert() {
		return convert;
	}

	public void setConvert(Convert convert) {
		this.convert = convert;
	}

	public RequestWS getRequestWS() {
		return requestWS;
	}

	public void setRequestWS(RequestWS requestWS) {
		this.requestWS = requestWS;
	}

	public MyDate getMyDate() {
		return myDate;
	}

	public void setMyDate(MyDate myDate) {
		this.myDate = myDate;
	}

	public ImageView getActiveImage() {
		return activeImage;
	}

	public void setActiveImage(ImageView activeImage) {
		this.activeImage = activeImage;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setSubIdArea(String subIdArea) {
		this.subIdArea = subIdArea;
	}

	public String getSubIdArea() {
		return subIdArea;
	}

	public void setIdSupervisionSelected(String idSupervisionSelected) {
		this.idSupervisionSelected = idSupervisionSelected;
	}

	public String getIdSupervisionSelected() {
		return idSupervisionSelected;
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