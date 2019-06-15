package com.ankita.mrtaxi;

import android.Manifest;
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
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

public class GenerateDriverReportActivity extends AppCompatActivity {

    TableLayout tlDReport;
    String d_name;
    ArrayList<String> reportDShiftArray = new ArrayList<>();
    ArrayList<String> reportDDriverNameArray = new ArrayList<>();
    ArrayList<String> reportClientNameArray = new ArrayList<>();
    ArrayList<String> reportClientCostArray = new ArrayList<>();
    ArrayList<String> reportCashArray = new ArrayList<>();
    ArrayList<String> reportGascreditArray = new ArrayList<>();
    ArrayList<String> reportGascashArray = new ArrayList<>();
    ArrayList<String> reportMaintenanceArray = new ArrayList<>();
    ArrayList<String> reportCommissionArray = new ArrayList<>();
    ArrayList<String> reportGSTArray = new ArrayList<>();
    ArrayList<String> reportCashleftArray = new ArrayList<>();
    ArrayList<String> reportTotalArray = new ArrayList<>();
    ArrayList<String> reportDateArray = new ArrayList<>();
    private Bitmap bitmap;
    LinearLayout llDReport;
    float TOTALCCOST = 0,TOTALCCAST = 0,TOTALCGASCREDIT = 0,TOTALCGASCAST = 0,TOTALCMAINTENANCE = 0,TOTALCCOMMISSION = 0,TOTALCGST = 0,TOTALCCASTLEFT = 0,TOTALCTOTAL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_driver_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        llDReport = (LinearLayout)findViewById(R.id.llDReport);

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
                    if (ActivityCompat.shouldShowRequestPermissionRationale(GenerateDriverReportActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(GenerateDriverReportActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Log.d("size"," "+llDReport.getWidth() +"  "+llDReport.getWidth());
                        bitmap = loadBitmapFromView(llDReport, llDReport.getWidth(), llDReport.getHeight());
                        createPdf();
                    } else {
                        ActivityCompat.requestPermissions(GenerateDriverReportActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 200);
                        // No explanation needed, we can request the permission.
                    }
                } else {
                    Log.d("size"," "+llDReport.getWidth() +"  "+llDReport.getWidth());
                    bitmap = loadBitmapFromView(llDReport, llDReport.getWidth(), llDReport.getHeight());
                    createPdf();
                }

            }
        });

        tlDReport = (TableLayout) findViewById(R.id.tlDReport);

        d_name = getIntent().getExtras().getString("d_name");

        reportDShiftArray = getIntent().getExtras().getStringArrayList("reportDShiftArray");
        reportDDriverNameArray = getIntent().getExtras().getStringArrayList("reportDDriverNameArray");
        reportClientNameArray = getIntent().getExtras().getStringArrayList("reportClientNameArray");
        reportClientCostArray = getIntent().getExtras().getStringArrayList("reportClientCostArray");
        reportCashArray = getIntent().getExtras().getStringArrayList("reportCashArray");
        reportGascreditArray = getIntent().getExtras().getStringArrayList("reportGascreditArray");
        reportGascashArray = getIntent().getExtras().getStringArrayList("reportGascashArray");
        reportMaintenanceArray = getIntent().getExtras().getStringArrayList("reportMaintenanceArray");
        reportCommissionArray = getIntent().getExtras().getStringArrayList("reportCommissionArray");
        reportGSTArray = getIntent().getExtras().getStringArrayList("reportGSTArray");
        reportCashleftArray = getIntent().getExtras().getStringArrayList("reportCashleftArray");
        reportTotalArray = getIntent().getExtras().getStringArrayList("reportTotalArray");
        reportDateArray = getIntent().getExtras().getStringArrayList("reportDateArray");

        String listCCOST="";
        for(String ss : reportClientCostArray) {
            if(listCCOST == ""){
                listCCOST += ss;
            }else{
                listCCOST += "," + ss;
            }
        }
        String[] tokenCCOST = listCCOST.split(",");
        ArrayList<Float> CCostArray = new ArrayList<>();
        for(String t : tokenCCOST) {
            CCostArray.add(Float.valueOf(t));
        }
        for(float sss : CCostArray) {
            if(TOTALCCOST == 0){
                TOTALCCOST += sss;
            }else{
                TOTALCCOST += + sss;
            }
        }

        String listCCAST="";
        for(String ss : reportCashArray) {
            if(listCCAST == ""){
                listCCAST += ss;
            }else{
                listCCAST += "," + ss;
            }
        }
        String[] tokenCCAST = listCCAST.split(",");
        ArrayList<Float> CCASTArray = new ArrayList<>();
        for(String t : tokenCCAST) {
            CCASTArray.add(Float.valueOf(t));
        }
        for(float sss : CCASTArray) {
            if(TOTALCCAST == 0){
                TOTALCCAST += sss;
            }else{
                TOTALCCAST += + sss;
            }
        }

        String listCGASCREDIT="";
        for(String ss : reportGascreditArray) {
            if(listCGASCREDIT == ""){
                listCGASCREDIT += ss;
            }else{
                listCGASCREDIT += "," + ss;
            }
        }
        String[] tokenCGASCREDIT = listCGASCREDIT.split(",");
        ArrayList<Float> CGASCREDITArray = new ArrayList<>();
        for(String t : tokenCGASCREDIT) {
            CGASCREDITArray.add(Float.valueOf(t));
        }
        for(float sss : CGASCREDITArray) {
            if(TOTALCGASCREDIT == 0){
                TOTALCGASCREDIT += sss;
            }else{
                TOTALCGASCREDIT += + sss;
            }
        }

        String listCGASCAST="";
        for(String ss : reportGascashArray) {
            if(listCGASCAST == ""){
                listCGASCAST += ss;
            }else{
                listCGASCAST += "," + ss;
            }
        }
        String[] tokenCGASCAST = listCGASCAST.split(",");
        ArrayList<Float> CGASCASTArray = new ArrayList<>();
        for(String t : tokenCGASCAST) {
            CGASCASTArray.add(Float.valueOf(t));
        }
        for(float sss : CGASCASTArray) {
            if(TOTALCGASCAST == 0){
                TOTALCGASCAST += sss;
            }else{
                TOTALCGASCAST += + sss;
            }
        }

        String listCMAINTENANCE="";
        for(String ss : reportMaintenanceArray) {
            if(listCMAINTENANCE == ""){
                listCMAINTENANCE += ss;
            }else{
                listCMAINTENANCE += "," + ss;
            }
        }
        String[] tokenCMAINTENANCE = listCMAINTENANCE.split(",");
        ArrayList<Float> CMAINTENANCEArray = new ArrayList<>();
        for(String t : tokenCMAINTENANCE) {
            CMAINTENANCEArray.add(Float.valueOf(t));
        }
        for(float sss : CMAINTENANCEArray) {
            if(TOTALCMAINTENANCE == 0){
                TOTALCMAINTENANCE += sss;
            }else{
                TOTALCMAINTENANCE += + sss;
            }
        }

        String listCCOMMISSION="";
        for(String ss : reportCommissionArray) {
            if(listCCOMMISSION == ""){
                listCCOMMISSION += ss;
            }else{
                listCCOMMISSION += "," + ss;
            }
        }
        String[] tokenCCOMMISSION = listCCOMMISSION.split(",");
        ArrayList<Float> CCOMMISSIONArray = new ArrayList<>();
        for(String t : tokenCCOMMISSION) {
            CCOMMISSIONArray.add(Float.valueOf(t));
        }
        for(float sss : CCOMMISSIONArray) {
            if(TOTALCCOMMISSION == 0){
                TOTALCCOMMISSION += sss;
            }else{
                TOTALCCOMMISSION += + sss;
            }
        }

        String listCGST="";
        for(String ss : reportGSTArray) {
            if(listCGST == ""){
                listCGST += ss;
            }else{
                listCGST += "," + ss;
            }
        }
        String[] tokenCGST = listCGST.split(",");
        ArrayList<Float> CGSTArray = new ArrayList<>();
        for(String t : tokenCGST) {
            CGSTArray.add(Float.valueOf(t));
        }
        for(float sss : CGSTArray) {
            if(TOTALCGST == 0){
                TOTALCGST += sss;
            }else{
                TOTALCGST += + sss;
            }
        }

        String listCCASTLEFT="";
        for(String ss : reportCashleftArray) {
            if(listCCASTLEFT == ""){
                listCCASTLEFT += ss;
            }else{
                listCCASTLEFT += "," + ss;
            }
        }
        String[] tokenCCASTLEFT = listCCASTLEFT.split(",");
        ArrayList<Float> CCASTLEFTArray = new ArrayList<>();
        for(String t : tokenCCASTLEFT) {
            CCASTLEFTArray.add(Float.valueOf(t));
        }
        for(float sss : CCASTLEFTArray) {
            if(TOTALCCASTLEFT == 0){
                TOTALCCASTLEFT += sss;
            }else{
                TOTALCCASTLEFT += + sss;
            }
        }

        String listCTOTAL="";
        for(String ss : reportTotalArray) {
            if(listCTOTAL == ""){
                listCTOTAL += ss;
            }else{
                listCTOTAL += "," + ss;
            }
        }
        String[] tokenCTOTAL = listCTOTAL.split(",");
        ArrayList<Float> CTOTALArray = new ArrayList<>();
        for(String t : tokenCTOTAL) {
            CTOTALArray.add(Float.valueOf(t));
        }
        for(float sss : CTOTALArray) {
            if(TOTALCTOTAL == 0){
                TOTALCTOTAL += sss;
            }else{
                TOTALCTOTAL += + sss;
            }
        }

        addHeaders();
        addData();
    }

    private void createPdf(){
        //WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        //DisplayMetrics displaymetrics = new DisplayMetrics();
        //this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //float hight = displaymetrics.heightPixels ;
        int hight = llDReport.getChildAt(0).getHeight();
        //float width = displaymetrics.widthPixels ;
        int width = llDReport.getChildAt(0).getWidth();

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
        tlDReport.addView(tr1, getTblLayoutParams());
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(1, "DATE", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "SHIFT", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        //tr.addView(getTextView(1, "DRIVER NAME", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(1, "CONTRACT COST", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "CASH", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(1, "GASCREDIT", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(1, "GASCASH", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(1, "MAINTENANCE", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(1, "COMMISSION", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(1, "GST", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(1, "CASHLEFT", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "TOTAL", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tlDReport.addView(tr, getTblLayoutParams());
    }

    private void addData() {
        int numCompanies = reportDateArray.size();
        for (int i = 0; i < numCompanies; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, reportDateArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportDShiftArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            //tr.addView(getTextView(i + numCompanies, reportDDriverNameArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportClientCostArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportCashArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportGascreditArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportGascashArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportMaintenanceArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportCommissionArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportGSTArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportCashleftArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportTotalArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tlDReport.addView(tr, getTblLayoutParams());
        }
        TableRow tr2 = new TableRow(this);
        tr2.setLayoutParams(getLayoutParams());
        tr2.addView(getTextView(0, "TOTAL", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr2.addView(getTextView(0, "", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr2.addView(getTextView(0, String.valueOf(TOTALCCOST), Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr2.addView(getTextView(0, String.valueOf(TOTALCCAST), Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr2.addView(getTextView(0, String.valueOf(TOTALCGASCREDIT), Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr2.addView(getTextView(0, String.valueOf(TOTALCGASCAST), Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr2.addView(getTextView(0, String.valueOf(TOTALCMAINTENANCE), Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr2.addView(getTextView(0, String.valueOf(TOTALCCOMMISSION), Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr2.addView(getTextView(0, String.valueOf(TOTALCGST), Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr2.addView(getTextView(0, String.valueOf(TOTALCCASTLEFT), Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr2.addView(getTextView(0, String.valueOf(TOTALCTOTAL), Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tlDReport.addView(tr2, getTblLayoutParams());
    }

}
