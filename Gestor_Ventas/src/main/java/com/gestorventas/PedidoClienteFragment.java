package com.gestorventas;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;




public class PedidoClienteFragment extends Fragment{

    private View view;
    private TextView txtFecha;
    private TextView txtCliente;
    private Switch scVenta;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pedido_cliente_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int position = FragmentPagerItem.getPosition(getArguments());
        txtCliente   = (TextView) view.findViewById(R.id.textCliente);
        txtFecha     = (TextView) view.findViewById(R.id.textFechaVenta);
        scVenta      = (Switch) view.findViewById(R.id.switch2);


    }

}
