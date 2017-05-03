package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.a480.fernando.hackathon.adapter.FacilityAdapter;
import com.a480.fernando.hackathon.model.Service;

public class FacilitiesActivity extends BaseActivity {

    private ListView facilitiesListView;
    private FacilityAdapter facilityAdapter;

    private ImageView image;
    private ImageView closeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilities);

        image = (ImageView) findViewById(R.id.facility_image);
        closeImage = (ImageView) findViewById(R.id.close_image);

        navigation = (DrawerLayout) findViewById(R.id.activity_facilities);
        setToolbar("");
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("Mapas");
        ImageView toolbarRightImageView = (ImageView) findViewById(R.id.toolbar_right_icon);
        toolbarRightImageView.setImageResource(R.drawable.services_icon);
        toolbarRightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FacilitiesActivity.this, FacilitiesMapActivity.class);
                startActivity(intent);
            }
        });

        loadFacilities();
    }

    private void loadFacilities() {
        facilitiesListView = (ListView) findViewById(R.id.facilities_list);

        facilityAdapter = new FacilityAdapter(mapsDao.getFacilities(), getApplicationContext());
        facilitiesListView.setAdapter(facilityAdapter);
        facilitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service facility = mapsDao.getFacilityByName((String) view.getTag());
                if(facility.getType().equals(AppConstant.LOCATION)) {
                    Intent intent = new Intent(FacilitiesActivity.this, FacilitiesMapActivity.class);
                    intent.putExtra(AppConstant.FACILITY_NAME, facility.getName());
                    startActivity(intent);
                } else {
                    if(facility.getName().contains("Plano")) {
                        image.setImageResource(R.drawable.uji_map);
                    } else {
                        image.setImageResource(R.drawable.bus_tram);
                    }
                    image.setVisibility(View.VISIBLE);
                    closeImage.setVisibility(View.VISIBLE);
                    facilitiesListView.setAlpha(0.3f);
                }
            }
        });
    }

    public void closeImage(View view) {
        image.setVisibility(View.GONE);
        closeImage.setVisibility(View.GONE);
        facilitiesListView.setAlpha(1);
    }

}
