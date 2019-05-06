package com.kushang_android_examples.qrscanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main extends AppCompatActivity {

    public static TextView resultTextview;
    Button scan_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextview = (TextView)findViewById(R.id.result_text);
        scan_btn = (Button)findViewById(R.id.btn_scan);

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ScanCodeActivity.class));
            }
        });
    }
}
