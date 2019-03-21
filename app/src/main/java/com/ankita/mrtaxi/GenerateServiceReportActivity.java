package com.ankita.mrtaxi;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GenerateServiceReportActivity extends AppCompatActivity {

    TextView txtSRVONameNo;
    String vehiclename,Oil_Total,Maintenance_Total,Main_Total;

    ArrayList<String> serviceReportocost = new ArrayList<>();
    ArrayList<String> serviceReportomcost = new ArrayList<>();
    ArrayList<String> serviceReportodate = new ArrayList<>();
    ArrayList<String> serviceReportTotal = new ArrayList<>();

    TableLayout tlServiceReport;

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

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        vehiclename = getIntent().getExtras().getString("vehiclename");
        Oil_Total = getIntent().getExtras().getString("Oil_Total");
        Maintenance_Total = getIntent().getExtras().getString("Maintenance_Total");
        Main_Total = getIntent().getExtras().getString("Main_Total");

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
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, "DATE", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
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
            tr.addView(getTextView(i + numCompanies, serviceReportocost.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, serviceReportomcost.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, serviceReportTotal.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tlServiceReport.addView(tr, getTblLayoutParams());
        }
        TableRow tr1 = new TableRow(this);
        tr1.setLayoutParams(getLayoutParams());
        tr1.addView(getTextView(0, "TOTAL", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr1.addView(getTextView(0, Oil_Total, Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr1.addView(getTextView(0, Maintenance_Total, Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr1.addView(getTextView(0, Main_Total, Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tlServiceReport.addView(tr1, getTblLayoutParams());
    }

}
