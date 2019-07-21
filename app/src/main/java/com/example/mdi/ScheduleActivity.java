package com.example.mdi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.sql.BatchUpdateException;

public class ScheduleActivity extends AppCompatActivity {

    private int year;
    private int month;
    private int dayOfMonth;

    private TextView txtV1;
    private TextView txtV2;
    private TextView txtV3;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        final Intent intent = getIntent();
        year = intent.getIntExtra("year", 0);
        month = intent.getIntExtra("month", 0);
        dayOfMonth = intent.getIntExtra("dayOfMonth", 0);

        txtV1 = (TextView) findViewById(R.id.txtV1);
        String date = "" + "" + year + "년 " + month + "월 " + dayOfMonth + "일";
        txtV1.setText(date);

        txtV2 = (TextView) findViewById(R.id.txtV2);
        displayMemo();

        txtV3 = (TextView) findViewById(R.id.txtV3);
        txtV3.setMovementMethod(new ScrollingMovementMethod());

        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void displayMemo() {
        fileName = year + "" + month + "" + dayOfMonth + ".txt";

        FileInputStream fis = null;
        try {
            fis = openFileInput(fileName);
            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            String str = new String(fileData);
            //한국어 설정 (fileData, "EUC-KR") 추가 설정
            txtV2.setText(str);
        } catch (Exception e) {
        }
    }
}
