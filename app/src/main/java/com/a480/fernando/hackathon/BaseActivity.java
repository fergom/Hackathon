package com.a480.fernando.hackathon;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
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
import android.webkit.WebView;
import android.widget.TextView;

import com.a480.fernando.hackathon.dao.ChatDao;
import com.a480.fernando.hackathon.dao.DocumentDao;
import com.a480.fernando.hackathon.dao.EventsDao;
import com.a480.fernando.hackathon.dao.FeedbackDao;
import com.a480.fernando.hackathon.dao.InfoDao;
import com.a480.fernando.hackathon.dao.MapsDao;
import com.a480.fernando.hackathon.dao.MatchDao;
import com.a480.fernando.hackathon.dao.NewsDao;
import com.a480.fernando.hackathon.dao.NotificationsDao;
import com.a480.fernando.hackathon.dao.SpeakersDao;
import com.a480.fernando.hackathon.dao.TokenDao;
import com.a480.fernando.hackathon.dao.UserDao;
import com.a480.fernando.hackathon.model.User;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.a480.fernando.hackathon.singleton.Singleton.getChatDaoInstance;
import static com.a480.fernando.hackathon.singleton.Singleton.getDocumentDaoInstance;
import static com.a480.fernando.hackathon.singleton.Singleton.getEventsDaoInstance;
import static com.a480.fernando.hackathon.singleton.Singleton.getFeedbackDaoInstance;
import static com.a480.fernando.hackathon.singleton.Singleton.getInfoDaoInstance;
import static com.a480.fernando.hackathon.singleton.Singleton.getMapsDaoInstance;
import static com.a480.fernando.hackathon.singleton.Singleton.getMatchDaoInstance;
import static com.a480.fernando.hackathon.singleton.Singleton.getNewsDaoInstance;
import static com.a480.fernando.hackathon.singleton.Singleton.getNotificationsDaoInstance;
import static com.a480.fernando.hackathon.singleton.Singleton.getSpeakersDaoInstance;
import static com.a480.fernando.hackathon.singleton.Singleton.getTokenDaoInstance;
import static com.a480.fernando.hackathon.singleton.Singleton.getUserDaoInstance;

/**
 * Created by Fernando on 16/03/2017.
 */

public class BaseActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    protected ActionBarDrawerToggle toggle;
    private boolean goBackEnabled;

    protected NavigationView navigationView;

    protected UserDao userDao = getUserDaoInstance();
    final static protected MapsDao mapsDao = getMapsDaoInstance();
    final static protected InfoDao infoDao = getInfoDaoInstance();
    final static protected FeedbackDao feedbackDao = getFeedbackDaoInstance();
    final static protected DocumentDao documentDao = getDocumentDaoInstance();
    final static protected SpeakersDao speakersDao = getSpeakersDaoInstance();
    final static protected NewsDao newsDao = getNewsDaoInstance();
    final static protected EventsDao eventsDao = getEventsDaoInstance();
    final static protected NotificationsDao notificationsDao = getNotificationsDaoInstance();
    final static protected MatchDao matchDao = getMatchDaoInstance();
    final static protected TokenDao tokenDao = getTokenDaoInstance();
    final static protected ChatDao chatDao = getChatDaoInstance();

    protected User user;
    protected DrawerLayout navigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!checkConn()) {
            Intent intent = new Intent(BaseActivity.this, NoConnectionActivity.class);
            startActivity(intent);
            finish();
        }

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            user = userDao.getUser();
        }
    }

    public boolean checkConn() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMgr.getActiveNetworkInfo() != null;
    }

    public void setToolbar(String title) {
        updateNavigation();

        goBackEnabled = false;

        toggle = new ActionBarDrawerToggle(this, navigation, R.string.open, R.string.close);

        navigation.addDrawerListener(toggle);
        toggle.syncState();

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setNavigationIcon(R.drawable.event_text_icon);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
    }

    public void setCloseToolbar(String title) {
        cancelToolbar(title, R.drawable.close_icon);
    }

    public void setGoBackToolbar(String title) {
        cancelToolbar(title, 0);
    }

    private void cancelToolbar(String title, int icon) {
        goBackEnabled = true;
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        if(icon != 0) {
            toolbar.setNavigationIcon(icon);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    protected void updateNavigation() {
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
                CircleImageView imageMenu = (CircleImageView)  navigationView.getHeaderView(0).findViewById(R.id.header_navigation).findViewById(R.id.image_menu);
                TextView nameMenu = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_navigation).findViewById(R.id.name_menu);
                nameMenu.setText(user.getName().toUpperCase() + " " + user.getSurname().toUpperCase());
                Glide.with(getApplicationContext()).load(user.getImage()).into(imageMenu);
                if(user.isSpeaker()) {
                    navigationView.getHeaderView(0).findViewById(R.id.my_questions).setVisibility(View.VISIBLE);
                    navigationView.getHeaderView(0).findViewById(R.id.contacts).setVisibility(View.GONE);
                    navigationView.getHeaderView(0).findViewById(R.id.networking).setVisibility(View.GONE);
                    navigationView.getHeaderView(0).findViewById(R.id.ask).setVisibility(View.GONE);
                    navigationView.getHeaderView(0).findViewById(R.id.augmented_reality).setVisibility(View.GONE);
                    navigationView.getHeaderView(0).findViewById(R.id.accreditation).setVisibility(View.GONE);
                    navigationView.getHeaderView(0).findViewById(R.id.speakers).setVisibility(View.GONE);
                } else {
                    navigationView.getHeaderView(0).findViewById(R.id.my_questions).setVisibility(View.GONE);
                    navigationView.getHeaderView(0).findViewById(R.id.contacts).setVisibility(View.VISIBLE);
                    navigationView.getHeaderView(0).findViewById(R.id.networking).setVisibility(View.VISIBLE);
                    navigationView.getHeaderView(0).findViewById(R.id.ask).setVisibility(View.VISIBLE);
                    navigationView.getHeaderView(0).findViewById(R.id.augmented_reality).setVisibility(View.VISIBLE);
                    navigationView.getHeaderView(0).findViewById(R.id.accreditation).setVisibility(View.VISIBLE);
                    navigationView.getHeaderView(0).findViewById(R.id.speakers).setVisibility(View.VISIBLE);
                }
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
            case R.id.my_questions:
                intent = new Intent(BaseActivity.this, MyQuestionsActivity.class);
                break;
            case R.id.profile:
                intent = new Intent(BaseActivity.this, ProfileActivity.class);
                break;
            case R.id.contacts:
                intent = new Intent(BaseActivity.this, ContactsActivity.class);
                break;
            case R.id.networking:
                intent = new Intent(BaseActivity.this, NetworkingActivity.class);
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
                intent = new Intent(BaseActivity.this, BreakingNewsActivity.class);
                break;
            case R.id.schedule:
                intent = new Intent(BaseActivity.this, ScheduleActivity.class);
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
                intent = new Intent(BaseActivity.this, NotificationsActivity.class);
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

    public void setJustifiedText(WebView webView, String text, String color) {
        String htmlText = "<html><body style=\"text-align:justify;color:" + color + "\"> %s </body></html>";
        text = text.replace("\n", "<br>");
        webView.loadData(String.format(htmlText, text), "text/html; charset=utf-8", "utf-8");
    }

    public void goBack(View view) {
        finish();
    }

    public void setToken() {
        tokenDao.setToken(FirebaseInstanceId.getInstance().getToken());
    }

}
