package com.example.maintenanceapps.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.maintenanceapps.Model.ModelPenyebab;
import com.example.maintenanceapps.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterAutoCompPenyebab extends ArrayAdapter<ModelPenyebab> {
    private List<ModelPenyebab> dataListFull;

    public AdapterAutoCompPenyebab(Context context, List<ModelPenyebab> objects) {
        super(context, 0, objects);
        dataListFull = new ArrayList<>(objects);
    }

    public Filter getFilter() {
        return dataFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.comp_spinner, parent, false
            );
        }

        TextView tvSpin = convertView.findViewById(R.id.txt_spin);

        ModelPenyebab dataItem = getItem(position);

        if (dataItem != null) {
            tvSpin.setText(dataItem.getPenyebab());
        }

        return convertView;
    }

    private Filter dataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ModelPenyebab> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(dataListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ModelPenyebab item : dataListFull) {
                    if (item.getPenyebab().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((ModelPenyebab) resultValue).getPenyebab();
        }
    };
}
