package com.example.maintenanceapps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.Model.ModelModul;
import com.example.maintenanceapps.Modul.AlarmActivity;
import com.example.maintenanceapps.R;

import java.util.List;

public class AdapterModul extends RecyclerView.Adapter<AdapterModul.HolderData> {
    private Context mContext;
    List<ModelModul> listData;

    public AdapterModul(Context context, List<ModelModul> listData) {
        mContext = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.complist_modul, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelModul datamodel = listData.get(position);

        holder.tvModul.setText("Modul " + datamodel.getNamaJenis() + " " + datamodel.getNamaSeries());
        holder.dModul = datamodel;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvModul;
        CardView cardModul;
        ModelModul dModul;

        public HolderData(View itemView) {
            super(itemView);

            tvModul = (TextView) itemView.findViewById(R.id.txt_modul);
            cardModul = (CardView) itemView.findViewById(R.id.btn_modul);

            cardModul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AlarmActivity.class);

                    Bundle args = new Bundle();
                    args.putString("id_jenis", dModul.getID_JenisKontrol());
                    args.putString("id_series", dModul.getID_SeriesKontrol());
                    args.putString("nama_jenis", dModul.getNamaJenis());
                    args.putString("nama_series", dModul.getNamaSeries());
                    intent.putExtras(args);

                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
