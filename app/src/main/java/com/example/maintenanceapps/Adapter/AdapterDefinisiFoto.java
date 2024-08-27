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
import com.example.maintenanceapps.Model.ModelDefinisiFoto;
import com.example.maintenanceapps.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterDefinisiFoto extends RecyclerView.Adapter<AdapterDefinisiFoto.HolderData>{
    private static final String TAG = "FOTODEFINISI_ADAPTER";
    private static final String BASE_URL = APIClient.BASE_IP + "FIM_Maintenance/Image/Alarm_Definisi/";

    private Context mContext;
    private List<ModelDefinisiFoto> listFoto;

    public AdapterDefinisiFoto(Context context, List<ModelDefinisiFoto> listFoto) {
        this.mContext = context;
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
        ModelDefinisiFoto datamodel = listFoto.get(position);
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
        ModelDefinisiFoto dModel;
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
