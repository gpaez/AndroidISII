package com.gestorventas.ubicacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.EditText;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author openmobster@gmail.com
 */
public class GetLocationCoordinatesTask extends AsyncTask<Map<String, Object>,Integer,Map<String, Object>>
{
    private Context context;
    private ProgressDialog progressDialog;
    EditText mtxtLatitud, mtxtLongitud;

    public GetLocationCoordinatesTask(Context context, EditText latitud, EditText longitud)
    {
        this.context = context;
        mtxtLatitud = latitud;
        mtxtLongitud = longitud;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        //Setup the progress dialog
        this.progressDialog = new ProgressDialog(this.context);
        this.progressDialog.setTitle("");
        this.progressDialog.setMessage("Procesando....");
        this.progressDialog.setCancelable(true);
    }


    @Override
    protected Map<String, Object> doInBackground(Map<String, Object>... params)
    {
        try
        {
            Map<String, Object> result = new HashMap<String, Object>();

            //Show the Progress Dialog
            this.publishProgress(0);

            //Use the LocationFinder to find a location from the hardware
            LocationFinder locationFinder = new LocationFinder();
            locationFinder.startFind(this.context);
            Location location = locationFinder.endFind();

            if(location != null)
            {
                result.put("location", location);
            }

            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);

        //Show the progress dialog
        this.progressDialog.show();
    }

    @Override
    protected void onPostExecute(Map<String, Object> result)
    {
        super.onPostExecute(result);

        //Dismiss the progress dialog
        this.progressDialog.dismiss();

        Location location = (Location)result.get("location");

        if(location != null)
        {
            mtxtLatitud.setText(String.valueOf(location.getLatitude()));
            mtxtLongitud.setText(String.valueOf(location.getLongitude()));
        }
    }
}
