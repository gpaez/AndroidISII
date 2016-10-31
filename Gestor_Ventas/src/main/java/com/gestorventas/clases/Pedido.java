package com.gestorventas.clases;

import java.util.Date;

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
	
	public Pedido() {
		super();
	}
	
	
	

}
