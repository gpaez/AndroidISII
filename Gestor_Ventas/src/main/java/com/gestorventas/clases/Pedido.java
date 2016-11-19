package com.gestorventas.clases;

import android.content.ContentValues;

import com.gestorventas.tablas.TPedidoCab;

import java.util.Date;

import com.gestorventas.tablas.TPedidoDet;
import com.gestorventas.utils.*;

public class Pedido {
	private int idPedido;
	private int idCliente;
	private int condicionVenta;
	private Date fechaVenta;
	private int idVendedor;
	private double importeGravado;
	private double importeIva;
	private double importeTotal;
	
	//Detalle
	private int idProducto; 
	private float cantidad;
	private double precioVenta;
	private double impGravadoDet;
	private double impIvaDet;
	private double impTotalDet;

	private  String[] data;
	
	public Pedido() {
		super();
	}

	public Pedido(String[] data){
		this.data = data;
	}

	public void Cabecera(int idCliente, int condicionVenta, Date fechaVenta, int idVendedor){
		this.idCliente      = idCliente;
		this.condicionVenta = condicionVenta;
		this.idVendedor     = idVendedor;
		this.fechaVenta     = fechaVenta;

	}


    public void Detalle(int idPedido, int idProducto, double precioVenta, float cantidad){
        this.idPedido    = idPedido;
        this.idProducto  = idProducto;
        this.precioVenta = precioVenta;
        this.cantidad    = cantidad;
    }

	public ContentValues pedidoCabeceraMapperContentValues() {

		ContentValues cv = new ContentValues();
		cv.put(TPedidoCab.COL_CODIGO_CLIENTE, this.idCliente);
		cv.put(TPedidoCab.COL_FECHA_VENTA,  Util.dateToStr(this.fechaVenta,"dd/MM/yyyy"));
		cv.put(TPedidoCab.COL_CONDICION_VENTA, this.condicionVenta);
		cv.put(TPedidoCab.COL_CODIGO_VENDEDOR, this.idVendedor);

		return cv;
	}

    public ContentValues pedidoDetalleMapperContentValues(){
        ContentValues cv = new ContentValues();
        cv.put(TPedidoDet.COL_ROWID , this.idPedido);
        cv.put(TPedidoDet.COL_ID_PRODUCTO , this.idProducto);
        cv.put(TPedidoDet.COL_PRECIO , this.precioVenta);
        cv.put(TPedidoDet.COL_CANTIDAD , this.cantidad);

        return cv;
    }
	
	

}
