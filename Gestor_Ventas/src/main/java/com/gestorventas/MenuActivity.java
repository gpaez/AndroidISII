package com.gestorventas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.KeyEvent;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import android.widget.Toast;


public class MenuActivity extends FragmentActivity implements View.OnClickListener{

    private ResideMenu resideMenu;
    private MenuActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemCliente;
    private ResideMenuItem itemAdress;
    private ResideMenuItem itemSettings;
    private ResideMenuItem itemCart;
    

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        setUpMenu();
        if( savedInstanceState == null )
            changeFragment(new HomeFragment());
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     "Inicio");
        itemCliente = new ResideMenuItem(this, R.drawable.icon_profile,  "Clientes");
        itemAdress   = new ResideMenuItem(this, R.drawable.icon_cart, "Producto");
        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "Ajustes");
        //itemCart     = new ResideMenuItem(this, R.drawable.icon_cart,"Carrito");
        

        itemHome.setOnClickListener(this);
        itemCliente.setOnClickListener(this);
        itemAdress.setOnClickListener(this);
        itemSettings.setOnClickListener(this);
        //itemCart.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCliente, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemAdress, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);
        //resideMenu.addMenuItem(itemCart, ResideMenu.DIRECTION_LEFT);

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome){
            changeFragment(new HomeFragment());
        }else if (view == itemCliente){
            changeFragment( new ClienteTableFragment());
        }else if (view == itemAdress){
            changeFragment(new ProductoListFragment());
        }else if (view == itemSettings){
            changeFragment(new SettingsFragment());
        }
        /*else if (view == itemCart){
        	changeFragment(new ProfileFragment());
        }*/

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
    	 @Override
        public void openMenu() {
            //Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
           // Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };


    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Fragment mFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment);

            if (mFragment instanceof ClienteEdit)
                changeFragment(new ClienteTableFragment());

            if (mFragment instanceof ClienteTableFragment)
                changeFragment(new HomeFragment());
            if (mFragment instanceof ProductoListFragment)
                changeFragment(new HomeFragment());
            if (mFragment instanceof ProductoEdit)
                changeFragment(new ProductoListFragment());

            if (mFragment instanceof PedidoClienteList)
                changeFragment(new HomeFragment());



            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
