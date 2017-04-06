package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.a480.fernando.hackathon.adapter.ServiceAdapter;

public class ServicesActivity extends BaseActivity {

    private ListView servicesListView;
    private static ServiceAdapter serviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        navigation = (DrawerLayout) findViewById(R.id.activity_services);
        setToolBar("");
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("Servicios");
        ImageView toolbarRightImageView = (ImageView) findViewById(R.id.toolbar_right_icon);
        toolbarRightImageView.setImageResource(R.drawable.services_icon);
        toolbarRightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServicesActivity.this, ServicesMapActivity.class);
                startActivity(intent);
            }
        });

        loadServices();
    }

    private void loadServices() {
        servicesListView = (ListView) findViewById(R.id.services_list);

        serviceAdapter = new ServiceAdapter(mapsDao.getServices(), getApplicationContext());
        servicesListView.setAdapter(serviceAdapter);
        servicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ServicesActivity.this, ServicesMapActivity.class);
                intent.putExtra(AppConstant.SERVICE_NAME, (String) view.getTag());
                startActivity(intent);
            }
        });
    }

}
