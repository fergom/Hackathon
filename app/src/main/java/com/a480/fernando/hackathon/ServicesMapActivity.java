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
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;

public class ServicesMapActivity extends BaseActivity implements OnMapReadyCallback, OnMarkerClickListener, OnMapClickListener {

    private GoogleMap map;
    private double defaultLatitude = 39.9863563;
    private double defaultLongitude = -0.0513246;
    private float defaultZoom = 14.0f;
    private float nearZoom = 18.0f;
    private String selectedServiceName = null;

    private RelativeLayout serviceInfoMap;
    private LinearLayout buttonInfoMap;
    private TextView serviceNameMap;
    private TextView serviceAddressMap;
    private TextView servicePhoneMap;

    private LinkedList<MarkerOptions> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_services);

        this.markers = new LinkedList<MarkerOptions>();

        this.serviceInfoMap = (RelativeLayout) findViewById(R.id.service_info_map);
        this.buttonInfoMap = (LinearLayout) findViewById(R.id.button_info_map);
        this.serviceInfoMap.setVisibility(View.GONE);
        this.buttonInfoMap.setVisibility(View.GONE);
        this.serviceNameMap = (TextView) findViewById(R.id.service_name_map);
        this.serviceAddressMap = (TextView) findViewById(R.id.service_address_map);
        this.servicePhoneMap = (TextView) findViewById(R.id.service_phone_map);

        this.selectedServiceName = getIntent().getStringExtra(AppConstant.SERVICE_NAME);

        this.navigation = (DrawerLayout) findViewById(R.id.activity_map_services);
        setToolBar("");
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("Servicios");
        ImageView toolbarRightImageView = (ImageView) findViewById(R.id.toolbar_right_icon);
        toolbarRightImageView.setImageResource(R.drawable.list_icon);
        toolbarRightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServicesMapActivity.this, ServicesActivity.class);
                startActivity(intent);
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.services_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(defaultLatitude,defaultLongitude), defaultZoom));
        this.map.setOnMarkerClickListener(this);
        this.map.setOnMapClickListener(this);
        loadLocations();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Service service = null;
        if(this.selectedServiceName != null) {
            service = this.mapsDao.getServiceByName(this.selectedServiceName);
            for(MarkerOptions mark: this.markers) {
                if(mark.getTitle().equals(this.selectedServiceName)) {
                    this.map.addMarker(createMarker(service, getIcon(service.getType())));
                }
            }
        }
        this.selectedServiceName = marker.getTitle();
        service = this.mapsDao.getServiceByName(this.selectedServiceName);
        setBottomInformation(service);
        marker.setIcon(BitmapDescriptorFactory.fromResource(getActiveIcon(service.getType())));
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        this.serviceInfoMap.setVisibility(View.GONE);
        this.buttonInfoMap.setVisibility(View.GONE);
    }

    private void loadLocations() {
        int icon;
        MarkerOptions marker;
        for(Service service: mapsDao.getServices()) {
            if(selectedServiceName != null && service.getName().equals(selectedServiceName)) {
                icon = getActiveIcon(service.getType());
                this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(service.getLatitude(),service.getLongitude()) , nearZoom));
                setBottomInformation(service);
            } else {
                icon = getIcon(service.getType());
            }
            marker = createMarker(service, icon);
            this.map.addMarker(marker);
            this.markers.add(marker);
        }
    }

    private MarkerOptions createMarker(Service service, int icon) {
        return new MarkerOptions().position(new LatLng(service.getLatitude(), service.getLongitude())).title(service.getName()).icon(BitmapDescriptorFactory.fromResource(icon));
    }

    private int getIcon(String serviceType) {
        switch (serviceType) {
            case AppConstant.HOTELS:
                return R.drawable.hotel_not_selected_icon;
            case AppConstant.OTHERS:
                return R.drawable.other_service_icon;
        }
        return 0;
    }

    private int getActiveIcon(String serviceType) {
        switch (serviceType) {
            case AppConstant.HOTELS:
                return R.drawable.hotel_selected_icon;
            case AppConstant.OTHERS:
                return R.drawable.other_service_selected_icon;
        }
        return 0;
    }

    private void setBottomInformation(Service service) {
        this.serviceNameMap.setText(service.getName());
        this.serviceAddressMap.setText(service.getAddress());
        this.servicePhoneMap.setText(service.getPhone());
        this.serviceInfoMap.setVisibility(View.VISIBLE);
        this.buttonInfoMap.setVisibility(View.VISIBLE);
    }

    public void moreInfo(View view) {
        Intent intent = new Intent(ServicesMapActivity.this, ServiceMapDetailActivity.class);
        intent.putExtra(AppConstant.SERVICE_NAME, this.selectedServiceName);
        startActivity(intent);
    }

}
