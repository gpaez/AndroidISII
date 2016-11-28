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

    public String getDescrProducto() {
        return descrProducto;
    }

    public void setDescrProducto(String descrProducto) {
        this.descrProducto = descrProducto;
    }

    private String descrProducto;

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

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getCondicionVenta() {
        return condicionVenta;
    }

    public void setCondicionVenta(int condicionVenta) {
        this.condicionVenta = condicionVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public double getImporteGravado() {
        return importeGravado;
    }

    public void setImporteGravado(double importeGravado) {
        this.importeGravado = importeGravado;
    }

    public double getImporteIva() {
        return importeIva;
    }

    public void setImporteIva(double importeIva) {
        this.importeIva = importeIva;
    }

    public double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getImpGravadoDet() {
        return impGravadoDet;
    }

    public void setImpGravadoDet(double impGravadoDet) {
        this.impGravadoDet = impGravadoDet;
    }

    public double getImpIvaDet() {
        return impIvaDet;
    }

    public void setImpIvaDet(double impIvaDet) {
        this.impIvaDet = impIvaDet;
    }

    public double getImpTotalDet() {
        return impTotalDet;
    }

    public void setImpTotalDet(double impTotalDet) {
        this.impTotalDet = impTotalDet;
    }
	
	

}
