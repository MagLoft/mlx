package com.github.magloft.mlx.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;

import com.github.magloft.mlx.Extension;
import com.github.magloft.mlx.ExtensionCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Location extends Extension {

    @Override
    public List<String> getActions() {
        return Collections.singletonList("location");
    }

    @Override
    public List<String> getPermissions() {
        return Collections.singletonList(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void performAction(final Activity activity, final String action, final Map<String, Object> data, final ExtensionCallback extensionCallback) {
        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(activity);
        locationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
            @Override
            public void onComplete(@NonNull Task<android.location.Location> task) {
                android.location.Location location = task.getResult();
                Map<String, Object> response = new HashMap<>();
                response.put("latitude", location.getLatitude());
                response.put("longitude", location.getLongitude());
                extensionCallback.onSuccess(response);
            }
        });
    }
}
