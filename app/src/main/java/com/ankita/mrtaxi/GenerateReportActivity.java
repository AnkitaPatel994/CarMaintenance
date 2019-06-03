package com.ankita.mrtaxi;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GenerateReportActivity extends AppCompatActivity {

    TextView txtGSDName;
    TableLayout tlReport;
    String d_name;
    ArrayList<String> reportDShiftArray = new ArrayList<>();
    ArrayList<String> reportClientNameArray = new ArrayList<>();
    ArrayList<String> reportCashArray = new ArrayList<>();
    ArrayList<String> reportGastypeArray = new ArrayList<>();
    ArrayList<String> reportGascashArray = new ArrayList<>();
    ArrayList<String> reportMaintenanceArray = new ArrayList<>();
    ArrayList<String> reportCommissionArray = new ArrayList<>();
    ArrayList<String> reportGSTArray = new ArrayList<>();
    ArrayList<String> reportCashleftArray = new ArrayList<>();
    ArrayList<String> reportTotalArray = new ArrayList<>();
    ArrayList<String> reportDateArray = new ArrayList<>();
    private Bitmap bitmap;
    LinearLayout llReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        llReport = (LinearLayout)findViewById(R.id.llReport);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(GenerateReportActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(GenerateReportActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Log.d("size"," "+llReport.getWidth() +"  "+llReport.getWidth());
                        bitmap = loadBitmapFromView(llReport, llReport.getWidth(), llReport.getHeight());
                        createPdf();
                    } else {
                        ActivityCompat.requestPermissions(GenerateReportActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 200);
                        // No explanation needed, we can request the permission.
                    }
                } else {
                    Log.d("size"," "+llReport.getWidth() +"  "+llReport.getWidth());
                    bitmap = loadBitmapFromView(llReport, llReport.getWidth(), llReport.getHeight());
                    createPdf();
                }

            }
        });

        txtGSDName = (TextView)findViewById(R.id.txtGSDName);
        tlReport = (TableLayout) findViewById(R.id.tlReport);

        d_name = getIntent().getExtras().getString("d_name");
        txtGSDName.setText(d_name);

        reportDShiftArray = getIntent().getExtras().getStringArrayList("reportDShiftArray");
        reportClientNameArray = getIntent().getExtras().getStringArrayList("reportClientNameArray");
        reportCashArray = getIntent().getExtras().getStringArrayList("reportCashArray");
        reportGastypeArray = getIntent().getExtras().getStringArrayList("reportGastypeArray");
        reportGascashArray = getIntent().getExtras().getStringArrayList("reportGascashArray");
        reportMaintenanceArray = getIntent().getExtras().getStringArrayList("reportMaintenanceArray");
        reportCommissionArray = getIntent().getExtras().getStringArrayList("reportCommissionArray");
        reportGSTArray = getIntent().getExtras().getStringArrayList("reportGSTArray");
        reportCashleftArray = getIntent().getExtras().getStringArrayList("reportCashleftArray");
        reportTotalArray = getIntent().getExtras().getStringArrayList("reportTotalArray");
        reportDateArray = getIntent().getExtras().getStringArrayList("reportDateArray");

        addHeaders();
        addData();

    }

    private void createPdf(){
        //WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        //DisplayMetrics displaymetrics = new DisplayMetrics();
        //this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //float hight = displaymetrics.heightPixels ;
        int hight = llReport.getChildAt(0).getHeight();
        //float width = displaymetrics.widthPixels ;
        int width = llReport.getChildAt(0).getWidth();

        /*int convertHighet = (int) hight, convertWidth = (int) width;*/

        int convertHighet = hight, convertWidth = width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MrTaxi/";
        File dir = new File(file_path);
        if (!dir.exists())
            dir.mkdirs();

        Random random = new Random();
        String randomno = String.format("%04d", random.nextInt(10000));

        // write the document content
        String targetPdf = "/sdcard/MrTaxi/"+randomno+"Report.pdf";

        Log.d("targetPdf",targetPdf);

        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();

    }

    private Bitmap loadBitmapFromView(LinearLayout llReport, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        llReport.draw(c);

        return b;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private ViewGroup.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    private View getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        return tv;
    }

    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
    }

    private void addHeaders() {

        TableRow tr1 = new TableRow(this);
        tr1.setLayoutParams(getLayoutParams());
        tr1.addView(getTextView(0, d_name, ContextCompat.getColor(this, R.color.colorPrimary), Typeface.BOLD, Color.WHITE));
        tlReport.addView(tr1, getTblLayoutParams());
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(1, "DATE", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        //tr.addView(getTextView(0, "SHIFT", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
            tr.addView(getTextView(1, "CONTRACT", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        //tr.addView(getTextView(0, "CASH", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(1, "GASTYPE", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(1, "GASCASH", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
            tr.addView(getTextView(1, "MAINTENANCE", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
            tr.addView(getTextView(1, "COMMISSION", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
            tr.addView(getTextView(1, "GST", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
            tr.addView(getTextView(1, "CASHLEFT", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        //tr.addView(getTextView(0, "TOTAL", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tlReport.addView(tr, getTblLayoutParams());
    }

    private void addData() {
        int numCompanies = reportDateArray.size();
        for (int i = 0; i < numCompanies; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, reportDateArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            //tr.addView(getTextView(i + numCompanies, reportDShiftArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportClientNameArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            //tr.addView(getTextView(i + numCompanies, reportCashArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportGastypeArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportGascashArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportMaintenanceArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportCommissionArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportGSTArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportCashleftArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            //tr.addView(getTextView(i + numCompanies, reportTotalArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tlReport.addView(tr, getTblLayoutParams());
        }
    }

}
