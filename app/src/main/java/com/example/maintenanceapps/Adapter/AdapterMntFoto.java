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

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.FullImageActivity;
import com.example.maintenanceapps.Model.ModelMaintenanceDetail;
import com.example.maintenanceapps.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterMntFoto extends RecyclerView.Adapter<AdapterMntFoto.HolderData> {
    private static final String TAG = "MNTFOTO_ADAPTER";
    private static final String BASE_URL = APIClient.BASE_IP + "FIM_Maintenance/Image/Maintenance/";

    private Context mContext;
    private List<ModelMaintenanceDetail> listFoto;

    public AdapterMntFoto(Context context, List<ModelMaintenanceDetail> listFoto) {
        this.mContext = context;
        this.listFoto = listFoto;
    }

    @NonNull
    @NotNull
    @Override
    public HolderData onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.complist_foto, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HolderData holder, int position) {
        ModelMaintenanceDetail datamodel = listFoto.get(position);
        holder.dModel = datamodel;

        holder.delete.setVisibility(View.GONE);
        Picasso.get()
                .load(BASE_URL + datamodel.getFoto())
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
        ModelMaintenanceDetail dModel;
        View mView;
        CardView delete;

        public HolderData(View itemView) {
            super(itemView);

            foto = (ImageView) itemView.findViewById(R.id.foto);
            delete = (CardView) itemView.findViewById(R.id.delete_image);
            mView = itemView;

            foto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), FullImageActivity.class);

                    Bundle args = new Bundle();
                    args.putString("url", BASE_URL + dModel.getFoto());
                    intent.putExtras(args);
                    Log.d(TAG, "activity: " + view.getContext().getPackageName());

                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
