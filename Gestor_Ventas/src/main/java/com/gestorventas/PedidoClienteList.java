package com.gestorventas;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.gestorventas.clases.Cliente;
import com.gestorventas.clases.ClienteAdapter;
import com.gestorventas.clases.ClienteHelper;
import com.gestorventas.clases.Pedido;
import com.gestorventas.database.DatabaseProvider;
import com.gestorventas.database.PocketMonster;
import com.gestorventas.database.PocketMonsterAdapter;
import com.gestorventas.database.PocketMonsterHelper;
import com.gestorventas.tablas.TCliente;
import com.gestorventas.utils.Util;
import com.nolanlawson.supersaiyan.SectionedListAdapter;
import com.nolanlawson.supersaiyan.Sectionizers;
import com.nolanlawson.supersaiyan.widget.SuperSaiyanScrollView;

import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Created by Guille on 28/10/16.
 */

public class PedidoClienteList extends ListFragment  {

    private View  view;
    SimpleCursorAdapter adapter;


    private SuperSaiyanScrollView scrollView;
    private ListView lista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.pedido_cliente, container, false);

        }catch (Exception ex){
            Util.err_Log(this, ex.getMessage());
        }

        return  view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try{
            String[] from = new String[] {
                    TCliente.COL_ROWID,
                    TCliente.COL_RAZON_SOCIAL
            };
            int[] to = new int[] {
                    R.id.cliente_id,
                    R.id.cliente_nombre
            };

            Cursor cliente = getActivity().getContentResolver().query(DatabaseProvider.CLIENTE_CONTENT_URI, from,
                    null, null,null);
            lista = getListView();

            scrollView = (SuperSaiyanScrollView) view.findViewById(R.id.scroll);
             adapter = new SimpleCursorAdapter(
                    getContext(), R.layout.pedido_cliente_item, cliente, from, to,0);

            adapter.notifyDataSetChanged();
            lista.setAdapter(adapter);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        PedidoTab pedidoTab = PedidoTab.values()[0];
                        String codigo = ((TextView) view.findViewById(R.id.cliente_id)).getText().toString();
                        String nombre = ((TextView) view.findViewById(R.id.cliente_nombre)).getText().toString();
                        pedidoTab.setCliente(codigo + " - " + nombre);
                        pedidoTab.startActivity(getActivity());

                        PedidoActivity.pedido = new Pedido();
                    }catch (Exception ex){
                        Util.err_Log(PedidoClienteList.this, ex.getMessage());
                    }

                }
            });



        }catch (Exception ex){
            Util.err_Log(this, ex.getMessage());
        }

    }


}
