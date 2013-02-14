package com.researchmobile.smartquiz.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.researchmobile.smartquiz.entity.Result;
import com.researchmobile.smartquiz.utility.ConnectState;
import com.researchmobile.smartquiz.utility.MyDialog;
import com.researchmobile.smartquiz.utility.Usuario;
import com.researchmobile.smartquiz.ws.RequestWS;

public class Login  extends Activity implements OnClickListener, OnKeyListener{
	private EditText usernameEditText;
	private EditText passwordEditText;
	private Button enterButton;
	private Result result;
	private String username;
	private String password;
	private Usuario usuario;
	
	private MyDialog myDialog;
	private ConnectState connectState;
	private ProgressDialog pd = null;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        try{
        componentPrepare();
        }catch(Exception e){
        	getMyDialog().AlertDialog(Login.this, "AVISO", "ERROR AL CARGAR LA VISTA");
        }
        
    }
    
    @Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)
        {
    		try{
	    		if (v == getUsernameEditText()){
	    			getPasswordEditText().requestFocus();
	    		}
	    		else if (v == getPasswordEditText()){
	    			new loginAsync().execute("");
	    		}
        	}catch(Exception e){
        		getMyDialog().AlertDialog(Login.this, "AVISO", "ERROR AL EJECUTAR EL LOGIN");
        	}
        	return true;
        }
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
        {
        	//
            return true;
        }
		return false;
	}
    
 // Clase para ejecutar en Background
    class loginAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(Login.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	loginVerification();
               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
               
                if (getResult().isResult()){
                	loginCongratulation();
                	getMyDialog().AlertDialog(Login.this, "Aviso", getResult().getMessage());
                }else{
                	getMyDialog().AlertDialog(Login.this, "Error", getResult().getMessage());
                	getUsernameEditText().setText("");
    				getPasswordEditText().setText("");
                }

         }
   }

    
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
			// Preventing default implementation previous to
			// android.os.Build.VERSION_CODES.ECLAIR
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	private void componentPrepare() {
		setResult(new Result());
		setUsernameEditText((EditText)findViewById(R.id.login_username_edittext));
		setPasswordEditText((EditText)findViewById(R.id.login_password_edittext));
		getUsernameEditText().setOnKeyListener(this);
		getPasswordEditText().setOnKeyListener(this);
		setEnterButton((Button)findViewById(R.id.login_enter_button));
		getEnterButton().setOnClickListener(this);
		setUsuario(new Usuario());
		setMyDialog(new MyDialog());
		setConnectState(new ConnectState());
		
	}
	
	public void onClick(View view) {
		if (view == getEnterButton()){
			new loginAsync().execute("");
			//Require verification data username and password
		}
	}
	private void loginVerification() {
		setUsername(getUsernameEditText().getText().toString());
		setPassword(getPasswordEditText().getText().toString());
					
		if (getConnectState().isConnectedToInternet(Login.this)){
			if (getUsername().equalsIgnoreCase("") || getPassword().equalsIgnoreCase("")){
				getResult().setMessage("Debe llenar los campos requeridos");
//				getMyDialog().AlertDialog(this, "ERROR", "DEBE LLENAR LOS CAMPOS REQUERIDOS");
			}else{
				getUsuario().setUsuario(getUsername());
				getUsuario().setClave(getPassword());
				try{
					loginWS();
				}catch(Exception e){
					getMyDialog().AlertDialog(this, "ERROR", "error en en loginWS");
				}
				
			}
				
		}else{
			getMyDialog().AlertDialog(Login.this, "LOGIN", "EN ESTE MOMENTO NO CUENTA CON CONEXION A INTERNET");
		}
	}

	private void loginWS() {
		RequestWS requestWS = new RequestWS();
		
		setResult(requestWS.login(getUsername(), getPassword(), this));
//		getMyDialog().simpleToast(this, getResult().getMessage());
		//Log.e("quiz", "result = " + getResult().isResult());
		if(getResult().isResult()){
			Log.e("quiz", "result = " + getResult().isResult());
			final	Boolean error = requestData();
				if(error){
					getResult().setMessage("Ocurrio un error al consultar los datos");
//					getMyDialog().AlertDialog(Login.this, "ALERTA", "OCURRIO UN ERROR AL CONSULTAR LOS DATOS");
					}
				else
					{
	//				loginCongratulation();
					}
		}else{
			
			cleanComponents();
		}
		
	}

	private Boolean requestData() {
		
		RequestWS requestWS = new RequestWS();
		
		Result resultTemp = new Result();
		
		resultTemp = requestWS.allData(getResult().getUser());
		
		//getResult().setBusiness(resultTemp.getBussines());
		if(resultTemp != null)
		{
		getResult().setBussines(resultTemp.getBussines());
		getResult().setNetwork(resultTemp.getNetwork());
		getResult().setSector(resultTemp.getSector());
		getResult().setStore(resultTemp.getStore());
		getResult().setSupervision(resultTemp.getSupervision());
		return false;
		}
		{
			getMyDialog().AlertDialog(Login.this, "AVISO", "COMUNIQUESE CON EL ADMINISTRADOR");
			return true;
			
		}
	}

	private void cleanComponents() {
		getPasswordEditText().setText("");
		getUsernameEditText().setText("");
		
		
	}

	private void loginCongratulation() {
		if(getResult()!=null)
		{
			Intent intent = new Intent(Login.this, QuizSelected.class);
			intent.putExtra("result", getResult());
			intent.putExtra("username", getResult().getUser());
			startActivity(intent);
		}else
		{
			getMyDialog().AlertDialog(Login.this, "AVISO", "COMUNIQUESE CON EL ADMINISTRADOR");
			
		}
		
	}

	
	public Button getEnterButton() {
		return enterButton;
	}
	public void setEnterButton(Button enterButton) {
		this.enterButton = enterButton;
	}

	public MyDialog getMyDialog() {
		return myDialog;
	}

	public void setMyDialog(MyDialog myDialog) {
		this.myDialog = myDialog;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ConnectState getConnectState() {
		return connectState;
	}

	public void setConnectState(ConnectState connectState) {
		this.connectState = connectState;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public EditText getUsernameEditText() {
		return usernameEditText;
	}

	public void setUsernameEditText(EditText usernameEditText) {
		this.usernameEditText = usernameEditText;
	}

	public EditText getPasswordEditText() {
		return passwordEditText;
	}

	public void setPasswordEditText(EditText passwordEditText) {
		this.passwordEditText = passwordEditText;
	}

	
}