package com.example.tungphan.wizelinecleanshortenchallenge.common;

import android.os.Build;

/**
 * Created by tungphan on 3/23/17.
 */

public class Utils {

    public static boolean isLowerThanJellyBeans() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean isLowerThanLollipop() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isHigherThanMasmarlow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
