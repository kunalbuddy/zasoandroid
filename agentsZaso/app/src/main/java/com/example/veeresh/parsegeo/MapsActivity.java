
package com.example.veeresh.parsegeo;




import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;



import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import APIs.AgentPicEndPoint;
import APIs.AllAgentEndPoint;
import APIs.AllFavouriteEndPoint;
import APIs.LogoutEndPoint;
import APIs.RemoveFavEndPoint;
import APIs.SaveUserFavouriteEndPoint;
import AdapterCustom.ListViewAdapter;
import Models.AllAgentModels;
import Models.FavouriteModel;
import Models.PositionModel;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.JacksonConverter;


public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener,ActionBar.OnNavigationListener {
   public final static String API = "http://192.168.1.11:8081";
    public final static String TAG_NAME ="map activity";

    FavouriteFragement favouriteFragement;
    private Toolbar toolbar;
    PositionModel loc=null;
    public double latitude;
    public double longitude;
    private GoogleMap mMap;
    LocationManager location;
    Marker myMarker;
    Spinner spinner;
   ProgressDialog dialog;
    FragmentManager manager ;
    FragmentTransaction trans;
    Context context=MapsActivity.this;
    SharedPreferences sharedPreferences;
    private HashMap<Marker,AllAgentModels>  markerAgentMapping=new HashMap<>();
    LoginFragment login=new LoginFragment();
    private List<FavouriteModel> models=new ArrayList<FavouriteModel>();


    RestAdapter retrofit = new RestAdapter.Builder()
            .setEndpoint(API).setConverter(new JacksonConverter()).build();
    AgentPicEndPoint agentPicEndPoint=retrofit.create(AgentPicEndPoint.class);
    AllAgentEndPoint allAgentEndPoint=retrofit.create(AllAgentEndPoint.class);
    RemoveFavEndPoint removeFavEndPoint=retrofit.create(RemoveFavEndPoint.class);
    SaveUserFavouriteEndPoint userFavouriteEndPoint=retrofit.create(SaveUserFavouriteEndPoint.class);
    AllFavouriteEndPoint allFavouriteEndPoint=retrofit.create(AllFavouriteEndPoint.class);
    LogoutEndPoint logoutEndPoint=retrofit.create(LogoutEndPoint.class);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        sharedPreferences=getSharedPreferences("auth_tocken",0);
        String strpref=sharedPreferences.getString("auth_tocken","");



//
//        toolbar=(Toolbar)findViewById(R.id.toolBar);
//        setSupportActionBar(toolbar);b
       // getSupportActionBar().show();



            setUpMapIfNeeded();
            spinner = (Spinner) findViewById(R.id.spinner);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.agents_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            mMap.setOnMarkerClickListener(MapsActivity.this);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String name = parent.getItemAtPosition(position).toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
       // actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

/*
      final String[] dropdownValues = getResources().getStringArray(R.array.dropdown);

        ArrayAdapter<String> adapterBar = new ArrayAdapter<String>(actionBar.getThemedContext(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                dropdownValues);

       adapterBar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        actionBar.setListNavigationCallbacks(adapterBar, this);*/
    }


    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
        /*return super.onCreateOptionsMenu(menu);*/
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.favoriteId:
            {
             favouriteAll();
                return true;
            }
            case R.id.logoutId:
            {
               logoutZaso();
                return true;
            }
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setUpMapIfNeeded();
        sharedPreferences=getSharedPreferences("auth_tocken",0);
        String strpref=sharedPreferences.getString("auth_tocken","");
        if(strpref.equals("")) {

            loginPage(login);
        }
    }

    public void favouriteAll()
    {
       /* allFavouriteEndPoint.getAllFav(new Callback<List<FavouriteModel>>() {
            @Override
            public void success(List<FavouriteModel> favouriteModels, Response response) {

                models=favouriteModels;
                listView.setAdapter(new ListViewAdapter(context,models));

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });*/
        favouriteFragement=new FavouriteFragement();
        manager=getSupportFragmentManager();
        trans=manager.beginTransaction();
        trans.add(R.id.main_container,favouriteFragement);
        trans.addToBackStack(TAG_NAME);
        trans.commit();

    }

    public void logoutZaso()
    {
        sharedPreferences=getSharedPreferences("auth_tocken",0);
        String accessToken=sharedPreferences.getString("auth_tocken","");


        logoutEndPoint.logoutUser(accessToken, new Callback<String>() {
            @Override
            public void success(String s, Response response) {

                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                String token=sharedPreferences.getString("auth_tocken","");
                if(token.equals("")) {

                    loginPage(login);
                }


            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.toString());

            }
        });
    }




    public void loginPage(LoginFragment login)
    {
        manager = getSupportFragmentManager();
        trans = manager.beginTransaction();
        trans.add(R.id.main_container, login);
        trans.addToBackStack(TAG_NAME);
        trans.commit();
    }

    public void setUserLocation(Location location)
    {
        LatLng agentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(agentLocation, 15));


    }


    private void setUpMapIfNeeded() {
        location = new LocationManager(this);
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(dialog!=null) {
            dialog.dismiss();
        }
    }

    private void setUpMap() {

//ParseGeoPoint userLocation = new ParseGeoPoint();
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Agents");
//        query.whereNear("location", userLocation);
//        query.setLimit(10);

        mMap.setMyLocationEnabled(true);
        if(!sharedPreferences.getString("auth_tocken","").equals("")) {

            dialog = new ProgressDialog(MapsActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }


        allAgentEndPoint.getAllAgent(new Callback<List<AllAgentModels>>() {

            @Override
            public void success(final List<AllAgentModels> agentModelsList, Response response) {

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String name=parent.getItemAtPosition(position).toString();
                           mMap.clear();
                        markerAgentMapping.clear();

                            List<AllAgentModels> clubbedSpecAgents = clubAgentsSpeciality(agentModelsList);
                            // List<AllAgentModels> clubbedVisaAgents = visa_list(agentModelsList);

                                for (int i = 0; i < clubbedSpecAgents.size(); i++) {
                                    if(clubbedSpecAgents.get(i).getSpeciality().toLowerCase().contains(name.toLowerCase())) {
                                    // AllAgentModels speciality = clubbedSpecAgents.get(i);
                                    // speciality.getSpeciality();


                                    // PositionModel getLatLong=new PositionModel();
                                    //ParseGeoPoint geoPoint = (ParseGeoPoint) clubbedSpecAgents.get(i).getLocation(location);
                                    List<PositionModel> geoPoint = clubbedSpecAgents.get(i).getLocation();
                                    Iterator<PositionModel> itr = geoPoint.iterator();

                                    while (itr.hasNext()) {

                                        loc = itr.next();
                                    }


                                    AllAgentModels agentDetails = clubbedSpecAgents.get(i);
                                    // LatLng agentLocation = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
                                    LatLng agentLocation = new LatLng(loc.getLat(), loc.getLon());


                                        myMarker = mMap.addMarker(new MarkerOptions()
//                                            title(agentDetails.getSpeciality(speciality) + " ")


/* .title(agentDetails.getSpeciality()) */

                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_agent_map))
                                            .position(agentLocation));

                                    markerAgentMapping.put(myMarker, agentDetails);


                                }


                            }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {List<AllAgentModels> clubbedSpecAgents = clubAgentsSpeciality(agentModelsList);


                    }
                });

                mMap.clear();
                markerAgentMapping.clear();
                List<AllAgentModels> clubbedSpecAgents = clubAgentsSpeciality(agentModelsList);
                // List<Al
                for (int i = 0; i < clubbedSpecAgents.size(); i++) {

                    // AllAgentModels speciality = clubbedSpecAgents.get(i);
                    // speciality.getSpeciality();


                    // PositionModel getLatLong=new PositionModel();
                    //ParseGeoPoint geoPoint = (ParseGeoPoint) clubbedSpecAgents.get(i).getLocation(location);
                    List<PositionModel> geoPoint = clubbedSpecAgents.get(i).getLocation();
                    Iterator<PositionModel> itr = geoPoint.iterator();

                    while (itr.hasNext()) {

                        loc = itr.next();
                    }


                    AllAgentModels agentDetails = clubbedSpecAgents.get(i);
                    // LatLng agentLocation = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
                    LatLng agentLocation = new LatLng(loc.getLat(), loc.getLon());


                    myMarker = mMap.addMarker(new MarkerOptions()


/*.title(agentDetails.getSpeciality(speciality) + " ")*//*

                           */
/* .title(agentDetails.getSpeciality()) */

                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_agent_map))
                            .position(agentLocation));

                    markerAgentMapping.put(myMarker, agentDetails);

