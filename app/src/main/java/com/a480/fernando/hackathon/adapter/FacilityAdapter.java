package com.a480.fernando.hackathon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a480.fernando.hackathon.AppConstant;
import com.a480.fernando.hackathon.R;
import com.a480.fernando.hackathon.model.Service;

import java.util.ArrayList;

/**
 * Created by Fernando on 05/04/2017.
 */

public class FacilityAdapter extends BaseAdapter {

    private ArrayList<Service> facilities;
    private Context context;

    public FacilityAdapter(ArrayList<Service> facilities, Context context) {
        this.facilities = facilities;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.facility_item, null);
        ImageView mapsFacility = (ImageView) v.findViewById(R.id.facility_maps_image);
        TextView name = (TextView) v.findViewById(R.id.facility_name);

        Service service = facilities.get(position);
        name.setText(service.getName());
        if(service.getType().equals(AppConstant.LOCATION)) {
            mapsFacility.setImageResource(R.drawable.maps_screenshot_location);
        } else {
            mapsFacility.setImageResource(R.drawable.uji_map);
        }

        v.setTag(facilities.get(position).getName());
        return v;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return facilities.get(position);
    }

    @Override
    public int getCount() {
        return facilities.size();
    }

}
