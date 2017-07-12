package qsh.com.animalantiepidemic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import qsh.com.animalantiepidemic.R;
import qsh.com.animalantiepidemic.helper.PinYinUtil;
import qsh.com.animalantiepidemic.models.FarmerModel;

/**
 * Created by Administrator on 2017/7/12.
 */

public class FarmerArrayAdapter  extends ArrayAdapter<FarmerModel> {

    Context context;
    int resource, textViewResourceId;
    List<FarmerModel> items, tempItems, suggestions;

    public FarmerArrayAdapter(Context context, int resource, int textViewResourceId, List<FarmerModel> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<FarmerModel>(items); // this makes the difference.
        suggestions = new ArrayList<FarmerModel>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_farmer, parent, false);
        }
        FarmerModel farmer = items.get(position);
        if (farmer != null) {
            TextView householderTextView = (TextView) view.findViewById(R.id.label_householder);
            if (householderTextView != null)
                householderTextView.setText(farmer.getHouseholder());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            FarmerModel farmer = (FarmerModel)resultValue;
            final String farmerInfo = farmer.getHouseholder() + "(" + farmer.getMobile() + "-" + farmer.getAddress() + "-" + farmer.getBreedTypeName() + ")";
            return farmerInfo;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                String lowerCaseQuery = constraint.toString().trim().toLowerCase();
                for (FarmerModel farmer : tempItems) {
                    if (farmer.getAddress().contains(lowerCaseQuery)
                            || farmer.getMobile().contains(lowerCaseQuery)
                            || farmer.getHouseholder().contains(lowerCaseQuery)
                            || PinYinUtil.getFirstSpell(farmer.getAddress()).trim().toLowerCase().contains(lowerCaseQuery)
                            || PinYinUtil.getFirstSpell(farmer.getHouseholder()).trim().toLowerCase().contains(lowerCaseQuery)) {
                        suggestions.add(farmer);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<FarmerModel> filterList = (ArrayList<FarmerModel>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (FarmerModel farmer : filterList) {
                    add(farmer);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
