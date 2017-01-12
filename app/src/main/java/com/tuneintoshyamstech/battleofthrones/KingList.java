package com.tuneintoshyamstech.battleofthrones;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuneintoshyamstech.battleofthrones.model.Battle;
import com.tuneintoshyamstech.battleofthrones.model.King;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shyam_2 on 1/7/2017.
 */

public class KingList extends ArrayAdapter<King> implements Filterable {

    private Activity context;

    private KingFilter kingFilter;
    private List<King> filteredList;

    public KingList(Activity context, List<King> objects) {
        super(context, R.layout.king_cards, objects);
        this.context = context;
        this.filteredList = (ArrayList)objects;
        getFilter();
    }

    @Override
    public Filter getFilter() {
        if (kingFilter == null) {
            kingFilter = new KingFilter();
        }

        return kingFilter;
    }

    /**
     * Custom filter for battle list
     * Filter content in battle list according to the search text
     */
    private class KingFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<King> tempList = new ArrayList<King>();

                // search content in battle list
                for (King king : filteredList) {
                    if (king.getKing_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(king);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = filteredList.size();
                filterResults.values = filteredList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         *
         * @param constraint text
         * @param results    filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<King>) results.values;
            notifyDataSetChanged();
        }


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.king_cards, null, true);

        ImageView kingImage = (ImageView) listViewItem.findViewById(R.id.king_image);

        TextView kingName = (TextView) listViewItem.findViewById(R.id.king_name);

        King kingDetail = filteredList.get(position);
        kingName.setText(kingDetail.getKing_name());//battleNames[position]);

        //attackerStatus.setImageResource(attackerStatuses[position]);

        return listViewItem;
    }

    /**
     * Get size of user list
     * @return userList size
     */
    @Override
    public int getCount() {
        return filteredList.size();
    }

    /**
     * Get specific item from user list
     * @param i item index
     * @return list item
     */
    @Override
    public King getItem(int i) {
        return filteredList.get(i);
    }

    /**
     * Get user list item id
     * @param i item index
     * @return current item id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }
}
