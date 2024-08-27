package com.example.maintenanceapps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.MainActivity;
import com.example.maintenanceapps.Maintenance.CheckMntActivity;
import com.example.maintenanceapps.Model.ModelMaintenance;
import com.example.maintenanceapps.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterMnt extends RecyclerView.Adapter<AdapterMnt.HolderData> {
    private static final String TAG = "ADAPTER_MNT";

    private Context mContext;
    private List<ModelMaintenance> listMnt;

    public AdapterMnt(Context context, List<ModelMaintenance> listMnt) {
        mContext = context;
        this.listMnt = listMnt;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.complist_mnt, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelMaintenance datamodel = listMnt.get(position);

        holder.dModel = datamodel;
        holder.tvLine.setText(datamodel.getNamaLine());
        holder.tvNoMesin.setText(datamodel.getNamaMesin());
        holder.tvProsesMesin.setText("Proses " + datamodel.getNamaProses());
        holder.jenisMnt = "0";

        if (datamodel.getNamaPIC() != null) holder.tvNamaPIC.setText("PIC " + datamodel.getNamaPIC());
//        else  holder.tvNamaPIC.setText("PIC -");

        SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatOut = new SimpleDateFormat("dd MMMM yyyy");

        if (datamodel.getTimeError().equals("0000-00-00 00:00:00")) {
            holder.jenisMnt = "1";
            holder.tvWaktuError.setText("Preventive Maintenance");
        } else {
            holder.jenisMnt = "0";
            try {
                Date time = formatIn.parse(datamodel.getTimeError());
                holder.tvWaktuError.setText(datamodel.getTimeError().substring(11, 16) + " | " + formatOut.format(time));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (datamodel.getStatus().equals("0")) {
            holder.tvJudulKet.setText("Waktu mulai maintenance : ");
            holder.layoutInfo.setBackgroundResource(R.drawable.color_red);
            holder.tvWaktuMulai.setText("-");
        } else if (datamodel.getStatus().equals("1")) {
            holder.tvJudulKet.setText("Waktu mulai maintenance : ");

            try {
                Date time = formatIn.parse(datamodel.getTimeStart());
                holder.tvWaktuMulai.setText(datamodel.getTimeStart().substring(11, 16) + " | " + formatOut.format(time));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (datamodel.getTimeError().equals("0000-00-00 00:00:00")) {
                holder.layoutInfo.setBackgroundResource(R.drawable.color_purple);
            } else {
                holder.layoutInfo.setBackgroundResource(R.drawable.color_blue);
            }
        } else if (datamodel.getStatus().equals("2")) {

            holder.tvJudulKet.setText("Lama penanganan : ");

            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            if (!datamodel.getTimeStart().equals("0000-00-00 00:00:00")
                    && !datamodel.getTimeStop().equals("0000-00-00 00:00:00")) {

                try {
                    Date firstDate = formatDate.parse(datamodel.getTimeStart());
                    Date secondDate = formatDate.parse(datamodel.getTimeStop());

                    holder.tvWaktuMulai.setText(printDifference(firstDate, secondDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                holder.tvWaktuMulai.setText("-");
            }

            if (datamodel.getTimeError().equals("0000-00-00 00:00:00")) {
                holder.layoutInfo.setBackgroundResource(R.drawable.color_yellow);
            } else {
                holder.layoutInfo.setBackgroundResource(R.drawable.color_green);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listMnt.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvLine, tvNoMesin, tvProsesMesin;
        TextView tvNamaPIC, tvWaktuError, tvWaktuMulai;
        TextView tvJudulKet;
        LinearLayout layoutInfo;
        CardView cardMnt;
        String jenisMnt;

        ModelMaintenance dModel;

        public HolderData(View itemView) {
            super(itemView);

            tvLine = (TextView) itemView.findViewById(R.id.txt_line);
            tvNoMesin = (TextView) itemView.findViewById(R.id.txt_nomesin);
            tvProsesMesin = (TextView) itemView.findViewById(R.id.txt_proses_mesin);

            tvNamaPIC = (TextView) itemView.findViewById(R.id.txt_namapic);
            tvWaktuError = (TextView) itemView.findViewById(R.id.txt_time_error);
            tvWaktuMulai = (TextView) itemView.findViewById(R.id.txt_time_mulai);

            tvJudulKet = (TextView) itemView.findViewById(R.id.txt_judul_ket);

            layoutInfo = (LinearLayout) itemView.findViewById(R.id.layout_info_mnt);

            cardMnt = (CardView) itemView.findViewById(R.id.card_mnt);

            cardMnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (dModel.getStatus().equals("1")) {
                        if (MainActivity.prefConfig.readID().equals(dModel.getID_PIC())) {
                            Intent intent = new Intent(view.getContext(), CheckMntActivity.class);

                            Bundle args = new Bundle();
                            args.putString("id_mnt", dModel.getID());
                            args.putString("id_series", dModel.getID_SeriesKontrol());
                            args.putString("id_mesin", dModel.getID_Mesin());
                            args.putString("id_line", dModel.getID_Line());
                            args.putString("jenis_mnt", jenisMnt);
                            intent.putExtras(args);

                            view.getContext().startActivity(intent);
                        }
                    }

                }
            });
        }
    }

    public String printDifference(Date startDate, Date endDate) {
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;

        return elapsedDays + " hari, " + elapsedHours + " jam, " + elapsedMinutes + " menit";
    }
}
