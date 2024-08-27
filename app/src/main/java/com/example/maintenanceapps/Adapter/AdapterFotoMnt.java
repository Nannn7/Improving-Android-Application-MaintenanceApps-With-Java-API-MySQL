package com.example.maintenanceapps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.FullImageActivity;
import com.example.maintenanceapps.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class AdapterFotoMnt extends RecyclerView.Adapter<AdapterFotoMnt.HolderData> {
    private static final String TAG = "FOTOMNT_ADAPTER";
//    private static final String BASE_URL = "http://192.168.1.183/FIM_Maintenance/Image/Maintenance/";

    private Context mContext;
    private List<File> listFoto;

    public AdapterFotoMnt(Context context, List<File> listFoto) {
        mContext = context;
        this.listFoto = listFoto;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.complist_foto, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        File foto = listFoto.get(position);
        holder.dModel = foto;

        Picasso.get()
                .load(foto)
                .placeholder(R.drawable.loader)
                .fit()
                .centerCrop()
                .into(holder.foto);
    }

    @Override
    public int getItemCount() {
        return listFoto.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        ImageView foto;
        File dModel;
        View mView;
        CardView delete;

        public HolderData(View itemView) {
            super(itemView);

            foto = (ImageView) itemView.findViewById(R.id.foto);
            delete = (CardView) itemView.findViewById(R.id.delete_image);
            mView = itemView;

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listFoto.remove(dModel);
                    dModel.delete();
                    notifyDataSetChanged();
                }
            });

            foto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), FullImageActivity.class);

                    Bundle args = new Bundle();
                    args.putString("url", dModel.getAbsolutePath());
                    intent.putExtras(args);
                    Log.d(TAG, "url: " + dModel.getAbsolutePath());

                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
