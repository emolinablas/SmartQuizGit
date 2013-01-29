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
import android.widget.TextView;

import com.researchmobile.smartquiz.entity.Result;
import com.researchmobile.smartquiz.utility.ConnectState;
import com.researchmobile.smartquiz.utility.MyDialog;
import com.researchmobile.smartquiz.utility.Usuario;
import com.researchmobile.smartquiz.ws.RequestWS;

public class Login  extends Activity implements OnClickListener{
	private TextView usernameTextView;
	private TextView passwordTextView;
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
        
        componentPrepare();
        
        getUsernameTextView().setOnKeyListener(new OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)
                {
                    getPasswordTextView().requestFocus();
                    return true;
                }
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                	//
                    return true;
                }
                return false;
            }
        });
        
        getPasswordTextView().setOnKeyListener(new OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)
                {
                	loginVerification();
                	return true;
                }
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                	//
                    return true;
                }
                return false;
            }
        });
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
		setUsernameTextView((TextView)findViewById(R.id.login_username_textview));
		setPasswordTextView((TextView)findViewById(R.id.login_password_textview));
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
		setUsername(getUsernameTextView().getText().toString());
		setPassword(getPasswordTextView().getText().toString());
					
		if (getConnectState().isConnectedToInternet(Login.this)){
			if (username.equalsIgnoreCase("") || password.equalsIgnoreCase("")){
				getMyDialog().AlertDialog(this, "ERROR", "DEBE LLENAR LOS CAMPOS REQUERIDOS");
			}
				getUsuario().setUsuario(getUsername());
				getUsuario().setClave(getPassword());
				try{
				loginWS();
			}catch (Exception exception){
				getMyDialog().simpleToast(this, "ERROR: INTENTE DE NUEVO");
			}
		}else{
			getMyDialog().AlertDialog(Login.this, "LOGIN", "EN ESTE MOMENTO NO CUENTA CON CONEXION A INTERNET");
		}
	}

	private void loginWS() {
		RequestWS requestWS = new RequestWS();
		
		setResult(requestWS.login(getUsername(), getPassword(), this));
//		getMyDialog().simpleToast(this, getResult().getMessage());
		Log.e("quiz", "result = " + getResult().isResult());
		if(getResult().isResult()){
			Log.e("quiz", "result = " + getResult().isResult());
			final	Boolean error = requestData();
				if(error){
					
					getMyDialog().AlertDialog(Login.this, "ALERTA", "OCURRIO UN ERROR AL CONSULTAR LOS DATOS");
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
		Log.e("quiz", "requestData = ");
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
		getUsernameTextView().setText("");
		getPasswordTextView().setText("");
		
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

	public TextView getUsernameTextView() {
		return usernameTextView;
	}
	public void setUsernameTextView(TextView usernameTextView) {
		this.usernameTextView = usernameTextView;
	}
	public TextView getPasswordTextView() {
		return passwordTextView;
	}
	public void setPasswordTextView(TextView passwordTextView) {
		this.passwordTextView = passwordTextView;
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
}