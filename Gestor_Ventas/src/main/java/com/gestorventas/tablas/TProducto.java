package com.gestorventas.tablas;

/**
 * Created by Guille on 29/10/16.
 */

public class TProducto {
    //nombre de la tabla
    public static final String NOMBRE_TABLA = "Producto";

    //uri type
    public static final int SELECT_ALL = 106;
    public static final int SELECT_DISTINCT = 107;

    //columnas de la tabla
    public static final String COL_ROWID = "_id";
    public static final String COL_DESCRIPCION = "descripcion";
    public static final String COL_PRECIO_VENTA = "precio_venta";
    public static final String COL_EXISTENCIA= "existencia";

    //crear la tabla
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + NOMBRE_TABLA + "("
                    + COL_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + COL_DESCRIPCION + " TEXT NOT NULL, "
                    + COL_PRECIO_VENTA + " DOUBLE NOT NULL, "
                    + COL_EXISTENCIA + " FLOAT NOT NULL"
                    + ")";


    //eliminar la tabla
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + NOMBRE_TABLA + ";";
}
