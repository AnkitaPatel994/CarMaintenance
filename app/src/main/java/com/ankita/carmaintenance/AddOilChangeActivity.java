package com.ankita.carmaintenance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddOilChangeActivity extends AppCompatActivity {

    Spinner spVehicleName;
    EditText txtOilVehicleKilometer,txtVehicleOilCost;
    Button btnAddOil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_oil_change);

        spVehicleName = (Spinner)findViewById(R.id.spVehicleName);
        txtOilVehicleKilometer = (EditText) findViewById(R.id.txtOilVehicleKilometer);
        txtVehicleOilCost = (EditText) findViewById(R.id.txtVehicleOilCost);
        btnAddOil = (Button) findViewById(R.id.btnAddOil);

    }
}
