package com.example.veeresh.parsegeo;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.parse.Parse;

/**
 * Created by Veeresh on 8/23/2015.
 */
public class
        MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "3Hi4C0izieFW3foZOjoBOCJ00BezGUsFQBFVp37t", "pFmAtXWpurad8u7ZSZjZEVWbHiVnaqWOTCXOaEfB");
    }
}