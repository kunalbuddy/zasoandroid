package com.example.veeresh.parsegeo;



import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import APIs.AgentPicEndPoint;
import APIs.AllAgentEndPoint;
import Models.AllAgentModels;
import Models.PositionModel;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.JacksonConverter;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener {
    String API = "http://192.168.0.109:8081";

    PositionModel loc=null;
    public double latitude;
    public double longitude;
    private GoogleMap mMap;
    LocationManager location;
    Marker myMarker;
   ProgressDialog dialog;
    FragmentManager manager ;
    FragmentTransaction trans;


    RestAdapter retrofit = new RestAdapter.Builder()
            .setEndpoint(API).setConverter(new JacksonConverter()).build();
    AgentPicEndPoint agentPicEndPoint=retrofit.create(AgentPicEndPoint.class);
    AllAgentEndPoint allAgentEndPoint=retrofit.create(AllAgentEndPoint.class);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.agents_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        mMap.setOnMarkerClickListener(MapsActivity.this);



    }

  /*  public  void  getAllAgentInfo()
    {
        allAgentEndPoint.getAllAgent(new Callback<AllAgentEndPoint>() {
            @Override
            public void success(AllAgentEndPoint allAgentEndPoint, Response response) {
            MapsActivity.this.allAgentEndPoint=allAgentEndPoint;
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }*/

    @Override
    protected void onResume()
    {
        super.onResume();
        setUpMapIfNeeded();
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
        dialog.dismiss();
    }

    private void setUpMap() {
        /*ParseGeoPoint userLocation = new ParseGeoPoint();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Agents");
        query.whereNear("location", userLocation);
        query.setLimit(10);*/
        mMap.setMyLocationEnabled(true);

        dialog = new ProgressDialog(MapsActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        allAgentEndPoint.getAllAgent(new Callback<List<AllAgentModels>>() {
            @Override
            public void success(List<AllAgentModels> agentModelsList, Response response) {

                   dialog.dismiss();
                List<AllAgentModels> clubbedSpecAgents = clubAgentsSpeciality(agentModelsList);
                // List<AllAgentModels> clubbedVisaAgents = visa_list(agentModelsList);

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

                                    /*.title(agentDetails.getSpeciality(speciality) + " ")*/
                           /* .title(agentDetails.getSpeciality()) */
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_agent_map))
                            .position(agentLocation));

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
        if(marker.equals(myMarker))
        {
            manager = getSupportFragmentManager();
            trans = manager.beginTransaction();
            int rightIn = R.anim.slide_left_in;
            int rightOut = R.anim.slide_left_out;
//            trans.setCustomAnimations(rightIn,rightOut);
            //int container = R.id.main_container;
            trans.add(R.id.main_container, new Fragment1(), "fragment_1");
            trans.addToBackStack("fragment_1");
            trans.commit();
            return true;
        }
        /*else{
            Toast.makeText(this,"Click on any marker",Toast.LENGTH_LONG).show();
        }*/
       else
            return false;
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
