package com.ankita.mrtaxi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText txtUName,txtPassword;
    Button btnLogin;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        txtUName = (EditText)findViewById(R.id.txtUName);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUName.getText().toString().equals("") && txtPassword.getText().toString().equals(""))
                {
                    Toast.makeText(LoginActivity.this,"Enter valid Username.",Toast.LENGTH_SHORT).show();
                }
                else if (txtPassword.getText().toString().equals(""))
                {
                    Toast.makeText(LoginActivity.this,"Enter valid Password.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    GetLogin login = new GetLogin();
                    login.execute();
                }
            }
        });

    }

    private class GetLogin extends AsyncTask<String,Void,String> {

        String status,message,UserId,UserName;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("uname",txtUName.getText().toString());
                joUser.put("pass",txtPassword.getText().toString());
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"Login.php",joUser.toString());
                JSONObject j = new JSONObject(pdUser);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    message=j.getString("message");
                    JSONArray JsArry=j.getJSONArray("Admin");
                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        UserId =jo.getString("id");
                        UserName =jo.getString("uname");

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
                session.createLoginSession(UserId,UserName);
                Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                txtUName.setText("");
                txtPassword.setText("");
                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
