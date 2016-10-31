package com.gestorventas.database;

import com.gestorventas.tablas.TCliente;
import com.gestorventas.tablas.TConfiguracion;
import com.gestorventas.tablas.TProducto;
import com.gestorventas.tablas.TUsuario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{
	private static final String DEBUG_TAG = "GestorVentas";
	private static final int DB_VERSION = 16;
    private static final String DB_NAME = "GestorVentas_Data";

    public DatabaseHandler(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) throws SQLiteException{
		// TODO Auto-generated method stub
		db.execSQL(TUsuario.CREATE_TABLE);
		db.execSQL(TConfiguracion.CREATE_TABLE);
		db.execSQL(TCliente.CREATE_TABLE);
		db.execSQL(TProducto.CREATE_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(DEBUG_TAG, "Actualizando la Base de Datos. Se perderan todos los datos. ["
	            + oldVersion + "]->[" + newVersion + "]");
		db.execSQL(TUsuario.DROP_TABLE);
		db.execSQL(TConfiguracion.DROP_TABLE);
		db.execSQL(TCliente.DROP_TABLE);
		db.execSQL(TProducto.DROP_TABLE);
		onCreate(db);
		
	}

}
