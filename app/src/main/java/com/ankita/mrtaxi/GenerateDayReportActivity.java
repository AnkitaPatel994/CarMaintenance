package com.ankita.mrtaxi;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateDayReportActivity extends AppCompatActivity {

    String c_id;
    LinearLayout llDayReport;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_day_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        llDayReport = (LinearLayout)findViewById(R.id.llDayReport);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("size"," "+llDayReport.getWidth() +"  "+llDayReport.getWidth());
                bitmap = loadBitmapFromView(llDayReport, llDayReport.getWidth(), llDayReport.getHeight());
                createPdf();
            }
        });

        LinearLayout llOther = (LinearLayout)findViewById(R.id.llOther);
        TextView txtGDName = (TextView)findViewById(R.id.txtGDName);
        TextView txtGDDate = (TextView)findViewById(R.id.txtGDDate);
        TextView txtGDShift = (TextView)findViewById(R.id.txtGDShift);
        TextView txtGDClientName = (TextView)findViewById(R.id.txtGDClientName);
        TextView txtGDOther = (TextView)findViewById(R.id.txtGDOther);
        TextView txtGDCash = (TextView)findViewById(R.id.txtGDCash);
        TextView txtGDGastype = (TextView)findViewById(R.id.txtGDGastype);
        TextView txtGDGascash = (TextView)findViewById(R.id.txtGDGascash);
        TextView txtGDMaintenance = (TextView)findViewById(R.id.txtGDMaintenance);
        TextView txtGDCommission = (TextView)findViewById(R.id.txtGDCommission);
        TextView txtGDGST = (TextView)findViewById(R.id.txtGDGST);
        TextView txtGDCashleft = (TextView)findViewById(R.id.txtGDCashleft);
        TextView txtGDTotal = (TextView)findViewById(R.id.txtGDTotal);

        c_id = getIntent().getExtras().getString("c_id");

        String c_dshift = getIntent().getExtras().getString("c_dshift");
        txtGDShift.setText(c_dshift);

        String d_name = getIntent().getExtras().getString("d_name");
        txtGDName.setText(d_name);

        String client_name = getIntent().getExtras().getString("client_name");
        txtGDClientName.setText(client_name);

        String c_other = getIntent().getExtras().getString("c_other");
        if(c_other.equals(""))
        {
            llOther.setVisibility(View.GONE);
        }
        txtGDOther.setText(c_other);

        String c_cash = getIntent().getExtras().getString("c_cash");
        txtGDCash.setText(c_cash);

        String c_gastype = getIntent().getExtras().getString("c_gastype");
        txtGDGastype.setText("Gas "+c_gastype+" :");

        String c_gascash = getIntent().getExtras().getString("c_gascash");
        txtGDGascash.setText(c_gascash);

        String c_maintenance = getIntent().getExtras().getString("c_maintenance");
        txtGDMaintenance.setText(c_maintenance);

        String c_commission = getIntent().getExtras().getString("c_commission");
        txtGDCommission.setText(c_commission);

        String c_gst = getIntent().getExtras().getString("c_gst");
        txtGDGST.setText(c_gst);

        String c_cashleft = getIntent().getExtras().getString("c_cashleft");
        txtGDCashleft.setText(c_cashleft);

        String c_total = getIntent().getExtras().getString("c_total");
        txtGDTotal.setText(c_total);

        String c_date = getIntent().getExtras().getString("c_date");
        txtGDDate.setText(c_date);

    }

    private Bitmap loadBitmapFromView(LinearLayout llDayReport, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        llDayReport.draw(c);

        return b;
    }

    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

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

        // write the document content
        String targetPdf = "/sdcard/pdfDayReport.pdf";
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

        //openGeneratedPDF(filePath);

    }

    private void openGeneratedPDF(File filePath) {
        if (filePath.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(filePath);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(GenerateDayReportActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home)
            finish();

        if (id == R.id.menu_delete)
        {
            /*Intent i = new Intent(getApplicationContext(),ClientActivity.class);
            startActivity(i);*/
            GetDeleteReport deleteReport = new GetDeleteReport();
            deleteReport.execute();
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetDeleteReport extends AsyncTask<String,Void,String> {
        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(GenerateDayReportActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                joUser.put("c_id",c_id);

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"deleteReport.php",joUser.toString());
                JSONObject j = new JSONObject(pdUser);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                }
                else
                {
                    message=j.getString("message");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if(status.equals("1"))
            {
                Intent i = new Intent(GenerateDayReportActivity.this,HomeActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(GenerateDayReportActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
