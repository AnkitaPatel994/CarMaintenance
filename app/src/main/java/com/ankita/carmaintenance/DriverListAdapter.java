package com.ankita.carmaintenance;

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

class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.ViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> driverListArray;
    View v;

    public DriverListAdapter(Context context, ArrayList<HashMap<String, String>> driverListArray) {
        this.context = context;
        this.driverListArray = driverListArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.driver_list, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final String d_id = driverListArray.get(i).get("d_id");
        final String d_name = driverListArray.get(i).get("d_name");
        final String d_cash = driverListArray.get(i).get("d_cash");
        final String d_medical = driverListArray.get(i).get("d_medical");
        final String d_kidsfirst = driverListArray.get(i).get("d_kidsfirst");
        final String d_socialservices = driverListArray.get(i).get("d_socialservices");
        final String d_pulpmill = driverListArray.get(i).get("d_pulpmill");
        final String d_osbmill = driverListArray.get(i).get("d_osbmill");
        final String d_namsaskmill = driverListArray.get(i).get("d_namsaskmill");
        final String d_detox = driverListArray.get(i).get("d_detox");
        final String d_sgi = driverListArray.get(i).get("d_sgi");

        viewHolder.txtDLName.setText(d_name);
        viewHolder.txtDLCash.setText(d_cash);
        viewHolder.txtDLMedical.setText(d_medical);
        viewHolder.txtDLKidsFirst.setText(d_kidsfirst);
        viewHolder.txtDLSocialServices.setText(d_socialservices);
        viewHolder.txtDLPulpMill.setText(d_pulpmill);
        viewHolder.txtDLOSBMill.setText(d_osbmill);
        viewHolder.txtDLNamSaskMill.setText(d_namsaskmill);
        viewHolder.txtDLDetox.setText(d_detox);
        viewHolder.txtDLSGI.setText(d_sgi);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,AddDriverActivity.class);
                i.putExtra("flag","edit");
                i.putExtra("d_id",d_id);
                i.putExtra("d_name",d_name);
                i.putExtra("d_cash",d_cash);
                i.putExtra("d_medical",d_medical);
                i.putExtra("d_kidsfirst",d_kidsfirst);
                i.putExtra("d_socialservices",d_socialservices);
                i.putExtra("d_pulpmill",d_pulpmill);
                i.putExtra("d_osbmill",d_osbmill);
                i.putExtra("d_namsaskmill",d_namsaskmill);
                i.putExtra("d_detox",d_detox);
                i.putExtra("d_sgi",d_sgi);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return driverListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDLName,txtDLCash,txtDLMedical,txtDLKidsFirst,txtDLSocialServices,txtDLPulpMill,txtDLOSBMill,txtDLNamSaskMill,txtDLDetox,txtDLSGI;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDLName = (TextView)itemView.findViewById(R.id.txtDLName);
            txtDLCash = (TextView)itemView.findViewById(R.id.txtDLCash);
            txtDLMedical = (TextView)itemView.findViewById(R.id.txtDLMedical);
            txtDLKidsFirst = (TextView)itemView.findViewById(R.id.txtDLKidsFirst);
            txtDLSocialServices = (TextView)itemView.findViewById(R.id.txtDLSocialServices);
            txtDLPulpMill = (TextView)itemView.findViewById(R.id.txtDLPulpMill);
            txtDLOSBMill = (TextView)itemView.findViewById(R.id.txtDLOSBMill);
            txtDLNamSaskMill = (TextView)itemView.findViewById(R.id.txtDLNamSaskMill);
            txtDLDetox = (TextView)itemView.findViewById(R.id.txtDLDetox);
            txtDLSGI = (TextView)itemView.findViewById(R.id.txtDLSGI);
        }
    }
}
