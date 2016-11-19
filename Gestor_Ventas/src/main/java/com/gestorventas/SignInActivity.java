package com.gestorventas;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.gestorventas.database.DatabaseProvider;
import com.gestorventas.tablas.TConfiguracion;
import com.gestorventas.tablas.TUsuario;
import com.dd.processbutton.iml.ActionProcessButton;
import com.gestorventas.utils.ProgressGenerator;
import com.gestorventas.utils.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.gestorventas.clases.Usuario;



public class SignInActivity extends Activity implements ProgressGenerator.OnCompleteListener {

    public static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";
    private final String URL = Util.URL+"/acceso.php";
	//private DBAdapter dbHelper;
	private Cursor cursor;
	private EditText editEmail;
	private EditText editPassword; 
	private TextView lblRegistrar;
	private ActionProcessButton btnSignIn;
	private ProgressGenerator progressGenerator;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_sign_in);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        lblRegistrar = (TextView) findViewById(R.id.lblRegistrar);

        progressGenerator = new ProgressGenerator(this);
        btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);

        
        buscarConfiguraciones();
        
        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.getBoolean(EXTRAS_ENDLESS_MODE)) {
            btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        } else {
            btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);
        }
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if( checklogindata( editEmail.getText().toString().trim() , editPassword.getText().toString().trim() )){
            		onComplete();
            		//new asynclogin().execute(editEmail.getText().toString().trim() , editPassword.getText().toString().trim()); 
            	}else{
            		err_login();
            		return;
            	}
                
            }
        });
		lblRegistrar.setVisibility(View.INVISIBLE);
    }

    private void buscarConfiguraciones() {
    	
    	try {
    
            
    		String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date()); 
    		final String inFileName = "//data//data//com.gestorventas//databases//GestorVentas_Data";
    		File dbFile = new File(inFileName);
    		FileInputStream fis = null;
    		fis = new FileInputStream(dbFile);
    		String directorio = Environment.getExternalStorageDirectory()+"/ventas.db";
    		File d = new File(directorio);   		
    		if (!d.exists()) {
    		d.mkdir();
    		}

    		String outFileName = directorio + "/"+"GestorVentas_Data" + "_"+timeStamp;
    		OutputStream output = new FileOutputStream(outFileName);
    		byte[] buffer = new byte[1024];
    		int length;
    		while ((length = fis.read(buffer)) > 0) {
    		output.write(buffer, 0, length);
    		}
    		output.flush();
    		output.close();
    		fis.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
    	

    	
		//
    	String[] projection = new String[]{
    		TConfiguracion.COL_ROWID,
    		TConfiguracion.COL_NAMESPACE,
    		TConfiguracion.COL_URL
    	};
    	try {
    		Cursor configuracion = getContentResolver().query(DatabaseProvider.CONFIGURACION_CONTENT_URI,
    				projection, null, null, null);
        	
        	if (configuracion.moveToFirst()){
        		
        	}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
    	
	}

	@Override
    public void onComplete() {
        //Toast.makeText(this, R.string.Loading_Complete, Toast.LENGTH_LONG).show();
		String[] projection = new String[]{
	    		TUsuario.COL_ROWID,
	    		TUsuario.COL_USUARIO,
	    		TUsuario.COL_CLAVE,
		        TUsuario.COL_VENDENDOR};
		boolean acceso = false;
		Cursor usuario = getContentResolver().query(DatabaseProvider.USUARIO_CONTENT_URI, projection, 
				null, null,null);
		try{
			if (usuario.moveToFirst()){
				for(int i =0; i < usuario.getCount() - 1; i++) {

					if ((usuario.getString(1).trim().toLowerCase().equals(editEmail.getText().toString().trim().toLowerCase()))
							&& (usuario.getString(2).trim().toLowerCase().equals(editPassword.getText().toString().trim().toLowerCase()))) {
						acceso = true;
						Usuario.VENDEDOR = usuario.getInt(3);
					}
					usuario.moveToNext();
				}
			}
	    	if (acceso){
		        Intent intent = new Intent(this, MenuActivity.class);
		        intent.putExtra(SignInActivity.EXTRAS_ENDLESS_MODE, true);
		        startActivity(intent);
				editEmail.setText("");
				editPassword.setText("");
	    	}else{
	    		err_login();
	    		return;
	    	}
		}catch (Exception e) {
			err_login(e.getMessage());
		}finally{
			usuario.close();
		}
    }
    
    public boolean checklogindata(String username ,String password ){
    	
        if 	(username.equals("") || password.equals("")){
        	Log.e("Login ui", "checklogindata user or pass error");
        return false;
        
        }else{
        	
        	return true;
        }
    }
    
    public void err_login(){
    	Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(200);
	    Toast toast1 = Toast.makeText(getApplicationContext(),"Error:Nombre de usuario o password incorrectos", Toast.LENGTH_SHORT);
 	    toast1.show();    	
    }

    public void err_login(String mensaje){
    	Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(200);
	    Toast toast1 = Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_SHORT);
 	    toast1.show();
    }
    
    class asynclogin extends AsyncTask< String, String, String > {
    	String user,pass;
    	protected void onPreExecute() {
    		
    	}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			return null;
		}  
		
		protected void onPostExecute(String result) {
			if (result.equals("ok")){
				editPassword.setText("");
				progressGenerator.start(btnSignIn);
                btnSignIn.setEnabled(false);
                editEmail.setEnabled(false);
                editPassword.setEnabled(false);
			}else{
            	err_login();
            }
		}
    }


}
