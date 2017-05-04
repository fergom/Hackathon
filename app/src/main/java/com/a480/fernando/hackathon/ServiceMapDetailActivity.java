package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.a480.fernando.hackathon.model.Service;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.a480.fernando.hackathon.AppConstant.GREY_HEX;
import static com.a480.fernando.hackathon.AppConstant.NEAR_ZOOM;

public class ServiceMapDetailActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_map_detail);

        this.service = this.mapsDao.getServiceByName(getIntent().getStringExtra(AppConstant.SERVICE_NAME));

        TextView name = (TextView) findViewById(R.id.service_name_detail);
        TextView address = (TextView) findViewById(R.id.service_address_detail);
        TextView phone = (TextView) findViewById(R.id.service_phone_detail);

        name.setText(this.service.getName());
        address.setText(this.service.getAddress());
        phone.setText(this.service.getPhone());

        WebView webView = (WebView) findViewById(R.id.service_description_detail);
        setJustifiedText(webView, service.getDescription().replace("\n","<br>"), GREY_HEX);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.detailed_service_map);
        mapFragment.getMapAsync(this);
    }

    public void route(View view) {
        Intent intent = new Intent(ServiceMapDetailActivity.this, ServiceRouteActivity.class);
        intent.putExtra(AppConstant.SERVICE_NAME, this.service.getName());
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(this.service.getLatitude(),this.service.getLongitude()), NEAR_ZOOM));
        this.map.addMarker(new MarkerOptions().position(new LatLng(service.getLatitude(), service.getLongitude())).title(service.getName()).icon(BitmapDescriptorFactory.fromResource(getIcon(service.getType()))));
    }

    private int getIcon(String type) {
        switch (type) {
            case AppConstant.HOTELS:
                return R.drawable.hotel_selected_icon;
            case AppConstant.SHOP:
                return R.drawable.shop_selected_icon;
            case AppConstant.RESTAURANT:
                return R.drawable.restaurant_selected_icon;
            case AppConstant.OTHERS:
                return R.drawable.other_service_selected_icon;
        }
        return 0;
    }

}
