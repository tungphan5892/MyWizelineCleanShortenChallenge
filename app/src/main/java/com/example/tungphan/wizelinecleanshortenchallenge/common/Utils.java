package com.example.tungphan.wizelinecleanshortenchallenge.common;

import android.os.Build;

/**
 * Created by tungphan on 3/23/17.
 */

public class Utils {

    public static boolean isHigherThanJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean isHigherThanJellyBeanMR2() {
        return
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    public static boolean isHigherThanKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isHigherThanMasmarlow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
