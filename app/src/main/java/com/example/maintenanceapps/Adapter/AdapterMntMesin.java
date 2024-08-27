package com.example.maintenanceapps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.Maintenance.DetailMntActivity;
import com.example.maintenanceapps.Model.ModelMaintenance;
import com.example.maintenanceapps.R;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterMntMesin extends RecyclerView.Adapter<AdapterMntMesin.HolderData> implements Filterable {
    private static final String TAG = "MNTMESIN_ADAPTER";

    private Context mContext;
    List<ModelMaintenance> listData;
    List<ModelMaintenance> listDataFull;

    public AdapterMntMesin(Context context, List<ModelMaintenance> listData) {
        mContext = context;
        this.listData = listData;
        listDataFull = new ArrayList<>(listData);
    }

    @NonNull
    @NotNull
    @Override
    public HolderData onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.complist_mnt_mesin, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HolderData holder, int position) {
        ModelMaintenance datamodel = listData.get(position);

        SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatOut = new SimpleDateFormat("dd MMMM yyyy");

        try {
            Date time = formatIn.parse(datamodel.getTimeError());
            holder.tvTime.setText(datamodel.getTimeError().substring(11, 16) + " | " + formatOut.format(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (datamodel.getID_Masalah() != null) {
            holder.tvMekanikal.setText(datamodel.getMasalah());
        } else {
            holder.tvMekanikal.setText("-");
        }

        if (datamodel.getTekDisplayAlarm() != null) {
            holder.tvTeknikal.setText(datamodel.getTekDisplayAlarm() + " | " + datamodel.getTekNamaAlarm());
        } else {
            holder.tvTeknikal.setText("-");
        }

        if (datamodel.getNamaSparepart() != null) {
            holder.tvBagianMesin.setText(datamodel.getNamaBagianMesin() + ", sparepart: " + datamodel.getNamaSparepart());
        } else {
            holder.tvBagianMesin.setText("-");
        }



        if (datamodel.getStatus().equals("0")) {
            holder.layoutInfo.setBackgroundResource(R.drawable.color_red);
        } else if (datamodel.getStatus().equals("1")) {
            if (datamodel.getTimeError().equals("0000-00-00 00:00:00")) {
                holder.layoutInfo.setBackgroundResource(R.drawable.color_purple);
            } else {
                holder.layoutInfo.setBackgroundResource(R.drawable.color_blue);
            }
        } else if (datamodel.getStatus().equals("2")) {
            if (datamodel.getTimeError().equals("0000-00-00 00:00:00")) {
                holder.tvTime.setText("Preventive Maintenance");
                holder.layoutInfo.setBackgroundResource(R.drawable.color_yellow);
            } else {
                holder.layoutInfo.setBackgroundResource(R.drawable.color_green);
            }
        }

        holder.dModul = datamodel;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public Filter getFilter() {
        return mekanikalFilter;
    }

    public Filter getFilterDisplayAlarm() {
        Log.d(TAG, "getFilterDisplayAlarm: ");
        return teknikalDisplayAlarmFilter;
    }

    public Filter getFilterNamaAlarm() {
        Log.d(TAG, "getFilterNamaAlarm: ");
        return teknikalNamaAlarmFilter;
    }

    public Filter getFilterBagianMesin() {
        Log.d(TAG, "getFilterBagianMesin: ");
        return bagianMesinFilter;
    }

    public Filter getFilterSparepart() {
        Log.d(TAG, "getFilterSparepart: ");
        return sparepartFilter;
    }

    private Filter mekanikalFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ModelMaintenance> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(listDataFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ModelMaintenance item : listDataFull) {
                    if (item.getMasalah() != null && item.getMasalah().toLowerCase().contains(filterPattern)) {
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

    private Filter teknikalDisplayAlarmFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ModelMaintenance> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(listDataFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ModelMaintenance item : listDataFull) {
                    if (item.getTekDisplayAlarm() != null && item.getTekDisplayAlarm().toLowerCase().contains(filterPattern)) {
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

    private Filter teknikalNamaAlarmFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ModelMaintenance> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(listDataFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ModelMaintenance item : listDataFull) {
                    if (item.getTekNamaAlarm() != null && item.getTekNamaAlarm().toLowerCase().contains(filterPattern)) {
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

    private Filter bagianMesinFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ModelMaintenance> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(listDataFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ModelMaintenance item : listDataFull) {
                    if (item.getNamaBagianMesin() != null && item.getNamaBagianMesin().toLowerCase().contains(filterPattern)) {
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

    private Filter sparepartFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ModelMaintenance> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(listDataFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ModelMaintenance item : listDataFull) {
                    if (item.getNamaSparepart() != null && item.getNamaSparepart().toLowerCase().contains(filterPattern)) {
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

    public void clearListData() {
        listData.clear();
        notifyDataSetChanged();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvTime, tvMekanikal, tvTeknikal, tvBagianMesin;
        CardView btnMnt;
        LinearLayout layoutInfo;
        ModelMaintenance dModul;

        public HolderData(View itemView) {
            super(itemView);

            tvTime = (TextView) itemView.findViewById(R.id.txt_time);
            tvMekanikal = (TextView) itemView.findViewById(R.id.txt_mekanikal);
            tvTeknikal = (TextView) itemView.findViewById(R.id.txt_teknikal);
            tvBagianMesin = (TextView) itemView.findViewById(R.id.txt_bagianmesin);
            layoutInfo = (LinearLayout) itemView.findViewById(R.id.layout_info_mnt);

            btnMnt = (CardView) itemView.findViewById(R.id.card_mnt);

            btnMnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailMntActivity.class);

                    Bundle args = new Bundle();
                    args.putString("id_mnt", dModul.getID());
                    intent.putExtras(args);

                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
