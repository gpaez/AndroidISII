package com.gestorventas;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.gestorventas.clases.Pedido;
import com.gestorventas.clases.Producto;
import com.gestorventas.database.DatabaseProvider;
import com.gestorventas.tablas.TPedidoDet;
import com.gestorventas.tablas.TProducto;
import com.gestorventas.utils.Util;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Guille on 31/10/16.
 */

public class PedidoDetFragment extends ListFragment implements AdapterView.OnItemSelectedListener{
    private View view;
    private Spinner cboProducto;
    private Button btnAgregar;
    private NumberPicker editCantidad;
    private int existencia;
    private String producto_id;
    private Double precioVenta;
    private SimpleAdapter adapter;
    private ListView listPedido;
    private String desProducto;
    private ArrayList<HashMap<String, String>> detalle;
    private HashMap<String, String> map;
    private EditText editTotalVenta;
    private Button btnConfirmarVenta;
    private Double totalVenta;


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
            editCantidad = (NumberPicker) view.findViewById(R.id.editCantidad);
            editCantidad.setMaxValue(0);
            editTotalVenta = (EditText)view.findViewById(R.id.editTotalVenta);
            editTotalVenta.setEnabled(false);
            btnConfirmarVenta = (Button)view.findViewById(R.id.btnConfirmarVenta);
            btnConfirmarVenta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmarVenta(v);
                }
            });
            totalVenta = 0.0;
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
            final Cursor producto = view.getContext()
                    .getContentResolver()
                    .query(DatabaseProvider.PRODUCTO_CONTENT_URI, from,
                            null, null,null);

            android.widget.SimpleCursorAdapter adapter = new android.widget.SimpleCursorAdapter(
                    getContext(), R.layout.productos_items, producto, from, to,0);

            adapter.notifyDataSetChanged();
            cboProducto.setAdapter(adapter);
            cboProducto.setSelection(-1);
            listPedido = getListView();

            cboProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    producto_id = String.valueOf(id);
                    editCantidad.setMaxValue(0);
                    obtenerDatosProducto(id);
                    editCantidad.setMaxValue(existencia);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            detalle = new ArrayList<HashMap<String, String>>();



;
        }catch (Exception ex){
            Util.err_Log(this, ex.getMessage());
        }





    }

    private void confirmarVenta(View v) {
        try {
            Util.alerta(this, "Pedido", "Su pedido se ha grabado correctamente");
            Intent intent = new Intent(getActivity(), MenuActivity.class);
            intent.putExtra(SignInActivity.EXTRAS_ENDLESS_MODE, true);
            startActivity(intent);
        }catch (Exception ex){
            Util.err_Log(getParentFragment(), ex.getMessage());
        }
        //RegistrarCabecera;
       // Pedido pedido = new Pedido();
        //pedido.Cabecera(PedidoActivity.pedido.getIdCliente(),
        //                PedidoActivity.pedido.getCondicionVenta(),
        //                PedidoActivity.pedido.getFechaVenta(),
        //                PedidoActivity.pedido.getIdVendedor());
        //getActivity().getContentResolver().insert(DatabaseProvider.PEDIDO_CABECERA_CONTENT_URI,
        //        pedido.pedidoCabeceraMapperContentValues());
    }

    private void obtenerDatosProducto(long id) {
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
        final Cursor producto = view.getContext()
                .getContentResolver()
                .query(DatabaseProvider.PRODUCTO_CONTENT_URI, from,
                        null, null,null);
        if (producto.moveToFirst()){
            do{
                if (id == producto.getInt(0)){
                    existencia  = producto.getInt(3);
                    precioVenta = producto.getDouble(2);
                    desProducto = producto.getString(1);


                }
            }while (producto.moveToNext());
        }
    }

    private void agregar(View v) {

        boolean existe = false;

        if (editCantidad.getValue() == 0){
            Util.alerta(this,"Cantidad","Ingrese cantidad a vender");
            editCantidad.requestFocus();
            return;
        }

        if ( editCantidad.getValue()>   existencia){
            Util.alerta(this,"Stock","Existencia insuficiente, verifique");
            return;
        }
        if (detalle.size()>0){
            for (int i=0; i<detalle.size();i++){
                if (detalle.get(i).get(TPedidoDet.COL_ID_PRODUCTO).equals(producto_id)){
                    existe = true;
                }
            }
            if (existe){
                Util.alerta(this,"Producto", "Producto ya existe,verifique");
                return;
            }
        }



        //todos los productos iva 10%
        Double iva     = (editCantidad.getValue() * precioVenta)/11;
        Double gravado = (editCantidad.getValue() * precioVenta)-iva;
        Double total   = (editCantidad.getValue() * precioVenta);
        map = new HashMap<String, String>();
        map.put(TPedidoDet.COL_ID_PRODUCTO,producto_id);
        map.put(TProducto.COL_DESCRIPCION,desProducto);
        map.put(TPedidoDet.COL_PRECIO, String.valueOf(precioVenta));
        map.put(TPedidoDet.COL_CANTIDAD, String.valueOf(editCantidad.getValue()));
        map.put(TPedidoDet.COL_TOTAL_VENTA, String.valueOf(total));
        detalle.add(map);
        totalVenta = totalVenta + total;


        String[] from = new String[]{
                TPedidoDet.COL_ID_PRODUCTO,
                TProducto.COL_DESCRIPCION,
                TPedidoDet.COL_PRECIO,
                TPedidoDet.COL_CANTIDAD,
                TPedidoDet.COL_TOTAL_VENTA
        };
        int[] to = new int[] {
                R.id.textIDProdPed,
                R.id.textDescripcion,
                R.id.TextPrecio,
                R.id.TextCantidad,
                R.id.textTotal
        };
        adapter = new SimpleAdapter(
               getContext(),detalle, R.layout.pedido_det_items, from, to);

        listPedido.setAdapter(null);
        listPedido.setAdapter(adapter);
        editTotalVenta.setText(String.valueOf(totalVenta));



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
