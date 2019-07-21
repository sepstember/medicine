package com.example.mdi;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDPage;
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream;
import com.tom_roush.pdfbox.pdmodel.font.PDFont;
import com.tom_roush.pdfbox.pdmodel.font.PDType0Font;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//https://ghj1001020.tistory.com/307

public class SendActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private TextView txtV1;
    private TextView txtV2;
    private Button btn1;
    private Button btn2;

    private Button btn3;
    private Button btn4;
    private Button btn5;

    private String startDate;
    private String endDate;

    private ScrollView scrollView;
    //https://gist.github.com/nexussord/202e2804b45751098231e45cf905736c

//    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TestLog";
//    final static String filename = "logfile.txt";

    private File root;
    private AssetManager assetManager;
    private PDFont font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        datePicker = (DatePicker) findViewById(R.id.datepicker);
        txtV1 = (TextView) findViewById(R.id.txtV1);
        txtV2 = (TextView) findViewById(R.id.txtV2);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(this, );
                dialog.show();
//                datePicker.setVisibility(View.VISIBLE);
//                btn3.setVisibility(View.VISIBLE);
//                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setVisibility(View.VISIBLE);
                btn4.setVisibility(View.VISIBLE);
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.INVISIBLE);

                startDate = String.format("%d-%d-%d", datePicker.getYear(),
                        datePicker.getMonth() + 1, datePicker.getDayOfMonth());
                txtV1.setText(startDate);
                scrollView.fullScroll(View.FOCUS_UP);
            }
        });

        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setVisibility(View.INVISIBLE);
                btn4.setVisibility(View.INVISIBLE);
                endDate = String.format("%d-%d-%d", datePicker.getYear(),
                        datePicker.getMonth() + 1, datePicker.getDayOfMonth());
                txtV2.setText(endDate);
                scrollView.fullScroll(View.FOCUS_UP);
            }
        });

        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPeriod();
            }
        });

        Button btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date = year + "년";
            txtV1.setText();
        }
    }

    //☆ 데이터 가져오는 메소드 구현!
    private void checkPeriod() {
        if (txtV1.getText().toString().length() == 0 || txtV2.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "시작 및 종료 날짜를 정해주세요", Toast.LENGTH_LONG).show();

            datePicker.setVisibility(View.INVISIBLE);
            btn3.setVisibility(View.INVISIBLE);
            btn4.setVisibility(View.INVISIBLE);
            scrollView.fullScroll(View.FOCUS_UP);

            return;
        }

        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            d = simpleDateFormat.parse(startDate);
        } catch (Exception e) {
        }

        Date d2 = new Date();
        try {
            d2 = simpleDateFormat.parse(endDate);
        } catch (Exception e) {
        }

        int compare = d2.compareTo(d);

        if (compare == 0) {
            Toast.makeText(getApplicationContext(), "시작 날짜와 종료 날짜가 동일합니다", Toast.LENGTH_LONG).show();

            datePicker.setVisibility(View.INVISIBLE);
            btn3.setVisibility(View.INVISIBLE);
            btn4.setVisibility(View.INVISIBLE);
            scrollView.fullScroll(View.FOCUS_UP);

            return;
        } else if (compare < 0) {
            Toast.makeText(getApplicationContext(), "종료 날짜가 시작 날짜보다 빠릅니다", Toast.LENGTH_LONG).show();

            datePicker.setVisibility(View.INVISIBLE);
            btn3.setVisibility(View.INVISIBLE);
            btn4.setVisibility(View.INVISIBLE);
            scrollView.fullScroll(View.FOCUS_UP);

            return;
        }

        //createPDF();
    }

    private void importData(WebView webView) {
//        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
//        PrintDocumentAdapter printDocumentAdapter = webView.createPrintDocumentAdapter();
//        printManager.print("MyWebView", printDocumentAdapter, null);

        String text = "123456789012345 67890123456 7890123 45678 901234567 8901233333333 3456789012 3456789 01234 567890";

    }
    @Override
    protected void onStart() {
        super.onStart();
        setUp();
    }

    private void setUp() {
        PDFBoxResourceLoader.init(getApplicationContext());
        root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
        assetManager = getAssets();

        if (ContextCompat.checkSelfPermission(SendActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SendActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

    }
    //https://webnautes.tistory.com/1311
    public String createPdf() {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);


        try{
            font = PDType0Font.load(document, assetManager.open("NanumBarunGothicLight.ttf"));
        }
        catch (IOException e){
        }

        PDPageContentStream contentStream;

        try {
            contentStream = new PDPageContentStream( document, page, true, true);

            int text_width = 470;
            int text_left = 70;

            String textN = "12345";
            int fontSize = 17;
            float leading = 1.5f * fontSize;

            List<String> lines;
            lines = new ArrayList<String>();
            int lastSpace = -1;
            for (String text : textN.split("\n"))
            {
                while (text.length() > 0) {
                    int spaceIndex = text.indexOf(' ', lastSpace + 1);
                    if (spaceIndex < 0)
                        spaceIndex = text.length();
                    String subString = text.substring(0, spaceIndex);
                    float size = fontSize * font.getStringWidth(subString) / 1000;
                    if (size > text_width) {
                        if (lastSpace < 0)
                            lastSpace = spaceIndex;
                        subString = text.substring(0, lastSpace);
                        lines.add(subString);
                        text = text.substring(lastSpace).trim();
                        lastSpace = -1;
                    } else if (spaceIndex == text.length()) {
                        lines.add(text);
                        text = "";
                    } else {
                        lastSpace = spaceIndex;
                    }
                }
            }

            contentStream.beginText();
            contentStream.setFont(font, fontSize);

            for (String line: lines)
            {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -leading);
            }
            contentStream.endText();
            contentStream.close();

            String path = root.getAbsolutePath() + "/테스트.pdf";

            document.save(path);
            document.close();

            return path;

        } catch (IOException e) {
        }

        return "error";
    }

    //데이트피커 리스터

