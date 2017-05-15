package com.a480.fernando.hackathon;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.a480.fernando.hackathon.adapter.ContactAdapter;
import com.a480.fernando.hackathon.model.User;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ContactsActivity extends BaseActivity {

    private MaterialSearchView searchView;
    private ListView contactsListView;
    private ArrayList<User> contacts;
    private TextView noContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contactos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        contactsListView = (ListView) findViewById(R.id.contacts_list_view);
        noContacts = (TextView) findViewById(R.id.no_contacts);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() { }

            @Override
            public void onSearchViewClosed() {
                setDefault();
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return onQueryTextChange(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()){
                    newText = newText.toLowerCase();
                    ArrayList<User> queryUsers = new ArrayList<User>();
                    for(User u: userDao.getUsers(matchDao.getUserMatches(user.getUid()))) {
                        if(u.getName().toLowerCase().contains(newText) || u.getSurname().toLowerCase().contains(newText) || u.getCity().toLowerCase().contains(newText) || u.getState().toLowerCase().contains(newText)) {
                            queryUsers.add(u);
                        }
                    }
                    contacts = queryUsers;
                    sortContacts();
                } else {
                    setDefault();
                }
                return true;
            }
        });

        setDefault();

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CALL_PHONE}, 1);
        }
    }

    private void setDefault() {
        contacts = userDao.getUsers(matchDao.getUserMatches(user.getUid()));
        sortContacts();
    }

    private void sortContacts() {
        Collections.sort(contacts, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if(o1.getName().compareTo(o2.getName()) == 0) {
                    return o1.getSurname().compareTo(o2.getSurname());
                } else {
                    return o1.getName().compareTo(o2.getName());
                }
            }
        });
        loadContacts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void loadContacts() {
        char first = ' ';
        ArrayList<User> contactsWithSeparator = new ArrayList<>();
        User separator;

        if(contacts.size() == 0) {
            noContacts.setVisibility(View.VISIBLE);
            contactsListView.setVisibility(View.GONE);

        } else {
            noContacts.setVisibility(View.GONE);
            contactsListView.setVisibility(View.VISIBLE);

            for(User c: contacts) {
                if(c.getName().toLowerCase().charAt(0) - first > 0) {
                    separator = new User();
                    separator.setName(c.getName());
                    separator.setFirstAlphabet(true);
                    contactsWithSeparator.add(separator);
                    first = c.getName().toLowerCase().charAt(0);
                }
                contactsWithSeparator.add(c);
            }

            contacts = contactsWithSeparator;

            ContactAdapter contactAdapter = new ContactAdapter(contacts, getApplicationContext());
            contactsListView.setAdapter(contactAdapter);
        }
    }
}
