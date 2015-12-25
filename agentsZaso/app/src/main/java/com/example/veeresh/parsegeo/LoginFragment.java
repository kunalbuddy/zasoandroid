package com.example.veeresh.parsegeo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import APIs.LoginInfoEndPoint;
import APIs.UserNewTokenEndPoint;
import Models.NewUserModel;
import Models.ResponseBack;
import Models.UserAuthentication;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.JacksonConverter;

/**
 * Created by gajendrasingh on 12/10/2015.
 */
public class LoginFragment extends BaseFragment {
    public final static String TAG_NAME ="login";

   private Button buttonLogin;
   private TextView textView;
    private EditText editTextEmail,editTextPass;
   private FragmentManager manager;
   private FragmentTransaction trans;
    SignUpFragment signUpFragment;
    private Boolean status;
    UserAuthentication model;
    SharedPreferences sharedPreferences;
    private NewUserModel usermodel;
   // MapsActivity mP
    //BaseFragment base=new BaseFragment();
    SharedPreferences sharedPreferencesLoginToken;

    SignUpFragment fragment=new SignUpFragment();

    RestAdapter retrofit = new RestAdapter.Builder()
            .setEndpoint(MapsActivity.API).setConverter(new JacksonConverter()).build();
    LoginInfoEndPoint loginInfoEndPoint=retrofit.create(LoginInfoEndPoint.class);
    UserNewTokenEndPoint userNewTokenEndPoint=retrofit.create(UserNewTokenEndPoint.class);

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=this.getActivity().getSharedPreferences("auth_tocken",Context.MODE_PRIVATE);
        model=new UserAuthentication();
        manager=this.getActivity().getSupportFragmentManager();
    }

    @Override
    public String getCustomTag() {
        return TAG_NAME;
    }

    public int getCount() {
        return manager.getBackStackEntryCount();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)throws InflateException{
       // return super.onCreateView(inflater, container, savedInstanceState);
       // int res=R.layout.loginfragment;
        View view=inflater.inflate(R.layout.loginfragment,container,false);
        textView=(TextView)view.findViewById(R.id.signUpHereId);
        buttonLogin=(Button)view.findViewById(R.id.loginButtonId);
        editTextEmail=(EditText)view.findViewById(R.id.loginEditTextId);
        editTextPass=(EditText)view.findViewById(R.id.loginEditTextId1);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    manager = getActivity().getSupportFragmentManager();
                    trans = manager.beginTransaction();

                    trans.replace(R.id.main_container, fragment);
                    trans.addToBackStack(getCustomTag());

                    trans.commit();
                manager.executePendingTransactions();



            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = false;
                String email = editTextEmail.getText().toString();
                String pass = editTextPass.getText().toString();
                String passText = signUpFragment.md5(pass);
                SharedPreferences token = mParentActivity.getSharedPreferences("auth_tocken", 0);
                String getToken = token.getString("auth_tocken", null);
                loginInfoEndPoint.getLoginInfo(email, passText, new Callback<ResponseBack>() {
                    @Override
                    public void success(ResponseBack responseBack, Response response) {
                        status = responseBack.getStatus();

                        if (status) {
                            model.setEmilaId(editTextEmail.getText().toString());
                            userNewTokenEndPoint.storeNewTokenInfo(model, new Callback<ResponseBack>() {

                                @Override
                                public void success(ResponseBack responseBack, Response response) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    editor.putString("auth_tocken", responseBack.getTocken());

                                    editor.commit();
                                    manager=getActivity().getSupportFragmentManager();
                                    trans=manager.beginTransaction();
                                    trans.addToBackStack(getCustomTag());
                                    trans.commit();
                                    manager.executePendingTransactions();


                                    int count=getCount();
                                    for(int i=0;i<count;i++)
                                    {
                                        manager.popBackStack();
                                    }

                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    System.out.println(error);

                                }
                            });

                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_LONG).show();

                    }
                });

               /* if (status) {
                    model.setEmilaId(editTextEmail.getText().toString());
                    userNewTokenEndPoint.storeNewTokenInfo(model, new Callback<ResponseBack>() {

                        @Override
                        public void success(ResponseBack responseBack, Response response) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("auth_tocken", responseBack.getTocken());

                            editor.commit();


                            int count=getCount();
                            for(int i=0;i<count;i++)
                            {
                                manager.popBackStack();
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            System.out.println(error);

                        }
                    });

                }*/
               /* else
                {
                    Toast.makeText(mParentActivity,"token is not null",Toast.LENGTH_LONG).show();
                }*/
            }
        });


        return view;
    }
}
