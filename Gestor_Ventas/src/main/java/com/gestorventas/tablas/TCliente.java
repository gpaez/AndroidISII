package com.gestorventas.tablas;

public class TCliente {
	//nombre de la tabla
		public static final String NOMBRE_TABLA = "Cliente";
		
		//uri type
		public static final int SELECT_ALL = 104;
	    public static final int SELECT_DISTINCT = 105;
		
		//columnas de la tabla
	    public static final String COL_ROWID = "_id";
		public static final String COL_RAZON_SOCIAL = "razon_social";
	    public static final String COL_CONDICION_VENTA = "condicion_venta";
		public static final String COL_RUC = "ruc";
		public static final String COL_CEDULA = "cedula";
		public static final String COL_DIRECCION = "direccion";
		public static final String COL_TELEFONO = "telefono";
		public static final String COL_CODVENDEDOR = "codigo_vendedor";
		public static final String COL_LONGITUD = "longitud";
		public static final String COL_LATITUD = "latitud";
		
		//crear la tabla
		public static final String CREATE_TABLE = 
				"CREATE TABLE IF NOT EXISTS " + NOMBRE_TABLA + "("
				+ COL_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
				+ COL_RAZON_SOCIAL + " TEXT NOT NULL, "
				+ COL_CONDICION_VENTA + " TEXT NOT NULL, "
				+ COL_RUC + " TEXT,"
				+ COL_CEDULA + " TEXT, "
				+ COL_DIRECCION + " TEXT, "
				+ COL_TELEFONO + " TEXT, "
				+ COL_CODVENDEDOR + " INTEGER, "
				+ COL_LONGITUD + " TEXT, "
				+ COL_LATITUD + " TEXT "
						+ ")";
		
		
		//eliminar la tabla
		public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + NOMBRE_TABLA + ";";
}
