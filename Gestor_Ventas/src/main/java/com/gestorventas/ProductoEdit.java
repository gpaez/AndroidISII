package com.gestorventas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gestorventas.clases.Producto;
import com.gestorventas.database.DatabaseProvider;
import com.gestorventas.utils.Util;

/**
 * Created by Guille on 30/10/16.
 */

public class ProductoEdit extends Fragment {
    private View view;
    private EditText editCodigo;
    private EditText editDescripcion;
    private EditText editPrecio;
    private EditText editExistencia;
    private Button btnAceptar;
    private Button btnCancelar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.producto_edit, container, false);
        try {
            editCodigo      = (EditText) view.findViewById(R.id.editCodigoProducto);
            editDescripcion = (EditText) view.findViewById(R.id.editDescripcionProducto);
            editExistencia  = (EditText) view.findViewById(R.id.editExistencia);
            editPrecio      = (EditText) view.findViewById(R.id.editPrecioProducto);

            editCodigo.setEnabled(false);
            editDescripcion.requestFocus();

            btnAceptar      = (Button) view.findViewById(R.id.btnAceptarProducto);
            btnCancelar     = (Button) view.findViewById(R.id.btnCancelarProducto);

            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    aceptar(v);
                }
            });
            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelar(v);
                }
            });
        }catch (Exception ex){
            Util.err_Log(this,ex.getMessage());
        }




        return view;
    }

    private void aceptar(View v){
        try {
            validaciones();
            Producto producto = new Producto(editDescripcion.getText().toString(),
                                             Double.parseDouble(editPrecio.getText().toString()),
                                             Float.parseFloat(editExistencia.getText().toString()));
            getActivity().getContentResolver().insert(DatabaseProvider.PRODUCTO_CONTENT_URI,
                                    producto.productoMapperContentValues());
            Toast.makeText(getContext(),
                    getClass().getName() + " Registro Completo!!!",
                    Toast.LENGTH_LONG).show();
            cancelar(v);

        }catch (Exception ex){
            Log.w(getString(R.string.app_name), ex.getMessage(), ex);
            Toast.makeText(getContext(),
                    ex.getClass().getName() + " " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

    }

    private void cancelar(View v) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, new ProductoListFragment(), "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
    private void validaciones() throws Exception{
        Util.validaVacio(getContext(),
                editDescripcion,
                ">>Favor ingrese la descripcion del producto");
        Util.validaVacio(getContext(),
                editPrecio,
                ">>Favor ingrese el precio de venta");
        Util.validaVacio(getContext(),
                editExistencia,
                ">>Favor ingrese la existencia");
    }
}
