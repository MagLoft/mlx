package com.github.magloft.mlx.phone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.github.magloft.mlx.Extension;
import com.github.magloft.mlx.ExtensionCallback;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class Phone extends Extension {

    @Override
    public List<String> getActions() {
        return Collections.singletonList("phone");
    }

    @Override
    public List<String> getPermissions() {
        return Collections.singletonList(Manifest.permission.CALL_PHONE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void performAction(final Activity activity, final String action, final Map<String, Object> data, final ExtensionCallback extensionCallback) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        String number = (String) data.get("number");
        intent.setData(Uri.parse("tel:" + number));
        activity.startActivity(intent);
        extensionCallback.onSuccess(null);
    }
}
