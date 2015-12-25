package com.example.veeresh.parsegeo;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by gajendrasingh on 12/12/2015.
 */
public abstract class BaseFragment extends Fragment {
    protected MapsActivity mParentActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mParentActivity=(MapsActivity)context;
    }

    public abstract String getCustomTag();
}
