package com.example.maintenanceapps.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.Model.ModelPenyebab;
import com.example.maintenanceapps.R;

import java.util.List;

public class AdapterPenyebab extends RecyclerView.Adapter<AdapterPenyebab.HolderData> {
    private static final String TAG = "PENYEBAB_ADAPTER";

    List<ModelPenyebab> listData;

    public AdapterPenyebab(List<ModelPenyebab> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.complist_penyebab, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelPenyebab datamodel = listData.get(position);

        holder.tvPenyebab.setText(datamodel.getPenyebab());
        holder.tvPerbaikan.setText(datamodel.getPerbaikan());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvPenyebab, tvPerbaikan;

        public HolderData(View itemView) {
            super(itemView);

            tvPenyebab = (TextView) itemView.findViewById(R.id.txt_penyebab);
            tvPerbaikan = (TextView) itemView.findViewById(R.id.txt_perbaikan);
        }
    }
}
