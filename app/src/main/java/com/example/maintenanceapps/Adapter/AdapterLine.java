package com.example.maintenanceapps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.Maintenance.MesinActivity;
import com.example.maintenanceapps.Model.ModelLine;
import com.example.maintenanceapps.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterLine extends RecyclerView.Adapter<AdapterLine.HolderData> implements Filterable {
    private static final String TAG = "LINE_ADAPTER";

    private Context mContext;
    List<ModelLine> listData;
    List<ModelLine> listDataFull;

    public AdapterLine(Context context, List<ModelLine> listData) {
        mContext = context;
        this.listData = listData;
        listDataFull = new ArrayList<>(listData);
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.complist_line, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelLine datamodel = listData.get(position);

        holder.tvLine.setText(datamodel.getNama());
        holder.dModul = datamodel;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public Filter getFilter() {
        return lineFilter;
    }

    private Filter lineFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ModelLine> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(listDataFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ModelLine item : listDataFull) {
                    if (item.getNama().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listData.clear();
            listData.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvLine;
        CardView btnLine;
        ModelLine dModul;

        public HolderData(View itemView) {
            super(itemView);

            tvLine = itemView.findViewById(R.id.txt_line);
            btnLine = itemView.findViewById(R.id.btn_line);

            btnLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), MesinActivity.class);

                    Bundle args = new Bundle();
                    args.putString("id_line", dModul.getID());
                    args.putString("nama_line", dModul.getNama());
                    args.putString("proses", dModul.getNama_Proses());
                    intent.putExtras(args);

                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
