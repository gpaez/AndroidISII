package com.gestorventas.clases;

import com.gestorventas.tablas.TUsuario;

import android.content.ContentValues;

public class Usuario {
	
	private String usuario;
	private String clave;
	private int idUsuario;
	private int codVendedor;
	public static int VENDEDOR;
	
	
	public Usuario(String usuario, String clave, int codVendedor) {
		super();
		this.usuario = usuario;
		this.clave = clave;
		this.codVendedor = codVendedor;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public ContentValues clienteMapperContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(TUsuario.COL_USUARIO, this.usuario);
        cv.put(TUsuario.COL_CLAVE, this.clave);
        cv.put(TUsuario.COL_VENDENDOR, this.codVendedor);
        return cv;
    }
}
