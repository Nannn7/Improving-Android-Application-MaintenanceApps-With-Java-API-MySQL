package com.example.maintenanceapps.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.maintenanceapps.Model.ModelSparepart;
import com.example.maintenanceapps.R;

import java.util.ArrayList;

public class AdapterSpinnerSparepart extends ArrayAdapter<ModelSparepart> {


    public AdapterSpinnerSparepart(Context context, ArrayList<ModelSparepart> spList) {
        super(context, 0, spList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.comp_spinner, parent, false
            );
        }

        TextView tvSpin = convertView.findViewById(R.id.txt_spin);

        ModelSparepart currentItem = getItem(position);

        if (currentItem != null) {
            tvSpin.setText(currentItem.getNama());
        }

        return convertView;
    }
}
