package com.github.magloft.mlx;

import android.app.Activity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtensionManager {
    private static final ExtensionManager ourInstance = new ExtensionManager();
    public static ExtensionManager getInstance() {
        return ourInstance;
    }
    private final List<Extension> extensions = new ArrayList<>();

    public void registerExtension(Extension extension) {
        this.extensions.add(extension);
    }

    public void performAction(final Activity activity, final String action, final Map<String,Object> data, final ExtensionCallback extensionCallback) {
        for (final Extension extension : this.extensions) {
            if (extension.getActions().contains(action)) {
                requestPermissions(activity, extension.getPermissions(), new ExtensionCallback() {
                    @Override
                    public void onSuccess(Map<String, Object> response) {
                        extension.performAction(activity, action, data, extensionCallback);
                    }

                    @Override
                    public void onError(Exception error) {
                        extensionCallback.onError(error);
                    }
                });
                return;
            }
        }
    }

    private void requestPermissions(final Activity activity, List<String> permissions, final ExtensionCallback extensionCallback) {
        if (permissions.isEmpty()) {
            extensionCallback.onSuccess(null);
        }else{
            Dexter.withActivity(activity).withPermissions(permissions).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
                        extensionCallback.onSuccess(null);
                    }else{
                        extensionCallback.onError(new Exception("Insufficient Permissions"));
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        }
    }
}
