package com.ankita.mrtaxi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TableRow.LayoutParams;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GenerateServiceReportActivity extends AppCompatActivity {

    TextView txtSRVONameNo;
    String vehiclename,Oil_Total,Maintenance_Total,Main_Total;

    ArrayList<String> serviceReportovkilometer = new ArrayList<>();
    ArrayList<String> serviceReportocost = new ArrayList<>();
    ArrayList<String> serviceReportomcost = new ArrayList<>();
    ArrayList<String> serviceReportodate = new ArrayList<>();
    ArrayList<String> serviceReportTotal = new ArrayList<>();
    private Bitmap bitmap;
    TableLayout tlServiceReport;
    LinearLayout llServiceReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_service_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        llServiceReport = (LinearLayout)findViewById(R.id.llServiceReport);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(GenerateServiceReportActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(GenerateServiceReportActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Log.d("size"," "+llServiceReport.getWidth() +"  "+llServiceReport.getWidth());
                        bitmap = loadBitmapFromView(llServiceReport, llServiceReport.getWidth(), llServiceReport.getHeight());
                        createPdf();
                    } else {
                        ActivityCompat.requestPermissions(GenerateServiceReportActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 200);
                        // No explanation needed, we can request the permission.
                    }
                } else {
                    Log.d("size"," "+llServiceReport.getWidth() +"  "+llServiceReport.getWidth());
                    bitmap = loadBitmapFromView(llServiceReport, llServiceReport.getWidth(), llServiceReport.getHeight());
                    createPdf();
                }

            }
        });

        vehiclename = getIntent().getExtras().getString("vehiclename");
        Oil_Total = getIntent().getExtras().getString("Oil_Total");
        Maintenance_Total = getIntent().getExtras().getString("Maintenance_Total");
        Main_Total = getIntent().getExtras().getString("Main_Total");

        serviceReportovkilometer = getIntent().getExtras().getStringArrayList("serviceReportovkilometer");
        serviceReportocost = getIntent().getExtras().getStringArrayList("serviceReportocost");
        serviceReportomcost = getIntent().getExtras().getStringArrayList("serviceReportomcost");
        serviceReportodate = getIntent().getExtras().getStringArrayList("serviceReportodate");
        serviceReportTotal = getIntent().getExtras().getStringArrayList("serviceReportTotal");

        txtSRVONameNo = (TextView)findViewById(R.id.txtSRVONameNo);

        txtSRVONameNo.setText(vehiclename);

        tlServiceReport = (TableLayout)findViewById(R.id.tlServiceReport);

        addHeaders();
        addData();

    }

    private void createPdf(){
        //WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        /*DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);*/

        int hight = llServiceReport.getChildAt(0).getHeight();
        int width = llServiceReport.getChildAt(0).getWidth();

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
        String targetPdf = "/sdcard/MrTaxi/"+randomno+"ServiceReportPDF.pdf";
        File filePath;
        filePath = new File(targetPdf);
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

    private Bitmap loadBitmapFromView(LinearLayout llServiceReport, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        llServiceReport.draw(c);

        return b;
    }

    private ViewGroup.LayoutParams getLayoutParams() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
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
        return new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
    }

    private void addHeaders() {

        TableRow tr1 = new TableRow(this);
        tr1.setLayoutParams(getLayoutParams());
        tr1.addView(getTextView(0, vehiclename, ContextCompat.getColor(this, R.color.colorPrimary), Typeface.BOLD, Color.WHITE));
        tlServiceReport.addView(tr1, getTblLayoutParams());
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, "DATE", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "KILOMETER", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "OIL_CHANDE", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "MAINTENANCE", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "TOTAL", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tlServiceReport.addView(tr, getTblLayoutParams());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void addData() {
        int numCompanies = serviceReportodate.size();
        for (int i = 0; i < numCompanies; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, serviceReportodate.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, serviceReportovkilometer.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, serviceReportocost.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, serviceReportomcost.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, serviceReportTotal.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tlServiceReport.addView(tr, getTblLayoutParams());
        }
        /*TableRow tr1 = new TableRow(this);
        tr1.setLayoutParams(getLayoutParams());
        tr1.addView(getTextView(0, "TOTAL", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr1.addView(getTextView(0, Oil_Total, Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr1.addView(getTextView(0, Maintenance_Total, Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr1.addView(getTextView(0, Main_Total, Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tlServiceReport.addView(tr1, getTblLayoutParams());*/

    }

}
