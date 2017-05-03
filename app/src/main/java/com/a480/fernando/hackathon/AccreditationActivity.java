package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;

public class AccreditationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accreditation);

        navigation = (DrawerLayout) findViewById(R.id.activity_accreditation);

        setToolBar("Acreditación");

        if(user != null) {
            TextView name = (TextView) findViewById(R.id.accreditation_name);
            TextView company = (TextView) findViewById(R.id.accreditation_company);
            TextView nameMenu = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_navigation).findViewById(R.id.name_menu);
            name.setText(user.getName() + " " + user.getSurname());
            nameMenu.setText(user.getName() + " " + user.getSurname());
            company.setText(user.getCompanyName());
        }
    }
}
