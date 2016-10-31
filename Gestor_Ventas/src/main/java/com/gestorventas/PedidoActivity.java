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

    public static void startActivity(Context context, PedidoTab pedidoTab) {
        try {
            Intent intent = new Intent(context, PedidoActivity.class);
            intent.putExtra(KEY_PEDIDO, pedidoTab.name());
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

            FragmentPagerItems pages = new FragmentPagerItems(this);
            //for (int titleResId : pedidoTab.tabs()) {
                pages.add(FragmentPagerItem.of(getString(2131099710), PedidoClienteFragment.class));
                pages.add(FragmentPagerItem.of(getString(2131099711), PedidoDetFragment.class));
                pages.add(FragmentPagerItem.of(getString(2131099712), PedidoCobroFragment.class));
            //}

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
