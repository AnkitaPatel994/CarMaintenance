package com.stimulustechnoweb.mrtaxi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GenerateDayReportActivity extends AppCompatActivity {

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

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        TextView txtGDName = (TextView)findViewById(R.id.txtGDName);
        TextView txtGDDate = (TextView)findViewById(R.id.txtGDDate);
        TextView txtGDShift = (TextView)findViewById(R.id.txtGDShift);
        TextView txtGDCash = (TextView)findViewById(R.id.txtGDCash);
        TextView txtGDMedical = (TextView)findViewById(R.id.txtGDMedical);
        TextView txtGDKidsfirst = (TextView)findViewById(R.id.txtGDKidsfirst);
        TextView txtGDSocialservices = (TextView)findViewById(R.id.txtGDSocialservices);
        TextView txtGDPulpmill = (TextView)findViewById(R.id.txtGDPulpmill);
        TextView txtGDOsbmill = (TextView)findViewById(R.id.txtGDOsbmill);
        TextView txtGDNamsaskmill = (TextView)findViewById(R.id.txtGDNamsaskmill);
        TextView txtGDDetox = (TextView)findViewById(R.id.txtGDDetox);
        TextView txtGDSgi = (TextView)findViewById(R.id.txtGDSgi);
        TextView txtGDGascredit = (TextView)findViewById(R.id.txtGDGascredit);
        TextView txtGDGascash = (TextView)findViewById(R.id.txtGDGascash);
        TextView txtGDMaintenance = (TextView)findViewById(R.id.txtGDMaintenance);
        TextView txtGDCommission = (TextView)findViewById(R.id.txtGDCommission);
        TextView txtGDTotal = (TextView)findViewById(R.id.txtGDTotal);

        String d_id = getIntent().getExtras().getString("d_id");
        String d_name = getIntent().getExtras().getString("d_name");
        txtGDName.setText(d_name);
        String c_dshift = getIntent().getExtras().getString("c_dshift");
        txtGDShift.setText(c_dshift);
        String d_cash = getIntent().getExtras().getString("d_cash");
        txtGDCash.setText(d_cash);
        String d_medical = getIntent().getExtras().getString("d_medical");
        txtGDMedical.setText(d_medical);
        String d_kidsfirst = getIntent().getExtras().getString("d_kidsfirst");
        txtGDKidsfirst.setText(d_kidsfirst);
        String d_socialservices = getIntent().getExtras().getString("d_socialservices");
        txtGDSocialservices.setText(d_socialservices);
        String d_pulpmill = getIntent().getExtras().getString("d_pulpmill");
        txtGDPulpmill.setText(d_pulpmill);
        String d_osbmill = getIntent().getExtras().getString("d_osbmill");
        txtGDOsbmill.setText(d_osbmill);
        String d_namsaskmill = getIntent().getExtras().getString("d_namsaskmill");
        txtGDNamsaskmill.setText(d_namsaskmill);
        String d_detox = getIntent().getExtras().getString("d_detox");
        txtGDDetox.setText(d_detox);
        String d_sgi = getIntent().getExtras().getString("d_sgi");
        txtGDSgi.setText(d_sgi);
        String c_gascredit = getIntent().getExtras().getString("c_gascredit");
        txtGDGascredit.setText(c_gascredit);
        String c_gascash = getIntent().getExtras().getString("c_gascash");
        txtGDGascash.setText(c_gascash);
        String c_maintenance = getIntent().getExtras().getString("c_maintenance");
        txtGDMaintenance.setText(c_maintenance);
        String c_commission = getIntent().getExtras().getString("c_commission");
        txtGDCommission.setText(c_commission);
        String c_total = getIntent().getExtras().getString("c_total");
        txtGDTotal.setText(c_total);
        String c_date = getIntent().getExtras().getString("c_date");
        txtGDDate.setText(c_date);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
