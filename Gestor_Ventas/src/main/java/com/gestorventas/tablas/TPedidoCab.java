package com.gestorventas.tablas;



public class TPedidoCab {
    //nombre de la tabla
    public static final String NOMBRE_TABLA = "Pedido_Cab";

    //uri type
    public static final int SELECT_ALL = 108;
    public static final int SELECT_DISTINCT = 109;

    //columnas de la tabla
    public static final String COL_ROWID = "_id";
    public static final String COL_CODIGO_CLIENTE = "codigo_cliente";
    public static final String COL_FECHA_VENTA= "fecha_venta";
    public static final String COL_CONDICION_VENTA = "codicion_venta";
    public static final String COL_CODIGO_VENDEDOR = "codigo_vendedor";
    public static final String COL_TOTAL_VENTA = "total_venta";
    public static final String COL_TOTAL_IVA= "total_iva";
    public static final String COL_TOTAL_GRAVADA = "total_gravada";


    //crear la tabla
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + NOMBRE_TABLA + "("
                    + COL_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + COL_CODIGO_CLIENTE + " INTEGER NOT NULL, "
                    + COL_FECHA_VENTA + " DATE NOT NULL, "
                    + COL_CONDICION_VENTA + " INTEGER NOT NULL, "
                    + COL_CODIGO_VENDEDOR + " INTEGER NOT NULL, "
                    + COL_TOTAL_VENTA + " DOUBLE NOT NULL, "
                    + COL_TOTAL_IVA + " DOUBLE NOT NULL, "
                    + COL_TOTAL_GRAVADA + " DOUBLE NOT NULL"
                    + ")";


    //eliminar la tabla
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + NOMBRE_TABLA + ";";

}
