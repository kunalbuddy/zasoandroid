
package com.example.veeresh.parsegeo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Iterator;
import java.util.List;

import APIs.AgentPicEndPoint;
import APIs.AllAgentEndPoint;
import Models.AgentPicModel;
import Models.AllAgentModels;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.JacksonConverter;




public class Fragment1 extends Fragment {

private TextView textView1,textView2,textView3,textView4,textView5;
    LinearLayout linearLayout;
    ImageView imageView;
    AllAgentModels info;

    RestAdapter retrofit = new RestAdapter.Builder()
            .setEndpoint(MapsActivity.API).setConverter(new JacksonConverter()).build();
    AgentPicEndPoint agentPicEndPoint=retrofit.create(AgentPicEndPoint.class);




    public View onCreateView(LayoutInflater inf, ViewGroup vg, Bundle b){
      // Toast.makeText(getActivity(), getArguments().getParcelable("values").toString(), Toast.LENGTH_LONG).show();
         info=getArguments().getParcelable("values");
      //  textView1=(TextView)findViewById(R.id.textViewId1);

View rootView=inf.inflate(R.layout.fragment_1, vg, false);

        /*imageView=(ImageView)rootView.findViewById(R.id.imageViewId);
        linearLayout=(LinearLayout)rootView.findViewById(R.id.linearLayoutImageView);
*/



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

       // picassoLib();

        return rootView;

        // Simply inflate the View from the .xml file.
        //return inf.inflate(R.layout.fragment_1, vg, false);
    }
   /* public void picassoLib()
    {
        String email=info.getEmailid();
        agentPicEndPoint.getPicturesOfAgent(email, new Callback<List<AgentPicModel>>() {
            @Override
            public void success(List<AgentPicModel> agentPicModels, Response response) {
                Iterator<AgentPicModel> iterator=agentPicModels.iterator();
                while (iterator.hasNext())
                {
                    AgentPicModel url=iterator.next();
                    String urlPic=url.getUrl();

                    Picasso.with(getActivity())
                            .load(urlPic)
                            .into(imageView);

                    linearLayout.addView(imageView);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });
    }*/

}

