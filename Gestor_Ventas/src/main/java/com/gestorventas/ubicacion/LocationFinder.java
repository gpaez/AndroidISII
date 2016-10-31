package com.gestorventas.ubicacion;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;

/**
 * This class fixes the current Location coordinates of the device
 *
 * @author openmobster@gmail.com
 */
public class LocationFinder {
    private Location location;
    private Context context;

    public LocationFinder() {
    }

    public LocationFinder(Context context) {
        this.context = context;
    }


    /**
     * Start the process of asking the hardware for location coordinates
     */
    public void startFind(Context context) {
        LocationLooper looper = new LocationLooper();
        looper.start();

        while (!looper.isReady()) ;

        looper.handler.post(new LocationBootstrapper(context));
    }

    /**
     * End the process of getting the location and cleanup
     *
     * @return the location obtained from the hardware
     */
    public Location endFind() {
        int counter = 6;
        while (this.location == null) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
            }

            if (counter-- == 0) {
                break;
            }
        }

        return this.location;
    }

    /**
     * Used to run location related code on the main thread
     *
     * @author openmobster@gmail.com
     */
    private class LocationLooper extends Thread {
        private Handler handler;


        private LocationLooper() {

        }


        public void run() {
            Looper.prepare();

            this.handler = new Handler();

            Looper.loop();
        }

        public boolean isReady() {
            return this.handler != null;
        }
    }

    /**
     * Bootstraps the Location fetching process
     *
     * @author openmobster@gmail.com
     */
    private class LocationBootstrapper implements Runnable {
        private Context context;

        private LocationBootstrapper(Context context) {
            this.context = context;
        }

        public void run() {
            // Acquire a reference to the system Location Manager
            LocationManager locationManager = (LocationManager) context.
                    getSystemService(Context.LOCATION_SERVICE);

            //This component receives callback with the results
            LocationListener locationListener = new LocationListenerImpl();

            // Register the listener with the Location Manager to receive location updates
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

            // Register the listener with the Location Manager to receive location updates
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            //Stay open for 10 seconds...get the fix in 10 seconds
            try {
                Thread.sleep(30000);
            } catch (Exception e) {
            }
            ;

            locationManager.removeUpdates(locationListener);
        }
    }

    /**
     * LocationListener that receives the results of reading the location from the hardware
     *
     * @author openmobster@gmail.com
     */
    private class LocationListenerImpl implements LocationListener {
        private LocationListenerImpl() {
        }

        //@Override
        public void onLocationChanged(Location location) {
            if (isBetterLocation(location, LocationFinder.this.location)) {
                //Set this location
                LocationFinder.this.location = location;
            }
        }

        //@Override
        public void onProviderDisabled(String provider) {
        }

        //@Override
        public void onProviderEnabled(String provider) {
        }

        //@Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        /**
         * Determines whether one Location reading is better than the current Location fix
         *
         * @param location            The new Location that you want to evaluate
         * @param currentBestLocation The current Location fix, to which you want to compare the new one
         */
        protected boolean isBetterLocation(Location location, Location currentBestLocation) {
            if (currentBestLocation == null) {
                // A new location is always better than no location
                return true;
            }

            // Check whether the new location fix is newer or older
            long timeDelta = location.getTime() - currentBestLocation.getTime();
            boolean isSignificantlyNewer = timeDelta > 120000;
            boolean isSignificantlyOlder = timeDelta < -120000;
            boolean isNewer = timeDelta > 0;

            // If it's been more than two minutes since the current location, use the new location
            // because the user has likely moved
            if (isSignificantlyNewer) {
                return true;
                // If the new location is more than two minutes older, it must be worse
            } else if (isSignificantlyOlder) {
                return false;
            }

            // Check whether the new location fix is more or less accurate
            int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
            boolean isLessAccurate = accuracyDelta > 0;
            boolean isMoreAccurate = accuracyDelta < 0;
            boolean isSignificantlyLessAccurate = accuracyDelta > 200;

            // Check if the old and new location are from the same provider
            boolean isFromSameProvider = isSameProvider(location.getProvider(),
                    currentBestLocation.getProvider());

            // Determine location quality using a combination of timeliness and accuracy
            if (isMoreAccurate) {
                return true;
            } else if (isNewer && !isLessAccurate) {
                return true;
            } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
                return true;
            }
            return false;
        }

        /**
         * Checks whether two providers are the same
         */
        private boolean isSameProvider(String provider1, String provider2) {
            if (provider1 == null) {
                return provider2 == null;
            }
            return provider1.equals(provider2);
        }
    }
}
