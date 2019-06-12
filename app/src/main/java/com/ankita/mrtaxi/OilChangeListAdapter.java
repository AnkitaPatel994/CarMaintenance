package com.ankita.mrtaxi;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

class OilChangeListAdapter extends RecyclerView.Adapter<OilChangeListAdapter.ViewHolder>{

    Context context;
    ArrayList<HashMap<String, String>> oilChangeListArray;
    View v;

    public OilChangeListAdapter(Context context, ArrayList<HashMap<String, String>> oilChangeListArray) {
        this.context = context;
        this.oilChangeListArray = oilChangeListArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.oil_change_list, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        final String o_id = oilChangeListArray.get(i).get("o_id");
        final String v_id = oilChangeListArray.get(i).get("v_id");
        final String v_name = oilChangeListArray.get(i).get("v_name");
        final String v_no = oilChangeListArray.get(i).get("v_no");
        final String o_v_kilometer = oilChangeListArray.get(i).get("o_v_kilometer");
        final String o_cost = oilChangeListArray.get(i).get("o_cost");
        final String o_maintenance = oilChangeListArray.get(i).get("o_maintenance");
        final String o_m_cost = oilChangeListArray.get(i).get("o_m_cost");
        String o_date = oilChangeListArray.get(i).get("o_date");

        if (o_cost.equals(""))
        {
            holder.llOil.setVisibility(View.GONE);
        }

        if (o_maintenance.equals(""))
        {
            holder.txtVOMaintenance.setVisibility(View.GONE);
        }

        if (o_m_cost.equals(""))
        {
            holder.txtVOMaintenanceCost.setVisibility(View.GONE);
        }

        holder.txtVONameNo.setText(v_name+" - "+v_no);
        holder.txtVOKilometer.setText(o_v_kilometer+"km");
        holder.txtVOCost.setText("$"+o_cost);
        holder.txtVOMaintenanceCost.setText("$"+o_m_cost);
        holder.txtVODate.setText(o_date);
        holder.txtVOMaintenance.setText(o_maintenance);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,AddOilChangeActivity.class);
                i.putExtra("flag","edit");
                i.putExtra("o_id",o_id);
                i.putExtra("v_id",v_id);
                i.putExtra("v_name",v_name);
                i.putExtra("v_no",v_no);
                i.putExtra("o_v_kilometer",o_v_kilometer);
                i.putExtra("o_maintenance",o_maintenance);
                i.putExtra("o_m_cost",o_m_cost);
                i.putExtra("o_cost",o_cost);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return oilChangeListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtVONameNo,txtVOKilometer,txtVODate,txtVOCost,txtVOMaintenance,txtVOMaintenanceCost;
        LinearLayout llOil;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            llOil = (LinearLayout) itemView.findViewById(R.id.llOil);
            txtVONameNo = (TextView)itemView.findViewById(R.id.txtVONameNo);
            txtVOKilometer = (TextView)itemView.findViewById(R.id.txtVOKilometer);
            txtVODate = (TextView)itemView.findViewById(R.id.txtVODate);
            txtVOMaintenance = (TextView)itemView.findViewById(R.id.txtVOMaintenance);
            txtVOMaintenanceCost = (TextView)itemView.findViewById(R.id.txtVOMaintenanceCost);
            txtVOCost = (TextView)itemView.findViewById(R.id.txtVOCost);

        }
    }
}
