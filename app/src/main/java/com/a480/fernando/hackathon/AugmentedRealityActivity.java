package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

public class AugmentedRealityActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_augmented_reality);

        navigation = (DrawerLayout) findViewById(R.id.activity_augmented_reality);
        setToolbar("Realidad aumentada");
    }

    public void scan(View view) {
        Intent intent = new Intent(AugmentedRealityActivity.this, ScanActivity.class);
        startActivity(intent);
    }
}
