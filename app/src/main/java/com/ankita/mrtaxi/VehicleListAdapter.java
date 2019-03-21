package com.ankita.mrtaxi;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

class VehicleListAdapter extends RecyclerView.Adapter<VehicleListAdapter.ViewHolder>{

    Context context;
    ArrayList<HashMap<String, String>> vehicleListArray;
    View v;

    public VehicleListAdapter(Context context, ArrayList<HashMap<String, String>> vehicleListArray) {
        this.context = context;
        this.vehicleListArray = vehicleListArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.vehicle_list, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final String v_id = vehicleListArray.get(i).get("v_id");
        final String v_name = vehicleListArray.get(i).get("v_name");
        final String v_no = vehicleListArray.get(i).get("v_no");
        final String v_kilometer = vehicleListArray.get(i).get("v_kilometer");

        viewHolder.txtVLNameNo.setText(v_name+" - "+v_no);
        viewHolder.txtVLKilometer.setText(v_kilometer+"km");

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,AddVehicleActivity.class);
                i.putExtra("flag","edit");
                i.putExtra("v_id",v_id);
                i.putExtra("v_name",v_name);
                i.putExtra("v_no",v_no);
                i.putExtra("v_kilometer",v_kilometer);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return vehicleListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtVLNameNo,txtVLKilometer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtVLNameNo = (TextView)itemView.findViewById(R.id.txtVLNameNo);
            txtVLKilometer = (TextView)itemView.findViewById(R.id.txtVLKilometer);
        }
    }
}
