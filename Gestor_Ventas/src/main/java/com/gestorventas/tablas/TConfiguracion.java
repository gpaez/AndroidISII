package com.gestorventas.tablas;

public class TConfiguracion {
	
	//Nombre de la tabla
	public static final String NOMBRE_TABLA = "Configuracion";
	
	//Uri Type
	public static final int SELECT_ALL = 102;
	public static final int SELECT_DISTINCT = 103;
	
	//Columnas
	public static final String COL_ROWID = "_id";
	public static final String COL_NAMESPACE = "NameSpace";
	public static final String COL_URL = "URL";
	
	//crear la tabla
	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + NOMBRE_TABLA + " (" +
			COL_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
			COL_NAMESPACE + " TEXT NOT NULL, " +
			COL_URL + " TEXT NOT NULL );";
	//Eliminar la tabla
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + NOMBRE_TABLA + ";";
}
