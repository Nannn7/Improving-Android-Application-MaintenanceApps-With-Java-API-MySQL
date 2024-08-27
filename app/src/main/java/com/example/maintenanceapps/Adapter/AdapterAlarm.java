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

import com.example.maintenanceapps.Model.ModelAlarm;
import com.example.maintenanceapps.Modul.DefinisiActivity;
import com.example.maintenanceapps.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterAlarm extends RecyclerView.Adapter<AdapterAlarm.HolderData> implements Filterable {
    private static final String TAG = "ALARM_ADAPTER";

    private Context mContext;
    List<ModelAlarm> listData;
    List<ModelAlarm> listDataFull;

    public AdapterAlarm(Context context, List<ModelAlarm> listData) {
        mContext = context;
        this.listData = listData;
        listDataFull = new ArrayList<>(listData);
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.complist_alarm, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelAlarm datamodel = listData.get(position);

//        holder.tvModul.setText("Modul " + datamodel.getNamaJenis() + " " + datamodel.getNamaSeries());
        holder.tvDisplay.setText(datamodel.getDisplay());
        holder.tvNama.setText(datamodel.getNama());
        holder.dModul = datamodel;
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
            List<ModelAlarm> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(listDataFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ModelAlarm item : listDataFull) {
                    if (item.getDisplay().toLowerCase().contains(filterPattern)) {
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
        TextView tvModul;
        TextView tvDisplay, tvNama;
        CardView cardModul;
        ModelAlarm dModul;

        public HolderData(View itemView) {
            super(itemView);

//            tvModul = (TextView) itemView.findViewById(R.id.txt_modul);
            tvDisplay = (TextView) itemView.findViewById(R.id.txt_display);
            tvNama = (TextView) itemView.findViewById(R.id.txt_nama);
            cardModul = (CardView) itemView.findViewById(R.id.btn_modul);

            cardModul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DefinisiActivity.class);

                    Bundle args = new Bundle();
                    args.putString("id_alarm", dModul.getID());
                    args.putString("display", dModul.getDisplay());
                    args.putString("nama_alarm", dModul.getNama());
                    args.putString("nama_jenis", dModul.getNamaJenis());
                    args.putString("nama_series", dModul.getNamaSeries());
                    intent.putExtras(args);

                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
