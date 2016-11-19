package com.gestorventas.tablas;

/**
 * Created by Guille on 18/11/16.
 */

public class TPedidoDet {

    //nombre de la tabla
    public static final String NOMBRE_TABLA = "Pedido_Det";

    //uri type
    public static final int SELECT_ALL = 110;
    public static final int SELECT_DISTINCT = 111;

    //columnas de la tabla
    public static final String COL_ROWID = "_id";
    public static final String COL_ID_PRODUCTO = "_id_prdducto";
    public static final String COL_PRECIO = "precio";
    public static final String COL_CANTIDAD = "cantidad";
    public static final String COL_TOTAL_VENTA = "total_venta";
    public static final String COL_TOTAL_IVA= "total_iva";
    public static final String COL_TOTAL_GRAVADA = "total_gravada";


    //crear la tabla
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + NOMBRE_TABLA + "("
                    + COL_ROWID + " INTEGER NOT NULL, "
                    + COL_ID_PRODUCTO + " INTEGER NOT NULL, "
                    + COL_PRECIO + " DOUBLE NOT NULL, "
                    + COL_CANTIDAD + " FLOAT NOT NULL, "
                    + COL_TOTAL_VENTA + " DOUBLE NOT NULL, "
                    + COL_TOTAL_IVA + " DOUBLE NOT NULL, "
                    + COL_TOTAL_GRAVADA + " DOUBLE NOT NULL, "
                    + " PRIMARY KEY ( "+COL_ROWID +","+COL_ID_PRODUCTO+")"
                    + ")";


    //eliminar la tabla
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + NOMBRE_TABLA + ";";

}
