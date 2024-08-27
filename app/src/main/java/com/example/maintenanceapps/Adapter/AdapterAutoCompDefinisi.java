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

import com.example.maintenanceapps.Model.ModelDefinisi;
import com.example.maintenanceapps.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterAutoCompDefinisi extends ArrayAdapter<ModelDefinisi> {
    private List<ModelDefinisi> listDataFull;

    public AdapterAutoCompDefinisi(Context context, List<ModelDefinisi> objects) {
        super(context, 0, objects);
        listDataFull = new ArrayList<>(objects);
    }

    public Filter getFilter() {
        return definisiFilter;
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

        ModelDefinisi item = getItem(position);

        if (item != null) {
            tvSpin.setText(item.getDefinisi());
        }

        return convertView;
    }

    private Filter definisiFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ModelDefinisi> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(listDataFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ModelDefinisi item : listDataFull) {
                    if (item.getDefinisi().toLowerCase().contains(filterPattern)) {
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
            return ((ModelDefinisi) resultValue).getDefinisi();
        }
    };
}