//                   dialog.dismiss();

                }


            }

            @Override
            public void failure(RetrofitError error) {
                System.out.print(error.toString());
            }
        });





    }

    @Override
    public boolean onMarkerClick(Marker marker) {

            manager = getSupportFragmentManager();
            trans = manager.beginTransaction();
            int rightIn = R.anim.slide_left_in;
            int rightOut = R.anim.slide_left_out;
       // getSupportFragmentManager().findFragmentById(R.id.map).getView().setVisibility(View.GONE);
//            trans.setCustomAnimations(rightIn,rightOut);
            //int container = R.id.main_container;
        Bundle bundle= new Bundle();
        bundle.putParcelable("values",markerAgentMapping.get(marker));
        Fragment1 fragment=new Fragment1();
        fragment.setArguments(bundle);

            trans.add(R.id.main_container, fragment, "fragment_1");
            trans.addToBackStack("fragment_1");
            trans.commit();
            return true;

    }

    // For VISA Agents
    public LinkedList<AllAgentModels> visa_list(List<AllAgentModels> agents)
    {
       // String agent = "speciality";
        LinkedList<AllAgentModels> visa_agents = new LinkedList<>();
        for(AllAgentModels parseObject: agents)
        {
            clubVisa(agents,parseObject,visa_agents);
        }
        return visa_agents;
    }

    public void clubVisa(List<AllAgentModels> agents,AllAgentModels agentDetail,List<AllAgentModels> visa_agents )
    {
        if(contains(visa_agents,agentDetail))
        {
            return;
        }
        for (AllAgentModels parseObject:agents)
        {
            if(parseObject.getSpeciality().equals(agentDetail.getSpeciality()))
            {


                agentDetail.setSpeciality( parseObject.getSpeciality().toString() + agentDetail.getSpeciality().toString());


            }
        }
        visa_agents.add(agentDetail);
    }



    //Club Agents if they have more than one Speciality
    public LinkedList<AllAgentModels> clubAgentsSpeciality(List<AllAgentModels> agentsDetails)
    {
        LinkedList<AllAgentModels> clubedAgents=new LinkedList<>();
        for(AllAgentModels parseObject:agentsDetails)
        {
        clubAgentSpeciality(agentsDetails,parseObject,clubedAgents);

        }
        return clubedAgents;
    }

    public void clubAgentSpeciality(List<AllAgentModels> agents,AllAgentModels agentDetail,List<AllAgentModels> clubedSpecializationAgents)
    {
        if(contains( clubedSpecializationAgents,agentDetail))
        {
           return ;
        }
     for(AllAgentModels parseObject:agents)
     {
      if(parseObject.getEmailid().equals(agentDetail.getEmailid()))
      {
          if(parseObject!=agentDetail)
          {
              agentDetail.setSpeciality(parseObject.getSpeciality().toString() + ", " + agentDetail.getSpeciality().toString());
          }
      }
     }
        clubedSpecializationAgents.add(agentDetail);
    }

    public boolean contains(List<AllAgentModels> clubedSpecializationAgents,AllAgentModels object)
    {
        for(AllAgentModels parseObject:clubedSpecializationAgents)
        {
            if(parseObject.getEmailid().equals(object.getEmailid()))
            {
                return true;
            }
        }
        return false;
    }

}

