package com.gestorventas.utils;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.support.v4.app.Fragment;


import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.gestorventas.R;

public class Util {
	
	public static final String URL= "http://192.168.1.9:85/barbosys";
	public static String PUERTO;
	public static String URLFOLDER;
	public static String DIRECCIONWEB;
	public static final int CODIGOCLIENTE = 100;
	public static final int RAZONSOCIAL = 101;
	
	public static String dateToSQL(String date)
	  {
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		
		Date fecha1 = null;
		
		try {

			fecha1 = formatoDelTexto.parse(date);

			} catch (ParseException ex) {

			ex.printStackTrace();

			}
		
		DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
		String convertido = fecha.format(fecha1);
		System.out.println(convertido);
		return convertido; 
	   
	  }

	/**
	 * Conversion de Date a String
	 * @param date
	 * @param format
     * @return
     */
	public static  String dateToStr(Date date, String format){
		DateFormat fecha = new SimpleDateFormat(format);
		String convertido = fecha.format(date);
		System.out.println(convertido);
		return convertido;
	}
	
	public static java.util.Date getPrimerDiaDelMes()
	  {
	    Calendar cal = Calendar.getInstance();
	    
	    cal.set(cal.get(1), cal.get(2), cal.getActualMinimum(5), cal.getMinimum(11), cal.getMinimum(12), cal.getMinimum(13));
	    
	    return cal.getTime();
	  }
	  
	public static java.util.Date getUltimoDiaDelMes()
	{
	    Calendar cal = Calendar.getInstance();
	    
	    cal.set(cal.get(1), cal.get(2), cal.getActualMaximum(5), cal.getMaximum(11), cal.getMaximum(12), cal.getMaximum(13));
	  
	    return cal.getTime();
	}
	
	public static String getFormatoMoneda(long monto){
		String importe;
		DecimalFormat formateador = new DecimalFormat("#,###.###");
		importe = formateador.format(monto);
		
		
		return importe;
	}
	


	//vibra y muestra un Toast
  public static void err_Log(Fragment view, String mensaje){
  	Vibrator vibrator =(Vibrator) view.getActivity().getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(200);
	    Toast toast1 = Toast.makeText(view.getActivity().getApplicationContext(),mensaje, Toast.LENGTH_SHORT);
	    toast1.show();    	
  }

  public static void err_Log(Activity view, String mensaje){
		Vibrator vibrator =(Vibrator) view.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(200);
		Toast toast1 = Toast.makeText(view.getApplicationContext(),mensaje, Toast.LENGTH_SHORT);
		toast1.show();
	}

	public static void err_Log(AppCompatActivity view, String mensaje){
		Vibrator vibrator =(Vibrator) view.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(200);
		Toast toast1 = Toast.makeText(view.getApplicationContext(),mensaje, Toast.LENGTH_SHORT);
		toast1.show();
	}
  
  public static void hiddenTeclado(Fragment view, EditText texto){
  	
	     InputMethodManager imm = (InputMethodManager)view.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	     imm.hideSoftInputFromWindow(texto.getWindowToken(), 0);
  }

  public static <T> void limpiarArrayList(ArrayList<T> List) {
		// TODO Auto-generated method stub
		for (int i= 0; i<List.size();i++){
			List.remove(i);
		}
		
	}

  public static void validaVacio(Context context, EditText control, String nombreControl)
			throws Exception {
		if (control.getText().toString().trim().equals("")) {
			control.requestFocus();
			throw new Exception(context.getString(R.string.MsjCampoVacio).replace(
					"$$", nombreControl)
			);
		}
	}

	/**
	 * Valida que por lo menos uno de los TextView's suministrados no estén vacios.
	 * @param context El context desde donde se invoca a la función.
	 * @param control Los controles que serán evaluados.
	 * @param nombreControl Los nombres de los controles que aparecerán en el mensaje.
	 * @throws Exception
	 */
  public static void validaVacio(Context context, EditText[] control, String[] nombreControl)
			throws Exception {
		boolean ok = false;
		String controles = "";
		for(int i = 0; i < control.length; i++)
		{
			if(!control[i].getText().toString().trim().equals("")) {
				ok = true;
			}

			if (i == 0)
				controles = nombreControl[i].trim();
			else
				controles += ", " + nombreControl[i].trim();
		}

		if (!ok) {
			String mensaje = context.getString(R.string.MsjCamposVacios);
			control[0].requestFocus();
			throw new Exception(mensaje.replace(
					"$$", controles)
			);
		}
	}

	/**
	* Valida que por lo menos uno de los TextView's suministrados no estén vacios ni contengan cero.
	* @param context El context desde donde se invoca a la función.
	* @param control Los controles que serán evaluados.
	* @param nombreControl Los nombres de los controles que aparecerán en el mensaje.
	* @throws Exception
	*/
	public static void validaVacioYCero(Context context, EditText[] control, String[] nombreControl)
		throws Exception {
	boolean ok = false;
	String controles = "";
	for(int i = 0; i < control.length; i++)
	{
		if(!control[i].getText().toString().trim().equals("") && !control[i].getText().toString().trim().equals("0")) {
			ok = true;
		}

		if (i == 0)
			controles = nombreControl[i].trim();
		else
			controles += ", " + nombreControl[i].trim();
	}

	if (!ok) {
		String mensaje = context.getString(R.string.MsjCamposVacios);
		control[0].requestFocus();
		throw new Exception(mensaje.replace(
				"$$", controles)
		);
	}
	}

	/**
	* Recibe una cadena numerica
	* @param valor
	* @param devolverNulo
	* @return
	*/
	public static Integer getIntVacio(String valor, boolean devolverNulo) {
	Integer retorno;
	if(devolverNulo)
		retorno = valor.trim().equals("") ? null : Integer.valueOf(valor);
	else
		retorno = valor.trim().equals("") ? 0 : Integer.valueOf(valor);
	return retorno;
	}

	/**
	* Recibe una cadena numerica
	* @param valor
	* @param devolverNulo
	* @return
	*/
	public static double getDoubleVacio(String valor, boolean devolverNulo) {
	double retorno;
	if(devolverNulo)
		retorno = valor.trim().equals("") ? null : Double.valueOf(valor);
	else
		retorno = valor.trim().equals("") ? 0 : Double.valueOf(valor);
	return retorno;
	}

	/**
	* Redondea un valor recibido como parametro
	* @param valor Número a redondear
	* @param decimales Cantidad de decimales que tendrá el valor redondeado
	* @return
	*/
	public static double redondear(double valor, int decimales) {
	double retorno;
	switch (decimales) {
		case 0:
			retorno = Math.round(valor);
			break;
		case 1:
			retorno = Math.round(valor * 10) / 10.0;
			break;
		case 2:
			retorno = Math.round(valor * 100) / 100.0;
			break;
		case 3:
			retorno = Math.round(valor * 1000) / 1000.0;
			break;
		default:
			retorno = Math.round(valor * 10000) / 10000.0;
			break;
	}
	return retorno;
	}



}
