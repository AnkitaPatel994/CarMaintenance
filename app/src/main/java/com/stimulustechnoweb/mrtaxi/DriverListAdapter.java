package com.stimulustechnoweb.mrtaxi;

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
        final String d_licenceno = driverListArray.get(i).get("d_licenceno");
        final String d_gst = driverListArray.get(i).get("d_gst");
        final String d_commission = driverListArray.get(i).get("d_commission");

        viewHolder.txtDLName.setText(d_name);
        viewHolder.txtDLLNo.setText(d_licenceno);
        viewHolder.txtDLGST.setText("GST : "+d_gst);
        viewHolder.txtDLCommission.setText("Commission: "+d_commission);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,AddDriverActivity.class);
                i.putExtra("flag","edit");
                i.putExtra("d_id",d_id);
                i.putExtra("d_name",d_name);
                i.putExtra("d_licenceno",d_licenceno);
                i.putExtra("d_gst",d_gst);
                i.putExtra("d_commission",d_commission);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return driverListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDLName,txtDLLNo,txtDLGST,txtDLCommission;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDLName = (TextView)itemView.findViewById(R.id.txtDLName);
            txtDLLNo = (TextView)itemView.findViewById(R.id.txtDLLNo);
            txtDLGST = (TextView)itemView.findViewById(R.id.txtDLGST);
            txtDLCommission = (TextView)itemView.findViewById(R.id.txtDLCommission);
        }
    }
}
