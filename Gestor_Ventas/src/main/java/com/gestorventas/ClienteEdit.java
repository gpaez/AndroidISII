package com.gestorventas;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gestorventas.clases.Cliente;
import com.gestorventas.clases.Usuario;
import com.gestorventas.utils.Util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.kyleduo.switchbutton.SwitchButton;
import android.widget.Toast;
import com.gestorventas.database.DatabaseProvider;

import org.w3c.dom.Text;

/**
 * Created by Guille on 22/10/16.
 */

public class ClienteEdit extends Fragment implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    private EditText editLongitud;
    private EditText editLatitud;
    private ToggleButton btnActualizar;
    private EditText editRazonSocial;
    private EditText editDireccion;
    private EditText editTelefono;
    private EditText editRUC;
    private EditText editCedula;
    private SwitchButton swContado;
    private Button btnRegistar;
    private TextView txtPago;

    private static final String LOGTAG = "android-localizacion";

    private static final int PETICION_PERMISO_LOCALIZACION = 101;

    private GoogleApiClient apiClient;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cliente_edit, container, false);

        editLatitud     = (EditText) rootView.findViewById(R.id.editLatitud);
        editLongitud    = (EditText) rootView.findViewById(R.id.editLongitud);
        btnActualizar   = (ToggleButton) rootView.findViewById(R.id.btnLatitude);
        editCedula      = (EditText) rootView.findViewById(R.id.editCedula);
        editRUC         = (EditText) rootView.findViewById(R.id.editRUC);
        editDireccion   = (EditText) rootView.findViewById(R.id.editDirelccion);
        editTelefono    = (EditText) rootView.findViewById(R.id.editTelefono);
        editRazonSocial = (EditText) rootView.findViewById(R.id.editRazonSocial);
        swContado       = (SwitchButton) rootView.findViewById(R.id.sb_default);
        btnRegistar     = (Button) rootView.findViewById(R.id.btnRegistrarClientes);
        txtPago     = (TextView) rootView.findViewById(R.id.txtPago);

        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar(v);
            }
        });

        swContado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    txtPago.setText("Credito");
                }else {
                    txtPago.setText("Contado");
                }

            }
        });


        editLongitud.setEnabled(false);
        editLatitud.setEnabled(false);
        try {

            apiClient = new GoogleApiClient.Builder(this.getActivity())
                    .enableAutoManage(this.getActivity(), this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
        }catch (Exception ex){
            Util.err_Log(this, ex.getMessage());
        }

        return rootView;
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        //Se ha producido un error que no se puede resolver automáticamente
        //y la conexión con los Google Play Services no se ha establecido.

        Log.e(LOGTAG, "Error grave al conectar con Google Play Services");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Conectado correctamente a Google Play Services
        try{
            if (ActivityCompat.checkSelfPermission(this.getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PETICION_PERMISO_LOCALIZACION);
            } else {

                Location lastLocation =
                        LocationServices.FusedLocationApi.getLastLocation(apiClient);

                updateUI(lastLocation);
            }
        }catch (Exception ex){
            Util.err_Log(this, ex.getMessage());
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        //Se ha interrumpido la conexión con Google Play Services

        Log.e(LOGTAG, "Se ha interrumpido la conexión con Google Play Services");
    }

    private void updateUI(Location loc) throws Exception{
        if (loc != null) {
            editLatitud.setText(String.valueOf(loc.getLatitude()));
            editLongitud.setText(String.valueOf(loc.getLongitude()));
        } else {
            editLatitud.setText("desconocida");
            editLongitud.setText("desconocida");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try{
            if (requestCode == PETICION_PERMISO_LOCALIZACION) {
                if (grantResults.length == 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Permiso concedido

                    @SuppressWarnings("MissingPermission")
                    Location lastLocation =
                            LocationServices.FusedLocationApi.getLastLocation(apiClient);

                    updateUI(lastLocation);

                } else {
                    //Permiso denegado:
                    //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.

                    Log.e(LOGTAG, "Permiso denegado");
                }
            }

        }catch (Exception ex){
            Util.err_Log(this, ex.getMessage());
        }

    }

    private void  registrar(View v){
        try {
            validaciones();
            Cliente cliente = new Cliente(editRazonSocial.getText().toString(),
                                          txtPago.getText().toString().toUpperCase(),
                                          editDireccion.getText().toString(),
                                          editRUC.getText().toString(),
                                          editCedula.getText().toString(),
                                          editTelefono.getText().toString(),
                                          Usuario.VENDEDOR,
                                          editLongitud.getText().toString(),
                                          editLatitud.getText().toString());
            getActivity().getContentResolver().insert(DatabaseProvider.CLIENTE_CONTENT_URI, cliente.clienteMapperContentValues());
            Toast.makeText(getContext(),
                    getClass().getName() + " Registro Completo!!!",
                    Toast.LENGTH_LONG).show();

            //Vuelve a la pantalla Principal
             getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment, new HomeFragment(), "fragment")
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } catch (Exception ex) {
            Log.w(getString(R.string.app_name), ex.getMessage(), ex);
            Toast.makeText(getContext(),
                    ex.getClass().getName() + " " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void validaciones() throws Exception{
        Util.validaVacio(getContext(),
                         editRazonSocial,
                         ">>Favor ingrese nombre Comercial");
        Util.validaVacio(getContext(),
                editDireccion,
                ">>Favor ingrese direccion del Cliente");
        Util.validaVacio(getContext(),
                editTelefono,
                ">>Favor ingrese numero de Contacto");
        Util.validaVacio(getContext(),
                editLatitud,
                ">>Error de Latitud");
        Util.validaVacio(getContext(),
                editLongitud,
                ">>Error de Longitud");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        apiClient.stopAutoManage(this.getActivity());
        apiClient.disconnect();
    }
}
