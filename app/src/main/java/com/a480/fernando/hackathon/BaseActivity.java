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
import android.widget.TextView;

import com.a480.fernando.hackathon.dao.DocumentDao;
import com.a480.fernando.hackathon.dao.FeedbackDao;
import com.a480.fernando.hackathon.dao.InfoDao;
import com.a480.fernando.hackathon.dao.MapsDao;
import com.a480.fernando.hackathon.dao.SpeakersDao;
import com.a480.fernando.hackathon.dao.UserDao;
import com.a480.fernando.hackathon.model.User;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Fernando on 16/03/2017.
 */

public class BaseActivity extends AppCompatActivity implements CallbackActivity {

    private Toolbar toolBar;
    private ActionBarDrawerToggle toggle;
    private boolean goBackEnabled;

    protected NavigationView navigationView;
    protected UserDao userDao = new UserDao();
    final static protected MapsDao mapsDao = new MapsDao();
    final static protected InfoDao infoDao = new InfoDao();
    final static protected FeedbackDao feedbackDao = new FeedbackDao();
    final static protected DocumentDao documentDao = new DocumentDao();
    final static protected SpeakersDao speakersDao = new SpeakersDao();
    protected User user;
    protected DrawerLayout navigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            user = userDao.getUser();
            if(user.getName() == null) {
                userDao.onAuthenticated(BaseActivity.this);
            }
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
        if(navigationView != null) {
            navigationView.inflateHeaderView(R.layout.navigation);
            if(user == null) {
                navigationView.getHeaderView(0).findViewById(R.id.navigation_login).setVisibility(View.VISIBLE);
                navigationView.getHeaderView(0).findViewById(R.id.header_navigation).setVisibility(View.GONE);
                navigationView.getHeaderView(0).findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
            } else {
                navigationView.getHeaderView(0).findViewById(R.id.navigation_login).setVisibility(View.GONE);
                navigationView.getHeaderView(0).findViewById(R.id.header_navigation).setVisibility(View.VISIBLE);
                navigationView.getHeaderView(0).findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
                TextView nameMenu = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_navigation).findViewById(R.id.name_menu);
                nameMenu.setText(user.getName() + " " + user.getSurname());
            }
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
            case R.id.navigation_login:
                intent = new Intent(BaseActivity.this, LoginActivity.class);
                break;
            case R.id.profile:
                intent = new Intent(BaseActivity.this, ProfileActivity.class);
                break;
            case R.id.networking:
                intent = new Intent(BaseActivity.this, ProfileActivity.class);
                break;
            case R.id.ask:
                intent = new Intent(BaseActivity.this, AskSpeakerListActivity.class);
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
                intent = new Intent(BaseActivity.this, SpeakersActivity.class);
                break;
            case R.id.documentation:
                intent = new Intent(BaseActivity.this, DocumentsActivity.class);
                break;
            case R.id.maps:
                intent = new Intent(BaseActivity.this, FacilitiesActivity.class);
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

    @Override
    public void onDataLoaded() {
        user = userDao.getUser();
        updateNavigation();
    }
}
