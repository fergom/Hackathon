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

public class FacilityMapDetailActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private float nearZoom = 18.0f;
    private Service facility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_map_detail);

        this.facility = this.mapsDao.getFacilityByName(getIntent().getStringExtra(AppConstant.FACILITY_NAME));

        TextView name = (TextView) findViewById(R.id.facility_name_detail);
        TextView address = (TextView) findViewById(R.id.facility_address_detail);

        name.setText(this.facility.getName());
        address.setText(this.facility.getAddress());

        setJustifiedText();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.detailed_facility_map);
        mapFragment.getMapAsync(this);
    }

    private void setJustifiedText() {
        String htmlText = "<html><body style=\"text-align:justify;color:#888888\"> %s </body></html>";
        String description = getString(R.string.service_descrption);
        description = description.replace("\n","<br>");
        WebView webView = (WebView) findViewById(R.id.facility_description_detail);
        webView.loadData(String.format(htmlText, description), "text/html; charset=utf-8", "utf-8");
    }

    public void goBack(View view) {
        finish();
    }

    public void route(View view) {
        Intent intent = new Intent(FacilityMapDetailActivity.this, FacilityRouteActivity.class);
        intent.putExtra(AppConstant.FACILITY_NAME, this.facility.getName());
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(this.facility.getLatitude(),this.facility.getLongitude()), nearZoom));
        this.map.addMarker(new MarkerOptions().position(new LatLng(facility.getLatitude(), facility.getLongitude())).title(facility.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
    }

}