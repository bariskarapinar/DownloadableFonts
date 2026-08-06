package com.myapp.downloadablefonts;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.Nullable;
import androidx.core.provider.FontRequest;
import androidx.core.provider.FontsContractCompat;

public class FontRequestManager {

    private final Context context;
    private Handler handler;

    public FontRequestManager(Context context) {
        this.context = context.getApplicationContext();
    }

    private Handler getHandler() {
        if (handler == null) {
            HandlerThread handlerThread = new HandlerThread("fonts");
            handlerThread.start();
            handler = new Handler(handlerThread.getLooper());
        }
        return handler;
    }

    public void requestFont(String query, final FontCallback callback) {
        String formattedQuery = "name=" + query;
        FontRequest request = new FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                formattedQuery,
                R.array.com_google_android_gms_fonts_certs
        );

        FontsContractCompat.FontRequestCallback fontCallback = new FontsContractCompat.FontRequestCallback() {
            @Override
            public void onTypefaceRetrieved(Typeface typeface) {
                callback.onSuccess(typeface);
            }

            @Override
            public void onTypefaceRequestFailed(int reason) {
                callback.onError(reason);
            }
        };

        FontsContractCompat.requestFont(context, request, fontCallback, getHandler());
    }

    public interface FontCallback {
        void onSuccess(Typeface typeface);
        void onError(int reason);
    }
}
