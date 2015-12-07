package com.example.veeresh.parsegeo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gajendrasingh on 12/1/2015.
 */
public class Fragment1 extends Fragment {

    public View onCreateView(LayoutInflater inf, ViewGroup vg, Bundle b){

        // Simply inflate the View from the .xml file.
        return inf.inflate(R.layout.fragment_1, vg, false);
    }
}
