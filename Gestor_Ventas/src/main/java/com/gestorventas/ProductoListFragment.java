package com.gestorventas;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.gestorventas.database.DatabaseProvider;
import com.gestorventas.tablas.TProducto;
import com.gestorventas.utils.Util;
import com.nolanlawson.supersaiyan.widget.SuperSaiyanScrollView;

/**
 * Created by Guille on 30/10/16.
 */

public class ProductoListFragment extends ListFragment {
    private View view;


    private SuperSaiyanScrollView scrollView;
    private ListView lista;
    private ImageButton imgButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.productos_list, container, false);

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
                    TProducto.COL_ROWID,
                    TProducto.COL_DESCRIPCION,
                    TProducto.COL_PRECIO_VENTA
            };
            int[] to = new int[] {
                    R.id.producto_id,
                    R.id.producto_nombre,
                    R.id.producto_precio
            };

            Cursor producto = getActivity().getContentResolver().query(DatabaseProvider.PRODUCTO_CONTENT_URI, from,
                    null, null,null);
            lista = getListView();

            scrollView = (SuperSaiyanScrollView) view.findViewById(R.id.scroll);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    getContext(), R.layout.productos_items, producto, from, to,0);

            adapter.notifyDataSetChanged();
            lista.setAdapter(adapter);
            imgButton = (ImageButton) view.findViewById(R.id.imageButton);
imgButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, new ProductoEdit(), "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
});


        }catch (Exception ex){
            Util.err_Log(this, ex.getMessage());
        }
    }
}
