package com.example.maintenanceapps.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Model.ModelDefinisi;
import com.example.maintenanceapps.Model.ModelDefinisiFoto;
import com.example.maintenanceapps.Model.ModelPenyebab;
import com.example.maintenanceapps.Model.ResponseDefinisiFoto;
import com.example.maintenanceapps.Model.ResponsePenyebab;
import com.example.maintenanceapps.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDefinisi extends RecyclerView.Adapter<AdapterDefinisi.HolderData> {
    private static final String TAG = "DEFINISI_ADAPTER";

    private Context mContext;
    private List<ModelDefinisi> listData;
    private List<ModelPenyebab> listPenyebab;
    private List<ModelDefinisiFoto> listFoto;

//    private RecyclerView rvPenyebab;
//    private AdapterPenyebab adPenyebab;
//    private RecyclerView.LayoutManager lmPenyebab;

    public AdapterDefinisi(Context context, List<ModelDefinisi> listData) {
        mContext = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.complist_definisi, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderData holder, int position) {
        ModelDefinisi datamodel = listData.get(position);

        holder.tvDefinisi.setText(datamodel.getDefinisi());
        holder.adModel = datamodel;
//        holder.gvFoto.setAdapter(new AdapterFotoDefinisi(datamodel.getID()));

        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        Call<ResponsePenyebab> listRespPenyebab = aiData.getPenyebab(datamodel.getID());

        listRespPenyebab.enqueue(new Callback<ResponsePenyebab>() {
            @Override
            public void onResponse(Call<ResponsePenyebab> call, Response<ResponsePenyebab> response) {
                String pesan = response.body().getPesan();
                Log.d(TAG, "Respons: Data Penyebab");

                listPenyebab = response.body().getResult();

                holder.adPenyebab = new AdapterPenyebab(listPenyebab);
                holder.rvPenyebab.setAdapter(holder.adPenyebab);
                holder.adPenyebab.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponsePenyebab> call, Throwable t) {
                Log.d(TAG, "Faiure: Data Penyebab, error: " + t.getMessage());
            }
        });

        final Call<ResponseDefinisiFoto> listRespFoto = aiData.getDefinisiFoto(datamodel.getID());

        listRespFoto.enqueue(new Callback<ResponseDefinisiFoto>() {
            @Override
            public void onResponse(Call<ResponseDefinisiFoto> call, Response<ResponseDefinisiFoto> response) {
                String pesan = response.body().getPesan();

                listFoto = response.body().getResult();
                Log.d(TAG, "Respons: listFoto: " + listFoto.size());

                if (listFoto.size() != 0) {
                    holder.adFoto = new AdapterDefinisiFoto(holder.mView.getContext(), listFoto);
                    holder.rvFoto.setAdapter(holder.adFoto);
                    holder.adFoto.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseDefinisiFoto> call, Throwable t) {
                Log.d(TAG, "Faiure: Data Definisi Foto, error: " + t.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvDefinisi;
        ModelDefinisi adModel;
        View mView;

        RecyclerView rvPenyebab;
        AdapterPenyebab adPenyebab;
        RecyclerView.LayoutManager lmPenyebab;

        RecyclerView rvFoto;
        AdapterDefinisiFoto adFoto;
        RecyclerView.LayoutManager lmFoto;

        public HolderData(final View itemView) {
            super(itemView);

            mView = itemView;
            tvDefinisi = (TextView) itemView.findViewById(R.id.txt_definisi);

            rvPenyebab = itemView.findViewById(R.id.rvPenyebab);
            lmPenyebab = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false);
            rvPenyebab.setLayoutManager(lmPenyebab);

            rvFoto = itemView.findViewById(R.id.rvFoto);
            lmFoto = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            rvFoto.setLayoutManager(lmFoto);
        }
    }
}











