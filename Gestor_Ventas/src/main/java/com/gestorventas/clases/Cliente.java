package com.gestorventas.clases;

import com.gestorventas.tablas.TCliente;



import android.content.ContentValues;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
	private String razonSocial;
	private String direccion;
	private String ruc;
	private String cedula;
	private String telefono;
	private int codVendedor;
	private String longitud;
	private String latitud;
	private String condicionVenta;
	private int codigo;
	private String tipoVenta;



	private  String[] data;

    public  Cliente(){

	}

	public Cliente(String[] data) {
		this.data = data;
	}

	public Cliente(String razonSocial, String condicionVenta, String direccion, String ruc, String cedula, String telefono,
                   int codVendedor, String longitud, String latitud) {
		super();
		this.razonSocial = razonSocial;
		this.condicionVenta = condicionVenta;
		this.direccion = direccion;
		this.ruc = ruc;
		this.cedula = cedula;
		this.telefono = telefono;
		this.codVendedor = codVendedor;
		this.longitud = longitud;
		this.latitud = latitud;


	}


	public Cliente(String razonSocial, String direccion, String ruc, String cedula, String Telefono){
		data = new String[]{
				razonSocial,
				direccion ,
				ruc ,
				cedula ,
				telefono
		};
	}

	public ContentValues clienteMapperContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(TCliente.COL_RAZON_SOCIAL, this.razonSocial);
		cv.put(TCliente.COL_CONDICION_VENTA, this.condicionVenta);
        cv.put(TCliente.COL_DIRECCION, this.direccion);
        cv.put(TCliente.COL_RUC, this.ruc);
        cv.put(TCliente.COL_CEDULA, this.cedula);
        cv.put(TCliente.COL_TELEFONO, this.telefono);
        cv.put(TCliente.COL_CODVENDEDOR, this.codVendedor);
        cv.put(TCliente.COL_LONGITUD, this.longitud);
        cv.put(TCliente.COL_LATITUD, this.latitud);
        return cv;
    }
	
	
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public int getCodVendedor() {
		return codVendedor;
	}
	public void setCodVendedor(int codVendedor) {
		this.codVendedor = codVendedor;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String[] getData(){
		return this.data;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getTipoVenta() {
		return tipoVenta;
	}
	public void setTipoVenta(String tipoVenta) {
		this.tipoVenta = tipoVenta;
	}


}
