package com.a480.fernando.hackathon;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

import com.a480.fernando.hackathon.model.Service;

import static com.a480.fernando.hackathon.AppConstant.LOCATION_REFRESH_DISTANCE;
import static com.a480.fernando.hackathon.AppConstant.LOCATION_REFRESH_TIME;
import static com.a480.fernando.hackathon.AppConstant.MY_PERMISSIONS_REQUEST_LOCATION;

public class ServiceRouteActivity extends BaseActivity {

    private Service service;
    private TextView coordinates;

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_route);

        this.service = this.serviceDao.getServiceByName(getIntent().getStringExtra(AppConstant.SERVICE_NAME));
        this.coordinates = (TextView) findViewById(R.id.coordinates);

        setCloseToolBar("Ruta a " + service.getName());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("LOCATION", "Lat: " + location.getLatitude() + " ; Long: " + location.getLongitude());
                coordinates.append("Lat: " + location.getLatitude() + " ; Long: " + location.getLongitude() + "\n");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        });
        showLocation();
    }

    public void showLocation() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (ActivityCompat.checkSelfPermission(ServiceRouteActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ServiceRouteActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Log.d("LOCATION", "Lat: " + location.getLatitude() + " ; Long: " + location.getLongitude());
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

}
