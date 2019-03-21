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

class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> clientListArray;
    View v;

    public ClientListAdapter(Context context, ArrayList<HashMap<String, String>> clientListArray) {
        this.context = context;
        this.clientListArray = clientListArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.client_list, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final String client_id = clientListArray.get(i).get("client_id");
        final String client_name = clientListArray.get(i).get("client_name");

        viewHolder.txtCLClientName.setText(client_name);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,AddClientActivity.class);
                i.putExtra("flag","edit");
                i.putExtra("client_id",client_id);
                i.putExtra("client_name",client_name);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return clientListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCLClientName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCLClientName = (TextView)itemView.findViewById(R.id.txtCLClientName);
        }
    }
}
