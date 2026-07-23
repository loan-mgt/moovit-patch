package app.revanced.extension.shared.patches;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import app.revanced.extension.shared.Logger;
import app.revanced.extension.shared.Utils;

public class GmsLocationHelper {
    private static final List<LocationListener> activeListeners = new ArrayList<>();
    private static Method onLocationChangedMethod;

    @SuppressLint("MissingPermission")
    public static void requestLocationUpdates(Object gmsLocationListener, Looper looper) {
        try {
            Context context = Utils.getContext();
            if (context == null) { Logger.printInfo(() -> "GmsLocationHelper: Context is null"); return; }
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager == null) { Logger.printInfo(() -> "GmsLocationHelper: LocationManager is null"); return; }
            removeLocationUpdatesInternal(locationManager);
            if (onLocationChangedMethod == null)
                onLocationChangedMethod = gmsLocationListener.getClass().getMethod("onLocationChanged", Location.class);
            LocationListener listener = new LocationListener() {
                @Override public void onLocationChanged(Location location) {
                    try { onLocationChangedMethod.invoke(gmsLocationListener, location); }
                    catch (Exception e) { Logger.printException(() -> "GmsLocationHelper: Failed to forward location", e); }
                }
                @Override public void onStatusChanged(String provider, int status, Bundle extras) {}
                @Override public void onProviderEnabled(String provider) {}
                @Override public void onProviderDisabled(String provider) {}
            };
            List<String> providers = locationManager.getProviders(true);
            boolean registered = false;
            for (String provider : providers) {
                try { locationManager.requestLocationUpdates(provider, 1000L, 0f, listener, looper); registered = true; }
                catch (Exception e) { /* ignore */ }
            }
            if (registered) activeListeners.add(listener);
        } catch (Exception e) {
            Logger.printException(() -> "GmsLocationHelper: Failed to request location updates", e);
        }
    }

    public static void removeLocationUpdates() {
        try {
            Context context = Utils.getContext();
            if (context == null) return;
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager == null) return;
            removeLocationUpdatesInternal(locationManager);
        } catch (Exception e) {
            Logger.printException(() -> "GmsLocationHelper: Failed to remove location updates", e);
        }
    }

    private static void removeLocationUpdatesInternal(LocationManager locationManager) {
        for (LocationListener listener : activeListeners) {
            try { locationManager.removeUpdates(listener); } catch (Exception e) { /* Ignore. */ }
        }
        activeListeners.clear();
    }
}
