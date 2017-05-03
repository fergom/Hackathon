package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.a480.fernando.hackathon.model.BreakingNew;
import com.bumptech.glide.Glide;

import static com.a480.fernando.hackathon.AppConstant.GREY_HEX;
import static com.a480.fernando.hackathon.AppConstant.getTime;

public class BreakingNewInfoActivity extends BaseActivity {

    private BreakingNew breakingNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breaking_new_info);

        breakingNew = newsDao.getNew(getIntent().getExtras().getString(AppConstant.BREAKING_NEW_TITLE));

        ImageView breakingNewImage = (ImageView) findViewById(R.id.news_info_image);
        TextView breakingNewTime = (TextView) findViewById(R.id.news_info_time);
        TextView breakingNewTitle = (TextView) findViewById(R.id.news_info_title);
        WebView webView = (WebView) findViewById(R.id.news_info_web);

        breakingNewTitle.setText(breakingNew.getTitle());
        breakingNewTime.setText(getTime(breakingNew.getTime()));

        if(breakingNew.getImage() != null) {
            Glide.with(getApplicationContext()).load(breakingNew.getImage()).into(breakingNewImage);
        }

        setJustifiedText(webView, breakingNew.getInfo().replace("\n", "<br>"), GREY_HEX);
    }

    public void closeBreakingNewInfo(View view) {
        finish();
    }

}
