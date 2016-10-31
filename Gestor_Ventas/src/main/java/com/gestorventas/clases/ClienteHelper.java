package com.gestorventas.clases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.gestorventas.database.DatabaseProvider;
import com.gestorventas.tablas.TCliente;


public class ClienteHelper {
    public static List<Cliente> readInClientes(Context context) {
        List<Cliente> clienteList = new ArrayList<Cliente>();
        try {
            String[] projection = new String[]{
                    TCliente.COL_ROWID,
                    TCliente.COL_RAZON_SOCIAL,
                    TCliente.COL_CONDICION_VENTA

            };
            Cursor cursor = context.getContentResolver().query(DatabaseProvider.CLIENTE_CONTENT_URI, projection,
                    null, null,null);
            if (cursor.moveToFirst()) {
                do {
                    Cliente cliente = new Cliente();
                    cliente.setCodigo(cursor.getInt(0));
                    cliente.setRazonSocial(cursor.getString(1));
                    cliente.setTipoVenta(cursor.getString(2));
                    clienteList.add(cliente);

                } while (cursor.moveToNext());
            }
        }catch (Exception ex){

        }
        return  clienteList;
    }
}
