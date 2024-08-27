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

import com.example.maintenanceapps.Model.ModelAlarm;
import com.example.maintenanceapps.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterAutoCompAlarm extends ArrayAdapter<ModelAlarm> {
    private List<ModelAlarm> alarmListFull;

    public AdapterAutoCompAlarm(Context context, List<ModelAlarm> alarmList) {
        super(context, 0, alarmList);
        alarmListFull = new ArrayList<>(alarmList);
    }

    public Filter getFilter() {
        return alarmFilter;
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

        ModelAlarm alarmItem = getItem(position);

        if (alarmItem != null) {
            tvSpin.setText(alarmItem.getDisplay());
        }

        return convertView;
    }

    private Filter alarmFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ModelAlarm> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(alarmListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ModelAlarm item : alarmListFull) {
                    if (item.getDisplay().toLowerCase().contains(filterPattern)) {
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
            return ((ModelAlarm) resultValue).getDisplay();
        }
    };
}
