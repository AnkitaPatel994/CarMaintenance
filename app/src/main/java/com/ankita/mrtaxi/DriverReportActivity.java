package com.ankita.mrtaxi;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class DriverReportActivity extends AppCompatActivity {

    TextView txtRDDate;
    Spinner spRDDriverName;
    Button btnRDReport;
    String driverId;
    ArrayList<String> DriverIdList = new ArrayList<>();
    ArrayList<String> DriverNameList = new ArrayList<>();

    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_report);
        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        txtRDDate = (TextView)findViewById(R.id.txtRDDate);
        txtRDDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(DriverReportActivity.this,new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;

                        if(selectedmonth < 10 && selectedday < 10)
                        {
                            txtRDDate.setText(selectedyear + "-" + "0"+selectedmonth + "-" + "0"+selectedday);
                        }
                        else if(selectedmonth < 10)
                        {
                            txtRDDate.setText(selectedyear + "-" + "0"+selectedmonth + "-" + selectedday);
                        }
                        else if(selectedday < 10)
                        {
                            txtRDDate.setText(selectedyear + "-" + selectedmonth + "-" + "0"+selectedday);
                        }
                        else
                        {
                            txtRDDate.setText(selectedyear + "-" + selectedmonth + "-" + selectedday);
                        }
                    }
                }, mYear, mMonth, mDay);
                //mDatePicker.getDatePicker().setMinDate(c.getTimeInMillis());
                mDatePicker.show();
            }
        });

        spRDDriverName = (Spinner) findViewById(R.id.spRDDriverName);
        GetDriverList driverList = new GetDriverList();
        driverList.execute();

        spRDDriverName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int position_no = spRDDriverName.getSelectedItemPosition();
                driverId = DriverIdList.get(position_no);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnRDReport = (Button) findViewById(R.id.btnRDReport);
        btnRDReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reportDShiftArray.clear();
                reportClientNameArray.clear();
                reportClientCostArray.clear();
                reportCashArray.clear();
                reportGascreditArray.clear();
                reportGascashArray.clear();
                reportMaintenanceArray.clear();
                reportCommissionArray.clear();
                reportGSTArray.clear();
                reportCashleftArray.clear();
                reportTotalArray.clear();
                reportDateArray.clear();

                if(txtRDDate.getText().toString().equals(""))
                {
                    Toast.makeText(DriverReportActivity.this,"Enter Date",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    GetDriverReport driverReport = new GetDriverReport();
                    driverReport.execute();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private class GetDriverList extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"drivername.php",joUser.toString());
                JSONObject j = new JSONObject(pdUser);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONArray JsArry=j.getJSONArray("vehicle");
                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        String d_id =jo.getString("d_id");
                        String d_name =jo.getString("d_name");

                        DriverIdList.add(d_id);
                        DriverNameList.add(d_name);
                    }
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
            if(status.equals("1"))
            {
                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(DriverReportActivity.this, android.R.layout.simple_spinner_dropdown_item, DriverNameList);
                spRDDriverName.setAdapter(spinnerArrayAdapter);
            }
            else
            {
                Toast.makeText(DriverReportActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetDriverReport extends AsyncTask<String,Void,String> {

        String status,message,d_name;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(DriverReportActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("c_d_id",driverId);
                joUser.put("c_date",txtRDDate.getText().toString());
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"driverreport.php",joUser.toString());
                JSONObject j = new JSONObject(pdUser);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");

                    JSONArray JsArry=j.getJSONArray("vehicle");
                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        String c_dshift =jo.getString("c_dshift");
                        d_name =jo.getString("d_name");
                        String client_name =jo.getString("client_name");
                        String c_cost =jo.getString("c_cost");
                        String c_cash =jo.getString("c_cash");
                        String c_gascredit =jo.getString("c_gascredit");
                        String c_gascash =jo.getString("c_gascash");
                        String c_maintenance =jo.getString("c_maintenance");
                        String c_commission =jo.getString("c_commission");
                        String c_gst =jo.getString("c_gst");
                        String c_cashleft =jo.getString("c_cashleft");
                        String c_total =jo.getString("c_total");
                        String c_date =jo.getString("c_date");

                        String[] tokencost = c_cost.split(",");
                        ArrayList<Integer> CCostArray = new ArrayList<>();
                        int listCost=0;
                        for(String t : tokencost) {
                            CCostArray.add(Integer.valueOf(t));
                        }
                        String ccost ="";
                        for(int sss : CCostArray) {
                            if(listCost == 0){
                                listCost += sss;
                            }else{
                                listCost += + sss;
                            }
                            ccost = String.valueOf(listCost);
                        }

                        reportDShiftArray.add(c_dshift);
                        reportDDriverNameArray.add(d_name);
                        reportClientNameArray.add(client_name);
                        reportClientCostArray.add(ccost);
                        reportCashArray.add(c_cash);
                        reportGascreditArray.add(c_gascredit);
                        reportGascashArray.add(c_gascash);
                        reportMaintenanceArray.add(c_maintenance);
                        reportCommissionArray.add(c_commission);
                        reportGSTArray.add(c_gst);
                        reportCashleftArray.add(c_cashleft);
                        reportTotalArray.add(c_total);
                        reportDateArray.add(c_date);

                    }
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
                Intent i = new Intent(DriverReportActivity.this,GenerateDriverReportActivity.class);
                i.putExtra("d_name",d_name);
                i.putExtra("reportDShiftArray",reportDShiftArray);
                i.putExtra("reportDDriverNameArray",reportDDriverNameArray);
                i.putExtra("reportClientNameArray",reportClientNameArray);
                i.putExtra("reportClientCostArray",reportClientCostArray);
                i.putExtra("reportCashArray",reportCashArray);
                i.putExtra("reportGascreditArray",reportGascreditArray);
                i.putExtra("reportGascashArray",reportGascashArray);
                i.putExtra("reportMaintenanceArray",reportMaintenanceArray);
                i.putExtra("reportCommissionArray",reportCommissionArray);
                i.putExtra("reportGSTArray",reportGSTArray);
                i.putExtra("reportCashleftArray",reportCashleftArray);
                i.putExtra("reportTotalArray",reportTotalArray);
                i.putExtra("reportDateArray",reportDateArray);
                startActivity(i);

            }
            else
            {
                Toast.makeText(DriverReportActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

}
