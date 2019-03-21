package com.ankita.mrtaxi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AddClientActivity extends AppCompatActivity {

    EditText txtClientName;
    Button btnAddClient,btnDeleteClient;
    String client_id,client_name,flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        txtClientName = (EditText)findViewById(R.id.txtClientName);
        btnAddClient = (Button) findViewById(R.id.btnAddClient);
        btnDeleteClient = (Button) findViewById(R.id.btnDeleteClient);

        flag = getIntent().getExtras().getString("flag");
        if(flag.equals("add"))
        {
            btnDeleteClient.setVisibility(View.GONE);
        }
        else if(flag.equals("edit"))
        {
            btnAddClient.setText("Update Client");
            btnDeleteClient.setVisibility(View.VISIBLE);
            client_id = getIntent().getExtras().getString("client_id");
            client_name = getIntent().getExtras().getString("client_name");
            txtClientName.setText(client_name);
        }

        btnAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag.equals("add"))
                {
                    GetAddClient addClient = new GetAddClient();
                    addClient.execute();
                }
                else if(flag.equals("edit"))
                {
                    GetEditClient editClient = new GetEditClient();
                    editClient.execute();
                }
            }
        });

        btnDeleteClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDeleteClient deleteClient = new GetDeleteClient();
                deleteClient.execute();
            }
        });

    }

    private class GetAddClient extends AsyncTask<String,Void,String> {
        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(AddClientActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                joUser.put("client_name",txtClientName.getText().toString());

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"addclient.php",joUser.toString());
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
                Intent i = new Intent(AddClientActivity.this,ClientActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(AddClientActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetEditClient extends AsyncTask<String,Void,String> {
        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(AddClientActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("client_id",client_id);
                joUser.put("client_name",txtClientName.getText().toString());

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"editClient.php",joUser.toString());
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
                Intent i = new Intent(AddClientActivity.this,ClientActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(AddClientActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetDeleteClient extends AsyncTask<String,Void,String> {
        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(AddClientActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                joUser.put("client_id",client_id);

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"deleteClient.php",joUser.toString());
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
                Intent i = new Intent(AddClientActivity.this,ClientActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(AddClientActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
