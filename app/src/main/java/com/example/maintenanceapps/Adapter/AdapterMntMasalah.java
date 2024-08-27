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

import com.example.maintenanceapps.Maintenance.EndMntActivity;
import com.example.maintenanceapps.Model.ModelMasalah;
import com.example.maintenanceapps.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterMntMasalah extends RecyclerView.Adapter<AdapterMntMasalah.HolderData> implements Filterable {
    private static final String TAG = "MNT_MASALAH_ADAPTER";

    private Context mContext;
    List<ModelMasalah> listData;
    List<ModelMasalah> listDataFull;
    String idMnt, idSeries, idMesin, idLine, jenisMnt;

    public AdapterMntMasalah(Context context, List<ModelMasalah> listData, String idMnt, String idSeries, String idMesin, String idLine, String jenisMnt) {
        mContext = context;
        this.listData = listData;
        this.idMnt = idMnt;
        this.idSeries = idSeries;
        this.idMesin = idMesin;
        this.idLine = idLine;
        this.jenisMnt = jenisMnt;
        listDataFull = new ArrayList<>(listData);
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.complist_masalah, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelMasalah datamodel = listData.get(position);

        holder.dModel = datamodel;
        holder.tvMasalah.setText(datamodel.getMasalah());
        holder.tvPenyebab.setText(datamodel.getPenyebab());
        holder.tvPenanganan.setText(datamodel.getPenanganan());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public Filter getFilter() {
        return alarmFilter;
    }

    private Filter alarmFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ModelMasalah> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(listDataFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ModelMasalah item : listDataFull) {
                    if (item.getMasalah().toLowerCase().contains(filterPattern)) {
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
        TextView tvMasalah, tvPenyebab, tvPenanganan;
        CardView card;

        ModelMasalah dModel;

        public HolderData(View itemView) {
            super(itemView);

            tvMasalah = (TextView) itemView.findViewById(R.id.txt_masalah);
            tvPenyebab = (TextView) itemView.findViewById(R.id.txt_penyebab);
            tvPenanganan = (TextView) itemView.findViewById(R.id.txt_penanganan);
            card = (CardView) itemView.findViewById(R.id.card_masalah);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), EndMntActivity.class);

                    Bundle args = new Bundle();
                    args.putString("id_mnt", idMnt);
                    args.putString("id_series", idSeries);
                    args.putString("id_mesin", idMesin);
                    args.putString("id_line", idLine);
                    args.putString("jenis_mnt", jenisMnt);
                    args.putString("id_masalah", dModel.getID());
                    intent.putExtras(args);

                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
