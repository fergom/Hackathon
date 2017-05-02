package com.a480.fernando.hackathon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.a480.fernando.hackathon.model.BreakingNew;

import java.net.URL;
import java.util.Calendar;

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

        breakingNewTitle.setText(breakingNew.getTitle());
        breakingNewTime.setText(getTime(breakingNew.getTime()));

        if(breakingNew.getImage() != null) {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                URL url = new URL(breakingNew.getImage());
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                breakingNewImage.setImageBitmap(bmp);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        setJustifiedText();
    }

    private void setJustifiedText() {
        String htmlText = "<html><body style=\"text-align:justify;color:#888888\"> %s </body></html>";
        String description = breakingNew.getInfo();
        description = description.replace("\n", "<br>");
        WebView webView = (WebView) findViewById(R.id.news_info_web);
        webView.loadData(String.format(htmlText, description), "text/html; charset=utf-8", "utf-8");
    }

    private String getTime(Calendar time) {
        String difference;
        long diff = Calendar.getInstance().getTime().getTime() - time.getTime().getTime();
        double minutes = diff / (1000 * 60);
        if (minutes < 60) {
            difference = minutes + " minutos";
        } else {
            long horas = (long) (minutes / 60);
            if(horas < 24) {
                difference = horas + " horas";
            } else {
                long dias = horas/24;
                difference = dias + " días.";
            }
        }
        return difference;
    }

    public void closeBreakingNewInfo(View view) {
        finish();
    }

}
