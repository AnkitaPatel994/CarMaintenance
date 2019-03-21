package com.stimulustechnoweb.mrtaxi;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

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

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

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
        return new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
    }

    private void addHeaders() {
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, "DATE", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "SHIFT", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "CLIENT_NAME", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "CASH", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "GASTYPE", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "GASCASH", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "MAINTENANCE", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "COMMISSION", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "GST", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "CASHLEFT", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "TOTAL", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tlReport.addView(tr, getTblLayoutParams());
    }

    private void addData() {
        int numCompanies = reportDateArray.size();
        for (int i = 0; i < numCompanies; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, reportDateArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportDShiftArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportClientNameArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportCashArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportGastypeArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportGascashArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportMaintenanceArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportCommissionArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportGSTArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportCashleftArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tr.addView(getTextView(i + numCompanies, reportTotalArray.get(i), Color.BLACK, Typeface.NORMAL, Color.WHITE));
            tlReport.addView(tr, getTblLayoutParams());
        }
    }

}
