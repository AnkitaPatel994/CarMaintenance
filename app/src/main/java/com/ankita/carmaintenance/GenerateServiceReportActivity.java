package com.ankita.carmaintenance;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class GenerateServiceReportActivity extends AppCompatActivity {

    TextView txtSRVONameNo,txtSRVDate,txtSRVOCost,txtSRVOMaintenanceCost,txtSRVCostTotal;
    String VehicleDate,VehicleId;

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

        String v_name = getIntent().getExtras().getString("v_name");
        String v_no = getIntent().getExtras().getString("v_no");
        String o_date = getIntent().getExtras().getString("o_date");
        String o_cost = getIntent().getExtras().getString("o_cost");
        String o_m_cost = getIntent().getExtras().getString("o_m_cost");

        txtSRVONameNo = (TextView)findViewById(R.id.txtSRVONameNo);
        txtSRVDate = (TextView)findViewById(R.id.txtSRVDate);
        txtSRVOCost = (TextView)findViewById(R.id.txtSRVOCost);
        txtSRVOMaintenanceCost = (TextView)findViewById(R.id.txtSRVOMaintenanceCost);
        txtSRVCostTotal = (TextView)findViewById(R.id.txtSRVCostTotal);

        txtSRVONameNo.setText(v_name+" - "+v_no);
        txtSRVDate.setText(o_date);
        txtSRVOCost.setText("$"+o_cost);
        txtSRVOMaintenanceCost.setText("$"+o_m_cost);
        int total = Integer.parseInt(o_cost) + Integer.parseInt(o_m_cost);
        txtSRVCostTotal.setText("$"+Integer.toString(total));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


}
