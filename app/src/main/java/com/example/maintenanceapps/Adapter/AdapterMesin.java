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

import com.example.maintenanceapps.Maintenance.RiwayatMesinActivity;
import com.example.maintenanceapps.Model.ModelMesin;
import com.example.maintenanceapps.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterMesin extends RecyclerView.Adapter<AdapterMesin.HolderData> implements Filterable {
    private static final String TAG = "MESIN_ADAPTER";

    private Context mContext;
    List<ModelMesin> listData;
    List<ModelMesin> listDataFull;

    public AdapterMesin(Context context, List<ModelMesin> listData) {
        mContext = context;
        this.listData = listData;
        listDataFull = new ArrayList<>(listData);
    }

    @NonNull
    @NotNull
    @Override
    public HolderData onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.complist_line, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HolderData holder, int position) {
        ModelMesin datamodel = listData.get(position);

        holder.tvLine.setText(datamodel.getNoMesin() + " ( Proses  " + datamodel.getNama_Proses() + " )");
        holder.dModul = datamodel;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public Filter getFilter() {
        return dataFilter;
    }

    private final Filter dataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ModelMesin> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(listDataFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ModelMesin item : listDataFull) {
                    if (item.getNoMesin() != null){
                        if (item.getNoMesin().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
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
        ModelMesin dModul;

        public HolderData(View itemView) {
            super(itemView);

            tvLine = (TextView) itemView.findViewById(R.id.txt_line);
            btnLine = (CardView) itemView.findViewById(R.id.btn_line);

            btnLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), RiwayatMesinActivity.class);

                    Bundle args = new Bundle();
                    args.putString("id_mesin", dModul.getID());
                    args.putString("nama_line", dModul.getNama_Line());
                    args.putString("no_mesin", dModul.getNoMesin());
                    args.putString("proses", dModul.getNama_Proses());
                    args.putString("modul", dModul.getNamaJenis() + ", " + dModul.getNamaSeries());
                    intent.putExtras(args);

                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
