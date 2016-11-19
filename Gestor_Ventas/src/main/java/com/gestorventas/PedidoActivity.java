package com.gestorventas;

import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gestorventas.utils.Util;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;





public class PedidoActivity extends AppCompatActivity{
    private static final String KEY_PEDIDO = "Pedido";
    private static final String CLIENTE = "Cliente";

    public static void startActivity(Context context, PedidoTab pedidoTab) {
        try {
            Intent intent = new Intent(context, PedidoActivity.class);
            intent.putExtra(KEY_PEDIDO, pedidoTab.name());
            intent.putExtra(CLIENTE, pedidoTab.cliente);
            context.startActivity(intent);
        }catch (Exception ex){
            //Util.err_Log(this, ex.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.pedido_activity);

            PedidoTab pedidoTab = getPedidoTab();



            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(pedidoTab.titleResId);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            ViewGroup tab = (ViewGroup) findViewById(R.id.tab);
            tab.addView(LayoutInflater.from(this).inflate(pedidoTab.layoutResId, tab, false));

            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
            pedidoTab.setup(viewPagerTab);
            int[] titleResId = pedidoTab.tabs();

            FragmentPagerItems pages = new FragmentPagerItems(this);
            pages.add(FragmentPagerItem.of(getString(titleResId[0]),
                                           PedidoClienteFragment.class,
                                           PedidoClienteFragment.arguments(pedidoTab.cliente)

            ));
            pages.add(FragmentPagerItem.of(getString(titleResId[1]), PedidoDetFragment.class));
            pages.add(FragmentPagerItem.of(getString(titleResId[2]), PedidoCobroFragment.class));

            FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                    getSupportFragmentManager(), pages);





            viewPager.setAdapter(adapter);
            viewPagerTab.setViewPager(viewPager);
        }catch (Exception ex){
            Util.err_Log(this, ex.getMessage());
        }

    }

    private PedidoTab getPedidoTab() {
        return PedidoTab.valueOf(getIntent().getStringExtra(KEY_PEDIDO));
    }



}
