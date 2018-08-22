package com.udacity.gradle.builditbigger;

import com.google.android.gms.ads.AdRequest;

final class AdUtil {
    private AdUtil() {
    }


    public static boolean haveAds() {
        return false;
    }

    public static AdRequest getAdRequest() {
        return null;
    }
}