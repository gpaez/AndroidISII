package com.gestorventas;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.gestorventas.R;
import com.gestorventas.database.RegisterUserClass;
import com.gestorventas.utils.Util;

public class RegisterActiviteUser extends Activity implements View.OnClickListener{
	
    private EditText editTextName;
    private EditText editTextApellido;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirmar;
    private EditText editTextEmail;
    private EditText editTextCelular;
    private Button buttonRegister;
    private String resultado;
    
    private static final String REGISTER_URL = Util.URL+"/Registration.php";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.registrousuario);
 
        editTextName              = (EditText) findViewById(R.id.edtNombre);
        editTextApellido          = (EditText) findViewById(R.id.edtApellido);
        editTextUsername          = (EditText) findViewById(R.id.edtUsuario);
        editTextPassword          = (EditText) findViewById(R.id.edtPassword);
        editTextPasswordConfirmar = (EditText) findViewById(R.id.edtPasswordConf);
        editTextEmail             = (EditText) findViewById(R.id.edtCorreo);
        editTextCelular           = (EditText) findViewById(R.id.edtCelular);
        
        buttonRegister = (Button) findViewById(R.id.btnRegistrar);
 
        buttonRegister.setOnClickListener(this);
    }
    

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == buttonRegister){
            registerUser();
        }
		
	}
	
	private void registerUser() {
		String name = editTextName.getText().toString().trim().toUpperCase();
		String apellido = editTextApellido.getText().toString().trim().toUpperCase();
        String username = editTextUsername.getText().toString().trim().toUpperCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();
        String passwordConfir = editTextPasswordConfirmar.getText().toString().trim().toLowerCase();
        String email = editTextEmail.getText().toString().trim().toLowerCase();
        String celular = editTextCelular.getText().toString().trim().toLowerCase();
        
        register(name,username,password,passwordConfir,email,celular,apellido);	
        /*if (resultado.trim().equals("Registro Completo")){
        	super.finish();
        }*/
        	
        
	}
	
	private void register(String name, String username, String password, String passwordConf,
			              String email, String celular, String apellido) {
		 class RegisterUser extends AsyncTask<String, Void, String>{
			    ProgressDialog loading;
	            RegisterUserClass ruc = new RegisterUserClass();
	            
	            @Override
	            protected void onPreExecute() {
	                super.onPreExecute();
	                loading = ProgressDialog.show(RegisterActiviteUser.this, "Please Wait",null, true, true);
	            }
	            
	            @Override
	            protected void onPostExecute(String s) {
	                super.onPostExecute(s);
	                loading.dismiss();
	                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
	            }
	            
	            @Override
	            protected String doInBackground(String... params) {
	 
	                HashMap<String, String> data = new HashMap<String,String>();
	                data.put("usuario",params[0]);                
	                data.put("password",params[2]);
	                data.put("passwordConf",params[3]);
	                data.put("nombreUser",params[1]);
	                data.put("correo",params[4]);
	                data.put("celular",params[5]);
	                data.put("apellido", params[6]);
	 
	                String result = ruc.sendPostRequest(REGISTER_URL,data);
	                RegisterActiviteUser.this.resultado = result.replaceAll("\"", "");
	                return result;
	            }
			 
		 }
		 RegisterUser ru = new RegisterUser();
	     ru.execute(name,username,password,passwordConf,email,celular,apellido);

	}

}