//
//    class SaveTask extends AsyncTask<Void, Void, String> {
//
//        @Override
//        protected String doInBackground(Void... voids) {
//
//            String path = createPdf();
//            return path;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            Toast.makeText(SendActivity.this, "잠시 기다리세요.", Toast.LENGTH_SHORT).show();
//
//        }
//
//        @Override
//        protected void onPostExecute(String path) {
//            super.onPostExecute(path);
//
//            Toast.makeText(SendActivity.this, path+"에 PDF 파일로 저장했습니다.", Toast.LENGTH_LONG).show();
//        }
//
//    }
}

//    public boolean createPDF() {
//        Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
//        boolean result = true;
//        PDDocument document = null;
//        try {
//            document = new PDDocument();
//        } catch (Exception e) {
//            result = false;
//            System.out.println("DocumentCreatioin-createPDF ERROR : " + e.getMessage());
//        }
//        PDPage blankPage = new PDPage();
//        document.addPage(blankPage);
//        PDFont font = PDType1Font.TIMES_ITALIC;
//        PDPageContentStream contentStream = null;
//        try {
//            contentStream = new PDPageContentStream(document, blankPage);
//            contentStream.setFont(font, 50);
//            contentStream.beginText();
//            contentStream.moveTextPositionByAmount(0, 100);
//            contentStream.drawString("Hello Apache PDFBox");
//            contentStream.endText();
//        } catch (IOException e) {
//            System.out.println("DocumentCreatioin-createPDF ERROR : " + e.getMessage());
//        } finally {
//            try {
//                contentStream.close();
//            } catch (IOException e ) {
//                System.out.println("DocumentCreatioin-createPDF ERROR : " + e.getMessage());
//            }
//        }
//        try {
//            document.save("c:/pdfbox/createPDF.pdf");
//        } catch (IOException e) {
//            result = false;
//            System.out.println("DocumentCreatioin-createPDF ERROR : " + e.getMessage());
//        } catch (Exception e) {
//            result = false;
//            System.out.println("DocumentCreatioin-createPDF ERROR : " + e.getMessage());
//        }
//        try {
//            document.close();
//        } catch (IOException e) {
//            result = false;
//            System.out.println("DocumentCreatioin-createPDF ERROR : " + e.getMessage());
//        }
//
//        return result;
//    }
//}




//    public void mOnFileWriter(View v) {
//        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        String contents = "Log 생성 : " + now + "\n";
//
//        writeTextFile(foldername, filename, contents);
//    }

//    public void writeTextFile(String foldername, String filename, String contents) {
//        try {
//            File dir = new File(foldername);
//            if (!dir.exists()) {
//                dir.mkdir();
//            }
//
//            FileOutputStream fos = new FileOutputStream(foldername + "/" + filename, true);
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
//            writer.write(contents);
//            writer.flush();
//
//            writer.close();
//            fos.close();
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Toast.makeText(getApplicationContext(), "저장되었습니다", Toast.LENGTH_LONG).show();
//    }
