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

import com.example.maintenanceapps.Model.ModelLine;
import com.example.maintenanceapps.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterAutoCompLine extends ArrayAdapter<ModelLine> {
    private List<ModelLine> lineListFull;

    public AdapterAutoCompLine(Context context, List<ModelLine> lineList) {
        super(context, 0, lineList);
        lineListFull = new ArrayList<>(lineList);
    }

    public Filter getFilter(){return lineFilter;}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.complist_line, parent, false
            );
        }

        TextView tvLine = convertView.findViewById(R.id.txt_line);

        ModelLine lineItem = getItem(position);

        if (lineItem != null) {
            tvLine.setText(lineItem.getDisplay());
        }

        return convertView;
    }

    private Filter lineFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ModelLine> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(lineListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ModelLine item : lineListFull) {
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
            return ((ModelLine) resultValue).getDisplay();
        }
    };
}
