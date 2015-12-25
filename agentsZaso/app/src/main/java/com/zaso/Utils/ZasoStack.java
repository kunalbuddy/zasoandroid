package com.zaso.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by gajendrasingh on 12/19/2015.
 */
public class ZasoStack {
    FragmentManager mFragmentManager;

    Fragment mTopFragment;
    String mTopFragmentName = "";
    String mBottomFragmentName = "";
    private boolean isResponsePopUntil = false;

    public ZasoStack(FragmentManager mFragmentManager) {
        init(mFragmentManager);
    }

    public void init(FragmentManager mgr) {
        this.mFragmentManager = mgr;
    }


    public String getFragmentNameRelative(int relativePos) {
        int index = mFragmentManager.getBackStackEntryCount() - 1 + relativePos;
        if (index >= 0) {
            return mFragmentManager.getBackStackEntryAt(index).getName();
        } else {
            return "";
        }
    }

   /* public int getCount() {
        return mFragmentManager.getBackStackEntryCount();
    }*/

  /*  public void pop() {
        isResponsePopUntil = false;
        if (getCount() <= 1) {
            mTopFragment = null;
            mTopFragmentName = "";
            mBottomFragmentName = "";

        } else {
            String name = getFragmentNameRelative(-1);
            mTopFragment = mFragmentManager.findFragmentByTag(name);
            mTopFragmentName = name;
        }
        mFragmentManager.popBackStackImmediate();
    }*/

    public void popUntil(String name) {
        //  isResponsePopUntil = name.equals(MobileInputFragment.TAG_NAME) || name.equals(DTHFragment.TAG_NAME) || name.equals(DataCardInputFragment.TAG_NAME);
        // /commented Fragment stack name

        mFragmentManager.popBackStackImmediate(name, 0);

        String topName = getFragmentNameRelative(0);
        mTopFragment = mFragmentManager.findFragmentByTag(topName);
        mTopFragmentName = topName;
    }

    public void popAll() {
        isResponsePopUntil = false;
        mTopFragment = null;
        mTopFragmentName = "";
        if (mFragmentManager != null) {
            mFragmentManager.popBackStackImmediate(null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }



    public FragmentManager getInstance() {
        return mFragmentManager;
    }

    public Fragment getTopFragment() {
        return mTopFragment;
    }

    public String getTopFragmentName() {
        if (mTopFragment == null) {
            return "";
        }
        return mTopFragmentName;
    }

  /*  public void setTopFragment(Fragment fragment, String fragmentName) {
        this.mTopFragment = fragment;
        this.mTopFragmentName = fragmentName;
        if (getCount() == 0) {
            this.mBottomFragmentName = fragmentName;
        }
    }
*/

}
