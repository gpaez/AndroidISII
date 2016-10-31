package com.gestorventas.clases;

import android.content.ContentValues;

import com.gestorventas.tablas.TProducto;
import com.nolanlawson.supersaiyan.util.StringUtil;

/**
 * Created by Guille on 29/10/16.
 */

public class Producto {

    private int idPrododucto;
    private String descripcion;
    private Double precio;
    private Float existencia;

    public Producto() {
    }

    public Producto(String descripcion, Double precio, Float existencia) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.existencia = existencia;
    }

    public int getIdPrododucto() {
        return idPrododucto;
    }

    public void setIdPrododucto(int idPrododucto) {
        this.idPrododucto = idPrododucto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Float getExistencia() {
        return existencia;
    }

    public void setExistencia(Float existencia) {
        this.existencia = existencia;
    }

    public ContentValues productoMapperContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(TProducto.COL_DESCRIPCION, this.descripcion);
        cv.put(TProducto.COL_PRECIO_VENTA, this.precio);
        cv.put(TProducto.COL_EXISTENCIA, this.existencia);
        return cv;
    }




}
