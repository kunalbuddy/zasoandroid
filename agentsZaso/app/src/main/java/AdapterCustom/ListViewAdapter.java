package AdapterCustom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.veeresh.parsegeo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import Models.FavouriteModel;

/**
 * Created by gajendrasingh on 12/23/2015.
 */
public class ListViewAdapter extends BaseAdapter {

    private  Context context;
    private LayoutInflater layoutInflater;
    private List<FavouriteModel>  listFav;


    public ListViewAdapter(Context context,List<FavouriteModel> listFav)
    {
        this.context=context;
        this.listFav=listFav;
        layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return listFav.size();
    }

    @Override
    public FavouriteModel getItem(int position) {
        return listFav.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_row, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
    FavouriteModel currentAgentData=getItem(position);
        mViewHolder.agentName.setText(currentAgentData.getFname()+currentAgentData.getLname());
        mViewHolder.ageLocality.setText(currentAgentData.getAddress());
       /* String url=currentAgentData.getPicurl();
        Picasso.with(context).load(url).into(mViewHolder.ivIcon);*/


        return convertView;
    }

    private class MyViewHolder {
        TextView agentName, ageLocality;
        ImageView ivIcon;

        public MyViewHolder(View item) {
            agentName = (TextView) item.findViewById(R.id.secondLineId);
            ageLocality = (TextView) item.findViewById(R.id.firstLineId);
            ivIcon = (ImageView) item.findViewById(R.id.iconId);
        }
    }
}
