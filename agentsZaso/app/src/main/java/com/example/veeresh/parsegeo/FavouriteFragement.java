package com.example.veeresh.parsegeo;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import APIs.AllFavouriteEndPoint;
import AdapterCustom.ListViewAdapter;
import Models.FavouriteModel;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.JacksonConverter;

/**
 * Created by gajendrasingh on 12/22/2015.
 */
public class FavouriteFragement extends Fragment {
    ListView listView;
    ProgressBar progressBar;
    Context context=this.mparent;
    MapsActivity mparent;
    private List<FavouriteModel> models=new ArrayList<FavouriteModel>();

   /* public FavouriteFragement()
    {
        this.context=mparent.getApplicationContext();
    }*/

    RestAdapter retrofit = new RestAdapter.Builder()
            .setEndpoint(MapsActivity.API).setConverter(new JacksonConverter()).build();
    AllFavouriteEndPoint allFavouriteEndPoint=retrofit.create(AllFavouriteEndPoint.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.favourite_listviewfragment,container,false);

        listView=(ListView)rootView.findViewById(R.id.listviewId);
        progressBar=(ProgressBar)rootView.findViewById(R.id.progressId);
        //listView.setAdapter(new ListViewAdapter(context,));
        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getAllFavourite();

        return rootView;
    }
   public void getAllFavourite()
    {
       allFavouriteEndPoint.getAllFav(new Callback<List<FavouriteModel>>() {
           @Override
           public void success(List<FavouriteModel> favouriteModels, Response response) {
               progressBar.setVisibility(View.GONE);
               listView.setVisibility(View.VISIBLE);
               models=favouriteModels;
               listView.setAdapter(new ListViewAdapter(context,models));

           }

           @Override
           public void failure(RetrofitError error) {
               System.out.println(error.toString());

           }
       });

    }


}
