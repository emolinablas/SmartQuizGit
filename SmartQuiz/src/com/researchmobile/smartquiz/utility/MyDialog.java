package com.researchmobile.smartquiz.utility;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.researchmobile.smartquiz.view.R;

public class MyDialog {
	
	//Mostrar un Toast Simple recibe el Contexto y un String para mostrar el mensaje
	public void simpleToast (Context context, String message){
		
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		
	}
	
	//Mostrar un Dialog con opciones Yes / No pasandoles Contexto, String título, String mensaje y los onClick Listener para cada opcion
	public static void showYesNoPrompt(Context _context, String title, String message, OnClickListener onYesListener, OnClickListener onNoListener) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(_context);
	    builder.setTitle(title);
	    builder.setIcon(android.R.drawable.ic_dialog_info); // lame icon
	    builder.setMessage(message);
	    builder.setCancelable(false);
	    builder.setPositiveButton("Yes", (android.content.DialogInterface.OnClickListener) onYesListener);
	    builder.setNegativeButton("No", (android.content.DialogInterface.OnClickListener) onNoListener);
	    builder.show();
	}
	
	//Mostrar un Dialog de Bienvenida
	public static void openFirstTimePopup(final Context context) {
	    final String message = "GRACIAS..!!";
	    final AlertDialog.Builder builder = new AlertDialog.Builder(context).
	    setCancelable(true).
	    setTitle("BIENVENIDO!").
	    setMessage(message).
	    setNegativeButton("CERRAR",null);

	    final AlertDialog di = builder.create();
	    
	    di.show();
	  }
	
	//Mostrar un Dialog con icono
	public static void showMessage(Context _context, String title, String message, int icon, DialogInterface.OnClickListener ackHandler) {
	      AlertDialog.Builder builder = new AlertDialog.Builder(_context);
	      builder.setTitle(title);
	      builder.setMessage(Html.fromHtml(message));
	      builder.setCancelable(false);
	      builder.setPositiveButton("BOTON", ackHandler);
	      builder.setIcon(icon);
	      builder.show();
	    }
	
	//Mostrar un AlertDialog
	public static void showAlert(Context context, String title, String text, boolean showOk) {
		    Builder alertBuilder = new Builder(context);
		    alertBuilder.setTitle(title);
		    alertBuilder.setMessage(text);
		    if (showOk)
		      alertBuilder.setNeutralButton("??", null);
		    alertBuilder.create().show();
		  }

		  public static void showAlert(Context context, String title, String text) {
		    showAlert(context, title, text, true);
		  }
		  
		  
		  //Mostrar un AletDialog de Ayuda
		  public static void AlertDialog(final Context ctx, final CharSequence title, final CharSequence message) {
		      new AlertDialog.Builder(ctx)
		      .setIcon(ctx.getResources().getDrawable(R.drawable.alert))
		      .setTitle(title)
		      .setMessage(message)
		      .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		              public void onClick(DialogInterface dialog, int whichButton) {
		            	  
		              }
		      })
		      .show();
		      }
	
	

}
