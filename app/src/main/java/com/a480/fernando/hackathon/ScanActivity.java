package com.a480.fernando.hackathon;

import android.os.Bundle;

public class ScanActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        setGoBackToolbar("Escanear");
    }
}
