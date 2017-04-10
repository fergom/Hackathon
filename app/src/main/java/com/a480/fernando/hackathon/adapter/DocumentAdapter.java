package com.a480.fernando.hackathon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a480.fernando.hackathon.R;
import com.a480.fernando.hackathon.model.Document;

import java.util.ArrayList;

/**
 * Created by Fernando on 07/04/2017.
 */

public class DocumentAdapter extends BaseAdapter {

    private ArrayList<Document> documents;
    private Context context;

    public DocumentAdapter(ArrayList<Document> documents, Context context) {
        this.documents = documents;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.document_item, null);
        TextView title = (TextView) v.findViewById(R.id.document_title);

        Document document = documents.get(position);
        title.setText(document.getTitle());

        v.setTag(documents.get(position).getTitle());
        return v;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return documents.get(position);
    }

    @Override
    public int getCount() {
        return documents.size();
    }

}