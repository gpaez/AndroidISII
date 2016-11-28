package com.gestorventas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

/**
 * Created by Guille on 31/10/16.
 */

public class PedidoCobroFragment extends Fragment {
    private EditText editSubTotal;
    private EditText editIVA;
    private EditText editTotal;
    private Button btnConfirmarVenta;
    private View view;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.pedido_cobro, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int position = FragmentPagerItem.getPosition(getArguments());
        editSubTotal = (EditText)view.findViewById(R.id.editSubTotal);
        editIVA      = (EditText)view.findViewById(R.id.editIVA);
        editTotal    = (EditText)view.findViewById(R.id.editTotal);
        btnConfirmarVenta = (Button)view.findViewById(R.id.btnConfirmarVenta);

        editTotal.setEnabled(false);
        editIVA.setEnabled(false);
        editSubTotal.setEnabled(false);
        btnConfirmarVenta.requestFocus();
    }

    public  void setTotales(String totales,
                                  String gravadas,
                                  String iva){
        this.editTotal.setText(totales);
    }
}
