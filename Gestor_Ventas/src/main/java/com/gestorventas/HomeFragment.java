package com.gestorventas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gestorventas.utils.Util;
import com.special.ResideMenu.ResideMenu;
import com.gestorventas.R;


public class HomeFragment extends Fragment {

    private View parentView;
    private ResideMenu resideMenu;
    private Button btnHacerPedido;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);
        setUpViews();
        return parentView;
    }

    private void setUpViews() {
        final MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

        parentView.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        
        parentView.findViewById(R.id.btnHacerPedido).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try{
                   getFragmentManager()
                           .beginTransaction()
                            .replace(R.id.main_fragment, new PedidoClienteList(), "fragment")
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                    //Intent i = new Intent(getActivity(), PokemonActivity.class );
                    //startActivity(i);

                }catch (Exception ex){
                    Util.err_Log(getActivity(), ex.getMessage());
                }


            }
        });

    }

}
