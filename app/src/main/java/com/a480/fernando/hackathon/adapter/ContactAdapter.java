package com.a480.fernando.hackathon.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.a480.fernando.hackathon.AppConstant;
import com.a480.fernando.hackathon.ChatActivity;
import com.a480.fernando.hackathon.R;
import com.a480.fernando.hackathon.model.User;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Fernando on 27/04/2017.
 */

public class ContactAdapter extends BaseAdapter {

    private ArrayList<User> contacts;
    private Context context;

    public ContactAdapter(ArrayList<User> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        User contact = this.contacts.get(position);

        if(!contact.getFirstAlphabet()) {
            v = View.inflate(context, R.layout.contact_item, null);

            CircleImageView profile = (CircleImageView) v.findViewById(R.id.contact_image);
            TextView name = (TextView) v.findViewById(R.id.contact_name);
            TextView city = (TextView) v.findViewById(R.id.contact_city);
            TextView state = (TextView) v.findViewById(R.id.contact_state);
            Button email = (Button) v.findViewById(R.id.contact_email);
            Button call = (Button) v.findViewById(R.id.contact_call);

            Glide.with(context).load(contact.getImage()).into(profile);
            name.setText(contact.getName() + " " + contact.getSurname());
            city.setText(contact.getCity());
            state.setText(contact.getState());

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(AppConstant.UID, contact.getUid());
                    intent.putExtra(AppConstant.CHAT_ID, contact.getChatId());
                    context.startActivity(intent);
                }
            });

            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("message/rfc822");
                        intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{contact.getEmail()});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Hackathon");
                        intent = Intent.createChooser(intent, "Enviar email...");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(context, "No se ha podido enviar el email.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(context, "Para poder llamar debes dar permisos a la aplicación.", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
                        context.startActivity(intent);
                    }
                }
            });
        } else {
            v = View.inflate(context, R.layout.contact_separator_item, null);
            TextView letter = (TextView) v.findViewById(R.id.contact_separator_letter);
            letter.setText("" + contact.getName().toUpperCase().charAt(0));
        }

        return v;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

}
