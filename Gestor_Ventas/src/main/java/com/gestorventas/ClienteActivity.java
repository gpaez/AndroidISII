package com.gestorventas;

import java.util.ArrayList;
import java.util.List;

import com.gestorventas.clases.Cliente;
import com.gestorventas.utils.Util;
import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Guille on 21/10/16.
 */

public class ClienteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.table);

            TableFixHeaders tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
            BaseTableAdapter baseTableAdapter = new ClienteAdapter(this);
            tableFixHeaders.setAdapter(baseTableAdapter);
        }catch (Exception ex){
            Util.err_Log( this,ex.getMessage());
        }
    }

    private class ClientesTipos{
        private final String nombre;
        private final List<Cliente> list;

        public ClientesTipos(String nombre) {
            this.nombre = nombre;
            this.list = new ArrayList<Cliente>();
        }

        public int size(){
            return list.size();
        }

        public Cliente get(int i){
            return list.get(i);

        }
    }

    public class ClienteAdapter extends BaseTableAdapter{

        private final ClientesTipos tipos[];
        private final String headers[]={
                "Razón Social",
                "Dirección",
                "RUC",
                "Cedula",
                "Telefono",
        };

        private final int[] widths = {
                200,200,80,80,80,
        };

        private final float density;

        public ClienteAdapter(Context context){
            tipos = new ClientesTipos[]{
                    new ClientesTipos("Contado"),
                    new ClientesTipos("Credito"),
            };

            density = context.getResources().getDisplayMetrics().density;

            tipos[0].list.add(new Cliente("Despensa Mucho mas","Contado","RI 18 Pitiantua","4840285-0","4840285","1",1,"0","0"));
            tipos[0].list.add( new Cliente("Despensa Mucho mas","Contado","RI 18 Pitiantua","4840285-0","4840285","1",1,"0","0"));
            tipos[0].list.add( new Cliente("Despensa Mucho mas","Contado","RI 18 Pitiantua","4840285-0","4840285","1",1,"0","0"));
            tipos[0].list.add( new Cliente("Despensa Mucho mas","Contado","RI 18 Pitiantua","4840285-0","4840285","1",1,"0","0"));
            tipos[0].list.add( new Cliente("Despensa Mucho mas","Contado","RI 18 Pitiantua","4840285-0","4840285","1",1,"0","0"));
            tipos[1].list.add( new Cliente("Despensa Mucho mas","Contado","RI 18 Pitiantua","4840285-0","4840285","1",1,"0","0"));
            tipos[1].list.add( new Cliente("Despensa Mucho mas","Contado","RI 18 Pitiantua","4840285-0","4840285","1",1,"0","0"));
            tipos[1].list.add( new Cliente("Despensa Mucho mas","Contado","RI 18 Pitiantua","4840285-0","4840285","1",1,"0","0"));
            tipos[1].list.add( new Cliente("Despensa Mucho mas","Contado","RI 18 Pitiantua","4840285-0","4840285","1",1,"0","0"));

        }
        @Override
        public int getRowCount(){
            return 14;
        }

        @Override
        public int getColumnCount(){
            return 6;
        }

        @Override
        public View getView(int row, int column, View convertView, ViewGroup parent) {
            final View view;
            switch (getItemViewType(row, column)) {
                case 0:
                    view = getFirstHeader(row, column, convertView, parent);
                    break;
                case 1:
                    view = getHeader(row, column, convertView, parent);
                    break;
                case 2:
                    view = getFirstBody(row, column, convertView, parent);
                    break;
                case 3:
                    view = getBody(row, column, convertView, parent);
                    break;
                case 4:
                    view = getTipoView(row, column, convertView, parent);
                    break;
                default:
                    throw new RuntimeException("wtf?");
            }
            return view;
        }

        private View getFirstHeader(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table_header_first, parent, false);
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[0]);
            return convertView;
        }

        private View getHeader(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table_header, parent, false);
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[column + 1]);
            return convertView;
        }

        private View getFirstBody(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table_first, parent, false);
            }
            convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).getData()[column + 1]);
            return convertView;
        }

        private View getBody(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table, parent, false);
            }
            convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).getData()[column + 1]);
            return convertView;
        }

        private View getTipoView(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table_family, parent, false);
            }
            final String string;
            if (column == -1) {
                string = getTipos(row).nombre;
            } else {
                string = "";
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(string);
            return convertView;
        }

        @Override
        public int getWidth(int column) {
            return Math.round(widths[column + 1] * density);
        }

        @Override
        public int getHeight(int row) {
            final int height;
            if (row == -1) {
                height = 35;
            } else if (isTipo(row)) {
                height = 25;
            } else {
                height = 45;
            }
            return Math.round(height * density);
        }


        @Override
        public int getItemViewType(int row, int column) {
            final int itemViewType;
            if (row == -1 && column == -1) {
                itemViewType = 0;
            } else if (row == -1) {
                itemViewType = 1;
            } else if (isTipo(row)) {
                itemViewType = 4;
            } else if (column == -1) {
                itemViewType = 2;
            } else {
                itemViewType = 3;
            }
            return itemViewType;
        }

        private boolean isTipo(int row) {
            int family = 0;
            while (row > 0) {
                row -= tipos[family].size() + 1;
                family++;
            }
            return row == 0;
        }

        private ClientesTipos getTipos(int row) {
            int tipo = 0;
            while (row >= 0) {
                row -= tipos[tipo].size() + 1;
                tipo++;
            }
            return tipos[tipo - 1];
        }

        private Cliente getDevice(int row) {
            int family = 0;
            while (row >= 0) {
                row -= tipos[family].size() + 1;
                family++;
            }
            family--;
            return tipos[family].get(row + tipos[family].size());
        }

        @Override
        public int getViewTypeCount() {
            return 5;
        }
    }
}
