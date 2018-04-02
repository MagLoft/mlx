package com.github.magloft.mlx;

import java.util.Map;

public interface ExtensionCallback {
    void onSuccess(Map<String, Object> response);
    void onError(Exception error);
}
