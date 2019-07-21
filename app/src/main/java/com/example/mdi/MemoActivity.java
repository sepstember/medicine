package com.example.mdi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MemoActivity extends AppCompatActivity {
    private int year;
    private int month;
    private int dayOfMonth;
    private EditText edt1;
    private String fileName;
    private TextView txtV2;
    private Button btn1;
    private Button btn3;
    private Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        final Intent intent = getIntent();
        year = intent.getIntExtra("year", 0);
        month = intent.getIntExtra("month", 0);
        dayOfMonth = intent.getIntExtra("dayOfMonth", 0);

        TextView txtV1 = (TextView) findViewById(R.id.txtV1);
        String date = "" + "" + year + "년 " + month + "월 " + dayOfMonth + "일";
        txtV1.setText(date);

        edt1 = (EditText) findViewById(R.id.edt1);
        edt1.setHorizontallyScrolling(false);
        txtV2 = (TextView) findViewById(R.id.txtV2);
        txtV2.setMovementMethod(new ScrollingMovementMethod());
        btn1 = (Button) findViewById(R.id.btn1);
        btn3 = (Button) findViewById(R.id.btn3);
        displayMemo();


        //저장하기
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(fileName);
            }
        });
        //#D81B60

        //되돌아가기
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //수정하기 버튼을 누르면 다시 editVIew 로 변환
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setVisibility(View.VISIBLE);
                txtV2.setVisibility(View.INVISIBLE);
                btn1.setVisibility(View.VISIBLE);
                btn3.setVisibility(View.INVISIBLE);
            }
        });

        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }

    //저장된 메모 보여주기
    private void displayMemo() {
        fileName = year + "" + month + "" + dayOfMonth + ".txt";
        Intent intent2 = getIntent();

        FileInputStream fis = null;
        try {
            fis = openFileInput(fileName);
            //메모가 있으면 textView 로 보여주기
            if (fis != null) {
                edt1.setVisibility(View.INVISIBLE);
                txtV2.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.VISIBLE);
            }

                byte[] fileData = new byte[fis.available()];
                fis.read(fileData);
                fis.close();

                String str = new String(fileData);
                //한국어 설정 (fileData, "EUC-KR") 추가 설정
                edt1.setText(str);
                txtV2.setText(str);
        } catch (Exception e) {
        }
    }



    //저장
    private void save(String readDay) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(readDay, Context.MODE_NO_LOCALIZED_COLLATORS);
            //MODE_NO_LOCALIZED_COLLATORS
            String content = edt1.getText().toString();
            String testContent = content.trim();
            if (testContent.equals("")) {
                Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_LONG).show();
                return;
            }
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(getApplicationContext(), "저장되었습니다", Toast.LENGTH_LONG).show();
            //저장하면 곧바로 저장이 잘 되었는지 사용자에게 보여주기
            displayMemo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //삭제
    public void delete() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MemoActivity.this);
        alert.setMessage("삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fileName = year + "" + month + "" + dayOfMonth + ".txt";
                        deleteFile(fileName);
                        Toast.makeText(getApplicationContext(), "삭제되었습니다", Toast.LENGTH_LONG).show();
                        MemoActivity.super.onBackPressed();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        AlertDialog alertDialog = alert.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        //입력 도중일 때 되돌아가기 누를 경우
        if (edt1.getVisibility() == View.VISIBLE) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MemoActivity.this);
            alert.setMessage("입력 중입니다만 되돌아가시겠습니까?").setCancelable(false).setPositiveButton("확인",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MemoActivity.super.onBackPressed();
                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            AlertDialog alertDialog = alert.create();
            alert.show();
        } else {
            super.onBackPressed();
        }
    }


}