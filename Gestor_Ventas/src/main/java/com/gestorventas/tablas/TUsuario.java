package com.gestorventas.tablas;

public class TUsuario {
	
	//nombre de la tabla
	public static final String NOMBRE_TABLA = "Usuario";
	
	//uri type
	public static final int SELECT_ALL = 100;
    public static final int SELECT_DISTINCT = 101;
	
	//columnas de la tabla
    public static final String COL_ROWID = "_idUsuario";
	public static final String COL_USUARIO = "usuario";
	public static final String COL_CLAVE = "clave";
	public static final String COL_VENDENDOR = "codigo_vendedor";
	
	//crear la tabla
	public static final String CREATE_TABLE = 
			"CREATE TABLE IF NOT EXISTS " + NOMBRE_TABLA + "("
			+ COL_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ COL_USUARIO + " TEXT NOT NULL, "
			+ COL_CLAVE + " TEXT NOT NULL,"
		    + COL_VENDENDOR + " INTEGER NOT NULL)";
	
	
	//eliminar la tabla
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + NOMBRE_TABLA + ";";

}
