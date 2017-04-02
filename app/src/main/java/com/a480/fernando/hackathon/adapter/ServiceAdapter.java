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
 * Created by Fernando on 29/03/2017.
 */

public class ServiceAdapter extends BaseAdapter {

    private ArrayList<Service> services;
    private Context context;

    public ServiceAdapter(ArrayList<Service> services, Context context) {
        this.services = services;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.service_item, null);
        ImageView mapsService = (ImageView) v.findViewById(R.id.service_maps_image);
        TextView name = (TextView) v.findViewById(R.id.service_name);
        TextView address = (TextView) v.findViewById(R.id.service_address);
        TextView phone = (TextView) v.findViewById(R.id.service_phone);
        TextView type = (TextView) v.findViewById(R.id.service_type);

        Service service = services.get(position);
        name.setText(service.getName());
        address.setText(service.getAddress());
        phone.setText(service.getPhone());
        type.setText(service.getType());
        if(service.getType().equals(AppConstant.HOTELS)) {
            mapsService.setImageResource(R.drawable.maps_screenshot_hotel);
        } else {
            mapsService.setImageResource(R.drawable.maps_screenshot_other);
        }

        v.setTag(services.get(position).getName());

        return v;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return services.get(position);
    }

    @Override
    public int getCount() {
        return services.size();
    }
}
