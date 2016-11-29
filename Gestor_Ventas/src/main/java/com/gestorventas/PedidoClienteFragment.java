package com.gestorventas;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gestorventas.clases.Usuario;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import java.text.SimpleDateFormat;
import java.util.Date;



public class PedidoClienteFragment extends Fragment{

    private static final String KEY_PARAM = "codcliente";
    private static final String PARAM_DESC = "cliente";


    public static PedidoClienteFragment newInstance(String param) {
        PedidoClienteFragment fragment = new PedidoClienteFragment();
        fragment.setArguments(arguments(param));
        return fragment;

    }

    public static Bundle arguments(String param) {
        return new Bundler()
                .putString(KEY_PARAM, param)
                .get();
    }

    private View view;
    private TextView txtFecha;
    private TextView txtCliente;
    private Switch scVenta;
    private EditText edtFechaVenta;
    private EditText textDireccion;
    private EditText textRUC;
    private EditText textTelefono;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pedido_cliente_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int position  = FragmentPagerItem.getPosition(getArguments());
        txtCliente    = (TextView) view.findViewById(R.id.textCliente);
        txtFecha      = (TextView) view.findViewById(R.id.textFechaVenta);
        scVenta       = (Switch) view.findViewById(R.id.switch2);
        edtFechaVenta = (EditText) view.findViewById(R.id.editFechaVenta);

        txtCliente.setText(getArguments().getString(KEY_PARAM));

        String[] palabrasSeparadas = KEY_PARAM.split("-");

        txtFecha.setText("Fecha Actual");
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        edtFechaVenta.setText(formateador.format(new Date()));
        edtFechaVenta.setEnabled(false);
        //edtFechaVenta.setTextColor(R.color.azure);
        PedidoActivity.pedido.setFechaVenta(new Date());
        PedidoActivity.pedido.setIdVendedor(Usuario.VENDEDOR);

        scVenta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    PedidoActivity.pedido.setCondicionVenta(1);
                }else
                    PedidoActivity.pedido.setCondicionVenta(0);
            }
        });

    }

}
