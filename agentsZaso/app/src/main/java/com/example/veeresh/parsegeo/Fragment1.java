package com.example.veeresh.parsegeo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import Models.AllAgentModels;

/**
 * Created by gajendrasingh on 12/1/2015.
 */
public class Fragment1 extends Fragment {
private TextView textView1,textView2,textView3,textView4,textView5,textView6;
    public View onCreateView(LayoutInflater inf, ViewGroup vg, Bundle b){
      // Toast.makeText(getActivity(), getArguments().getParcelable("values").toString(), Toast.LENGTH_LONG).show();
        AllAgentModels info=getArguments().getParcelable("values");
      //  textView1=(TextView)findViewById(R.id.textViewId1);
View rootView=inf.inflate(R.layout.fragment_1, vg, false);
        textView1=(TextView)rootView.findViewById(R.id.textViewId1);
        textView2=(TextView)rootView.findViewById(R.id.textViewId2);
        textView3=(TextView)rootView.findViewById(R.id.textViewId3);
        textView4=(TextView)rootView.findViewById(R.id.textViewId4);
        textView5=(TextView)rootView.findViewById(R.id.textViewId0);
       // textView6=(TextView)rootView.findViewById(R.id.textViewId00);
        textView1.setText(info.getEmailid());
        textView2.setText(info.getAddress());
        textView3.setText(info.getSpeciality());
        textView4.setText(info.getMobileNumber());
        textView5.setText(info.getfName()+" "+info.getlName());
        return rootView;

        // Simply inflate the View from the .xml file.
        //return inf.inflate(R.layout.fragment_1, vg, false);
    }
}
