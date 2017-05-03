package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a480.fernando.hackathon.model.Service;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;

import static com.a480.fernando.hackathon.AppConstant.DEFAULT_LATITUDE;
import static com.a480.fernando.hackathon.AppConstant.DEFAULT_LONGITUDE;
import static com.a480.fernando.hackathon.AppConstant.DEFAULT_ZOOM;
import static com.a480.fernando.hackathon.AppConstant.NEAR_ZOOM;

public class FacilitiesMapActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private GoogleMap map;
    private String selectedFacilityName = null;

    private RelativeLayout facilityInfoMap;
    private LinearLayout buttonInfoMap;
    private TextView facilityNameMap;
    private TextView facilityAddressMap;

    private LinkedList<MarkerOptions> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilities_map);

        this.markers = new LinkedList<MarkerOptions>();

        this.facilityInfoMap = (RelativeLayout) findViewById(R.id.facility_info_map);
        this.buttonInfoMap = (LinearLayout) findViewById(R.id.button_info_map);
        this.facilityInfoMap.setVisibility(View.GONE);
        this.buttonInfoMap.setVisibility(View.GONE);
        this.facilityNameMap = (TextView) findViewById(R.id.facility_name_map);
        this.facilityAddressMap = (TextView) findViewById(R.id.facility_address_map);

        this.selectedFacilityName = getIntent().getStringExtra(AppConstant.FACILITY_NAME);

        this.navigation = (DrawerLayout) findViewById(R.id.activity_map_facilities);
        setToolbar("");
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("Servicios");
        ImageView toolbarRightImageView = (ImageView) findViewById(R.id.toolbar_right_icon);
        toolbarRightImageView.setImageResource(R.drawable.list_icon);
        toolbarRightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FacilitiesMapActivity.this, FacilitiesActivity.class);
                startActivity(intent);
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.facilities_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(DEFAULT_LATITUDE,DEFAULT_LONGITUDE), DEFAULT_ZOOM));
        this.map.setOnMarkerClickListener(this);
        this.map.setOnMapClickListener(this);
        loadLocations();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Service facility = null;
        if(this.selectedFacilityName != null) {
            facility = this.mapsDao.getFacilityByName(this.selectedFacilityName);
            for(MarkerOptions mark: this.markers) {
                if(mark.getTitle().equals(this.selectedFacilityName)) {
                    this.map.addMarker(createMarker(facility, R.drawable.event_location_icon));
                }
            }
        }
        this.selectedFacilityName = marker.getTitle();
        facility = this.mapsDao.getFacilityByName(this.selectedFacilityName);
        setBottomInformation(facility);
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.location));
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        this.facilityInfoMap.setVisibility(View.GONE);
        this.buttonInfoMap.setVisibility(View.GONE);
    }

    private void loadLocations() {
        int icon;
        MarkerOptions marker;
        for(Service facility: mapsDao.getFacilities()) {
            if(selectedFacilityName != null && facility.getName().equals(selectedFacilityName)) {
                icon = R.drawable.location;
                this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(facility.getLatitude(),facility.getLongitude()) , NEAR_ZOOM));
                setBottomInformation(facility);
            } else {
                icon = R.drawable.event_location_icon;
            }
            marker = createMarker(facility, icon);
            this.map.addMarker(marker);
            this.markers.add(marker);
        }
    }

    private MarkerOptions createMarker(Service facility, int icon) {
        return new MarkerOptions().position(new LatLng(facility.getLatitude(), facility.getLongitude())).title(facility.getName()).icon(BitmapDescriptorFactory.fromResource(icon));
    }

    private void setBottomInformation(Service facility) {
        this.facilityNameMap.setText(facility.getName());
        this.facilityAddressMap.setText(facility.getAddress());
        this.facilityInfoMap.setVisibility(View.VISIBLE);
        this.buttonInfoMap.setVisibility(View.VISIBLE);
    }

    public void moreInfo(View view) {
        Intent intent = new Intent(FacilitiesMapActivity.this, FacilityMapDetailActivity.class);
        intent.putExtra(AppConstant.FACILITY_NAME, this.selectedFacilityName);
        startActivity(intent);
    }

}
