package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.a480.fernando.hackathon.dao.ServiceDao;
import com.a480.fernando.hackathon.dao.UserDao;
import com.a480.fernando.hackathon.model.User;

/**
 * Created by Fernando on 16/03/2017.
 */

public class BaseActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private ActionBarDrawerToggle toggle;
    private boolean goBackEnabled;

    protected NavigationView navigationView;
    protected UserDao userDao = new UserDao();
    protected ServiceDao serviceDao = new ServiceDao();
    protected User user;
    protected DrawerLayout navigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String email = getUserEmail();

        if (email != null) {
            user = userDao.getUser(email);
        }
    }

    public void setToolBar(String title) {
        updateNavigation();

        goBackEnabled = false;

        toggle = new ActionBarDrawerToggle(this, navigation, R.string.open, R.string.close);

        navigation.addDrawerListener(toggle);
        toggle.syncState();

        toolBar = (Toolbar) findViewById(R.id.app_bar);
        toolBar.setNavigationIcon(R.drawable.event_text_icon);
        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
    }

    public void setCloseToolBar(String title) {
        cancelToolBar(title, R.drawable.close_icon);
    }

    public void setGoBackToolBar(String title) {
        cancelToolBar(title, 0);
    }

    private void cancelToolBar(String title, int icon) {
        goBackEnabled = true;
        toolBar = (Toolbar) findViewById(R.id.app_bar);
        if(icon != 0) {
            toolBar.setNavigationIcon(icon);
        }
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void updateNavigation() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.inflateHeaderView(R.layout.navigation);
        if(user == null) {
            navigationView.getHeaderView(0).findViewById(R.id.header_navigation).setVisibility(View.GONE);
            navigationView.getHeaderView(0).findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        } else {
            navigationView.getHeaderView(0).findViewById(R.id.header_navigation).setVisibility(View.VISIBLE);
            navigationView.getHeaderView(0).findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
            TextView nameMenu = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_navigation).findViewById(R.id.name_menu);
            nameMenu.setText(user.getName() + " " + user.getSurname());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(goBackEnabled) {
            onBackPressed();
            return true;
        } else if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void navigationItemClicked(View view) {
        Intent intent = null;
        switch(view.getId()) {
            case R.id.profile:
                intent = new Intent(BaseActivity.this, ProfileActivity.class);
                break;
            case R.id.networking:
                intent = new Intent(BaseActivity.this, ProfileActivity.class);
                break;
            case R.id.ask:
                intent = new Intent(BaseActivity.this, ProfileActivity.class);
                break;
            case R.id.augmented_reality:
                intent = new Intent(BaseActivity.this, AugmentedRealityActivity.class);
                break;
            case R.id.accreditation:
                intent = new Intent(BaseActivity.this, AccreditationActivity.class);
                break;
            case R.id.home:
                intent = new Intent(BaseActivity.this, HomeActivity.class);
                break;
            case R.id.news:
                intent = new Intent(BaseActivity.this, HomeActivity.class);
                break;
            case R.id.schedule:
                intent = new Intent(BaseActivity.this, HomeActivity.class);
                break;
            case R.id.speakers:
                intent = new Intent(BaseActivity.this, HomeActivity.class);
                break;
            case R.id.documentation:
                intent = new Intent(BaseActivity.this, HomeActivity.class);
                break;
            case R.id.maps:
                intent = new Intent(BaseActivity.this, HomeActivity.class);
                break;
            case R.id.services:
                intent = new Intent(BaseActivity.this, ServicesActivity.class);
                break;
            case R.id.notifications:
                intent = new Intent(BaseActivity.this, HomeActivity.class);
                break;
            case R.id.sponsors:
                intent = new Intent(BaseActivity.this, SponsorActivity.class);
                break;
            case R.id.about:
                intent = new Intent(BaseActivity.this, AboutActivity.class);
                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }

    public void inscription(View view) {
        Intent intent = new Intent(BaseActivity.this, InscriptionInfoActivity.class);
        startActivity(intent);
    }

    public User createUser() {
        User user = new User();
        EditText name = (EditText) findViewById(R.id.inscription_name);
        user.setName(name.getText().toString());
        EditText surname = (EditText) findViewById(R.id.inscription_surname);
        user.setSurname(surname.getText().toString());
        Spinner country = (Spinner) findViewById(R.id.inscription_country);
        user.setCountry(country.getSelectedItem().toString());
        Spinner state = (Spinner) findViewById(R.id.inscription_state);
        user.setState(state.getSelectedItem().toString());
        Spinner city = (Spinner) findViewById(R.id.inscription_city);
        user.setCity(city.getSelectedItem().toString());
        EditText postalCode = (EditText) findViewById(R.id.inscription_cp);
        user.setPostalCode(postalCode.getText().toString());
        EditText phoneNumber = (EditText) findViewById(R.id.inscription_phone);
        user.setPhoneNumber(phoneNumber.getText().toString());
        EditText website = (EditText) findViewById(R.id.inscription_website);
        user.setWebsite(website.getText().toString());
        EditText companyName = (EditText) findViewById(R.id.inscription_company);
        user.setCompanyName(companyName.getText().toString());
        EditText nif = (EditText) findViewById(R.id.inscription_nif);
        user.setNif(nif.getText().toString());
        Spinner sector = (Spinner) findViewById(R.id.inscription_sector);
        user.setSector(sector.getSelectedItem().toString());
        Spinner position = (Spinner) findViewById(R.id.inscription_position);
        user.setPosition(position.getSelectedItem().toString());
        Spinner department = (Spinner) findViewById(R.id.inscription_department);
        user.setDepartment(department.getSelectedItem().toString());
        CheckBox fact = (CheckBox) findViewById(R.id.inscription_fact);
        user.setFact(fact.isChecked());
        return user;
    }

    public String getUserEmail() {
        return AppSharedPreferences.getUser(getApplicationContext());
    }

}
