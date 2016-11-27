package com.gestorventas;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.gestorventas.clases.Producto;
import com.gestorventas.database.DatabaseProvider;
import com.gestorventas.tablas.TProducto;
import com.gestorventas.utils.Util;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import java.util.ArrayList;

/**
 * Created by Guille on 31/10/16.
 */

public class PedidoDetFragment extends Fragment{
    private View view;
    private Spinner cboProducto;
    private Button btnAgregar;
    private EditText editCantidad;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pedido_det_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try{
            int position = FragmentPagerItem.getPosition(getArguments());
            cboProducto  = (Spinner)view.findViewById(R.id.spinnerProducto);
            btnAgregar   = (Button)view.findViewById(R.id.btnAgregarProducto);
            editCantidad = (EditText)view.findViewById(R.id.editCantPedido);
        }catch (Exception ex){
            Util.err_Log(this, ex.getMessage());
        }


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregar(v);
            }
        });

        String[] from = new String[]{
                TProducto.COL_ROWID,
                TProducto.COL_DESCRIPCION,
                TProducto.COL_PRECIO_VENTA,
                TProducto.COL_EXISTENCIA
        };
        int[] to = new int[] {
                R.id.producto_id,
                R.id.producto_nombre,
                R.id.producto_precio,
                R.id.producto_existencia
        };
        Cursor producto = view.getContext()
                .getContentResolver()
                .query(DatabaseProvider.PRODUCTO_CONTENT_URI, from,
                       null, null,null);

         android.widget.SimpleCursorAdapter adapter = new android.widget.SimpleCursorAdapter(
                        getContext(), R.layout.productos_items, producto, from, to,0);

        adapter.notifyDataSetChanged();
        cboProducto.setAdapter(adapter);

    }

    private void agregar(View v) {

    }
}
