package com.gestorventas;

import android.content.Context;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

public enum PedidoTab {


  INDICATOR_TRICK1(R.string.pedido_title_indicator, R.layout.pedido_indicator);



  public final int titleResId;
  public final int layoutResId;
  public String cliente;

  PedidoTab(int titleResId, int layoutResId) {
    this.titleResId = titleResId;
    this.layoutResId = layoutResId;
  }

  public static int[] tab10() {
    return new int[] {
        R.string.demo_tab_1,
        R.string.demo_tab_2,
        R.string.demo_tab_3

    };
  }

  public static int[] tab3() {
    return new int[] {
        R.string.demo_tab_1,
        R.string.demo_tab_2,
        R.string.demo_tab_3
    };
  }

  public void startActivity(Context context) {
    PedidoActivity.startActivity(context, this);
  }

  public void setup(final SmartTabLayout layout) {
    //Do nothing.
  }

  public int[] tabs() {
    return tab10();
  }

  public void setCliente(String cliente){
    this.cliente = cliente;
  }
}
