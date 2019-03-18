package com.stimulustechnoweb.mrtaxi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddClientActivity extends AppCompatActivity {

    EditText txtClientName;
    Button btnAddClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        txtClientName = (EditText)findViewById(R.id.txtClientName);
        btnAddClient = (Button) findViewById(R.id.btnAddClient);

    }
}
