package com.udacity.gradle.builditbigger;

import com.google.android.gms.ads.AdRequest;

public final class AdUtil {
    private AdUtil() {
    }


    public static boolean haveAds() {
        return true;
    }

    public static AdRequest getAdRequest() {
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        return new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
    }
}