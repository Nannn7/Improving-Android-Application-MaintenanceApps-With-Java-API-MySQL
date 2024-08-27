package com.example.maintenanceapps.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Model.ModelDefinisiFoto;
import com.example.maintenanceapps.Model.ResponseDefinisiFoto;
import com.example.maintenanceapps.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterFotoDefinisi extends BaseAdapter {
    private static final String TAG = "FOTODEFINISI_ADAPTER";
//    private static final String BASE_URL = "http://localhost/FIM_Maintenance/Image/Alarm_Definisi/";
    private static final String BASE_URL = "http://192.168.1.53/FIM_Maintenance/Image/Alarm_Definisi/";

    private Context mContext;
    private ArrayList<String> mFoto = new ArrayList<String>();

    public AdapterFotoDefinisi(Context context) {
        mContext = context;
    }

    public AdapterFotoDefinisi(String id_definisi) {
        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        Call<ResponseDefinisiFoto> listRespFoto = aiData.getDefinisiFoto(id_definisi);
        listRespFoto.enqueue(new Callback<ResponseDefinisiFoto>() {
            @Override
            public void onResponse(Call<ResponseDefinisiFoto> call, Response<ResponseDefinisiFoto> response) {
                String pesan = response.body().getPesan();

                List<ModelDefinisiFoto> listFoto = response.body().getResult();
                for (ModelDefinisiFoto foto: listFoto) {
                    Log.d(TAG, "ResponsFoto: " + foto.getFoto());
                    mFoto.add(BASE_URL + foto.getFoto());
                }
            }

            @Override
            public void onFailure(Call<ResponseDefinisiFoto> call, Throwable t) {
                Log.d(TAG, "Faiure: Data Foto, error: " + t.getMessage());
            }
        });
    }

    @Override
    public int getCount() {
        return mFoto.size();
    }

    @Override
    public String getItem(int i) {
        return mFoto.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(340, 340));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) view;
        }
        String url = getItem(i);
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.loader)
                .fit()
                .centerCrop()
                .into(imageView);

        return imageView;
    }
}
