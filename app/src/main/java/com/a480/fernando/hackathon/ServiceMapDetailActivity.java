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

public class ServiceMapDetailActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private float nearZoom = 18.0f;
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

        setJustifiedText();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.detailed_service_map);
        mapFragment.getMapAsync(this);
    }

    private void setJustifiedText() {
        String htmlText = "<html><body style=\"text-align:justify;color:#888888\"> %s </body></html>";
        String description = service.getDescription();
        description = description.replace("\n","<br>");
        WebView webView = (WebView) findViewById(R.id.service_description_detail);
        webView.loadData(String.format(htmlText, description), "text/html; charset=utf-8", "utf-8");
    }

    public void goBack(View view) {
        finish();
    }

    public void route(View view) {
        Intent intent = new Intent(ServiceMapDetailActivity.this, ServiceRouteActivity.class);
        intent.putExtra(AppConstant.SERVICE_NAME, this.service.getName());
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(this.service.getLatitude(),this.service.getLongitude()), nearZoom));
        this.map.addMarker(new MarkerOptions().position(new LatLng(service.getLatitude(), service.getLongitude())).title(service.getName()).icon(BitmapDescriptorFactory.fromResource(getIcon(service.getType()))));
    }

    private int getIcon(String type) {
        switch (type) {
            case AppConstant.HOTELS:
                return R.drawable.hotel_selected_icon;
            case AppConstant.OTHERS:
                return R.drawable.other_service_selected_icon;
        }
        return 0;
    }

}
