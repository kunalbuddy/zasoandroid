package com.example.veeresh.parsegeo;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.zaso.Utils.ZasoStack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import APIs.NewUserEndPoint;
import Models.NewUserModel;
import Models.ResponseBack;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.JacksonConverter;

/**
 * Created by gajendrasingh on 12/10/2015.
 */
public class SignUpFragment extends BaseFragment implements Validator.ValidationListener {
    Validator validator;

    public final static String TAG_NAME ="sign_up";

    @Length(min = 1,message= "Name Cannot be empty")
    private EditText nameEditText;
    @NotEmpty
    @Email
    private EditText emailEditText;
    @Length(min=10,max = 10,message = "enter valid mobile number")
    private EditText mobileEditText;
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    private EditText passwordEditText;
    @ConfirmPassword
    private EditText confirmPasswordEditText;
    private SharedPreferences sharedPreferences=null;

    private Button signUpButton;
     FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    ZasoStack mZasoStack;

    RestAdapter retrofit = new RestAdapter.Builder()
            .setEndpoint(MapsActivity.API).setConverter(new JacksonConverter()).build();

    NewUserEndPoint newUserEndPoint=retrofit.create(NewUserEndPoint.class);

    @Override
    public String getCustomTag() {
        return TAG_NAME;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences=this.getActivity().getSharedPreferences("auth_tocken", Context.MODE_PRIVATE);
       // mFragmentManager = this.getActivity().getSupportFragmentManager();

    }

    public int getCount() {
        return mFragmentManager.getBackStackEntryCount();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);

        View rootView=inflater.inflate(R.layout.signupfrgment, container, false);





        nameEditText=(EditText)rootView.findViewById(R.id.editTextId1);
        emailEditText=(EditText)rootView.findViewById(R.id.editTextId2);
        mobileEditText=(EditText)rootView.findViewById(R.id.editTextId3);
        passwordEditText=(EditText)rootView.findViewById(R.id.editTextId4);
        confirmPasswordEditText=(EditText)rootView.findViewById(R.id.editTextId5);
        signUpButton=(Button)rootView.findViewById(R.id.submitButtonId);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewUserModel newUserModel=new NewUserModel();
                newUserModel.setFullName(nameEditText.getText().toString());
                newUserModel.setEmaiId(emailEditText.getText().toString());
                newUserModel.setMobileNumber(mobileEditText.getText().toString());

                String passw=md5(confirmPasswordEditText.getText().toString());
                newUserModel.setPassword(passw);



                newUserEndPoint.saveNewUser(newUserModel,new Callback<ResponseBack>() {

                    @Override
                    public void success(ResponseBack responseBack, Response response) {
                        String token=responseBack.getTocken();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("auth_tocken",token);
                        editor.commit();
                        mFragmentManager=getActivity().getSupportFragmentManager();
                        mFragmentTransaction=mFragmentManager.beginTransaction();

                        mFragmentTransaction.addToBackStack(getCustomTag());
                        mFragmentTransaction.commit();

                        mFragmentManager.executePendingTransactions();
                        int count=getCount();
                        for(int i=0;i<count;i++)
                        {
                            mFragmentManager.popBackStack();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getActivity(),"SignedUp failed",Toast.LENGTH_LONG).show();

                    }
                });


            }
        });


        validator = new Validator(this);
        validator.setValidationListener(this);

        return rootView;
    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(getActivity(), "you have Successfully signed up", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }



    public static final String md5(final String pass) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(pass.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
