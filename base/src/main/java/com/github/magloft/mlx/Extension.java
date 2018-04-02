package com.github.magloft.mlx;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class Extension extends ContentProvider {

    public Extension() {
        super();
    }

    public List<String> getActions() {
        return Collections.emptyList();
    }

    public List<String> getPermissions() {
        return Collections.emptyList();
    }

    public abstract void performAction(final Activity activity, final String action, final Map<String, Object> data, final ExtensionCallback extensionCallback);

    public boolean onCreate() {
        ExtensionManager.getInstance().registerExtension(this);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
