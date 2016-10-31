package com.gestorventas;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;


import com.gestorventas.clases.Cliente;
import com.gestorventas.database.DatabaseProvider;
import com.gestorventas.tablas.TCliente;
import com.gestorventas.utils.Util;
import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

import java.util.ArrayList;
import java.util.List;

public class ClienteTableFragment extends Fragment {

    private class ClienteTypes {
        private final String name;
        private final List<Cliente> list;

        ClienteTypes(String name) {
            this.name = name;
            list = new ArrayList<Cliente>();
        }

        public int size() {
            return list.size();
        }

        public Cliente get(int i) {
            return list.get(i);
        }
    }
    private ImageButton imgButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clientes_list, container, false);
        TableFixHeaders tableFixHeaders = (TableFixHeaders)  view.findViewById(R.id.tableCliente);
        BaseTableAdapter baseTableAdapter = new ClienteAdapter(this.getContext());
        tableFixHeaders.setAdapter(baseTableAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imgButton = (ImageButton) getView().findViewById(R.id.imageButton);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new ClienteEdit(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

    }


    public class ClienteAdapter extends BaseTableAdapter {

        private final ClienteTypes familys[];
        private final String headers[] = {
                "Razon Social",
                "Direccion",
                "RUC",
                "Cedula",
                "Telefono",
        };

        private final int[] widths = {
                120,
                100,
                90,
                90,
                90,
        };
        private final float density;

        public ClienteAdapter(Context context) {
            int pos;
            familys = new ClienteTypes[]{
                    new ClienteTypes("Contado"),
                    new ClienteTypes("Credito"),
                    new ClienteTypes("Otros")
            };

            density = context.getResources().getDisplayMetrics().density;

            String[] projection = new String[]{
                    TCliente.COL_ROWID,
                    TCliente.COL_RAZON_SOCIAL,
                    TCliente.COL_DIRECCION,
                    TCliente.COL_RUC,
                    TCliente.COL_CEDULA,
                    TCliente.COL_TELEFONO,
                    TCliente.COL_CONDICION_VENTA

            };

            Cursor cliente = getActivity().getContentResolver().query(DatabaseProvider.CLIENTE_CONTENT_URI, projection,
                    null, null,null);
            try{
            if (cliente.moveToFirst()) {
                do {
                    switch (cliente.getString(6).trim().toUpperCase()) {
                        case "CONTADO":
                            pos = 0;
                            break;
                        case "CREDITO":
                            pos = 1;
                            break;
                        default:
                            pos = 2;
                    }
                    familys[pos].list.add(new Cliente(cliente.getString(0) + " - " + cliente.getString(1),
                            cliente.getString(2),
                            cliente.getString(3),
                            cliente.getString(4),
                            cliente.getString(5)
                    ));
                } while (cliente.moveToNext());
            }
                familys[0].list.add(new Cliente("Vacio", "Vacio", "Vacio", "Vacio", "Vacio"));
                familys[0].list.add(new Cliente("Vacio", "Vacio", "Vacio", "Vacio", "Vacio"));
                familys[0].list.add(new Cliente("Vacio", "Vacio", "Vacio", "Vacio", "Vacio"));
                familys[0].list.add(new Cliente("Vacio", "Vacio", "Vacio", "Vacio", "Vacio"));
                familys[0].list.add(new Cliente("Vacio", "Vacio", "Vacio", "Vacio", "Vacio"));
                familys[1].list.add(new Cliente("Vacio", "Vacio", "Vacio", "Vacio", "Vacio"));
                familys[1].list.add(new Cliente("Vacio", "Vacio", "Vacio", "Vacio", "Vacio"));
                familys[1].list.add(new Cliente("Vacio", "Vacio", "Vacio", "Vacio", "Vacio"));
                familys[1].list.add(new Cliente("Vacio", "Vacio", "Vacio", "Vacio", "Vacio"));
                familys[1].list.add(new Cliente("Vacio", "Vacio", "Vacio", "Vacio", "Vacio"));
                familys[2].list.add(new Cliente("Vacio", "Vacio", "Vacio", "Vacio", "Vacio"));

            }catch (Exception e) {
                Util.err_Log(ClienteTableFragment.this, e.getMessage());
            }finally{
                cliente.close();
            }

        }

        @Override
        public int getRowCount() {
            return 14;
        }

        @Override
        public int getColumnCount() {
            return 4;
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
                    view = getFamilyView(row, column, convertView, parent);
                    break;
                default:
                    throw new RuntimeException("wtf?");
            }
            return view;
        }

        private View getFirstHeader(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_table_header_first, parent, false);
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[0]);
            return convertView;
        }

        private View getHeader(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_table_header, parent, false);
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[column + 1]);
            return convertView;
        }

        private View getFirstBody(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_table_first, parent, false);
            }
            convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).getData()[column + 1]);
            return convertView;
        }

        private View getBody(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_table, parent, false);
            }
            convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).getData()[column + 1]);
            return convertView;
        }

        private View getFamilyView(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_table_family, parent, false);
            }
            final String string;
            if (column == -1) {
                string = getFamily(row).name;
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
            } else if (isFamily(row)) {
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
            } else if (isFamily(row)) {
                itemViewType = 4;
            } else if (column == -1) {
                itemViewType = 2;
            } else {
                itemViewType = 3;
            }
            return itemViewType;
        }

        private boolean isFamily(int row) {
            int family = 0;
            while (row > 0) {
                row -= familys[family].size() + 1;
                family++;
            }
            return row == 0;
        }

        private ClienteTypes getFamily(int row) {
            int family = 0;
            while (row >= 0) {
                row -= familys[family].size() + 1;
                family++;
            }
            return familys[family - 1];
        }

        private Cliente getDevice(int row) {
            int family = 0;
            while (row >= 0) {
                row -= familys[family].size() + 1;
                family++;
            }
            family--;
            return familys[family].get(row + familys[family].size());
        }

        @Override
        public int getViewTypeCount() {
            return 5;
        }
    }
}
