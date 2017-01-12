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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shyam_2 on 1/7/2017.
 */

public class BattleList extends ArrayAdapter<Battle> implements Filterable {
    private String[] battleNames;
    private String[] attackerNames;
    private String[] defenderNames;
    private String[] attackerStatuses;
    private Activity context;

    private BattleFilter battleFilter;
    private List<Battle> filteredList;

    public BattleList(Activity context, String[] battleNames, String[] attackerNames, String[] defenderNames, String[] attackerStatuses, List<Battle> objects) {
        super(context, R.layout.battle_cards, objects);
        this.context = context;
        this.battleNames = battleNames;
        this.attackerNames = attackerNames;
        this.defenderNames = defenderNames;
        this.attackerStatuses = attackerStatuses;
        this.filteredList = (ArrayList)objects;
        getFilter();
    }

    @Override
    public Filter getFilter() {
        if (battleFilter == null) {
            battleFilter = new BattleFilter();
        }

        return battleFilter;
    }

    /**
     * Custom filter for battle list
     * Filter content in battle list according to the search text
     */
    private class BattleFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Battle> tempList = new ArrayList<Battle>();

                // search content in battle list
                for (Battle battle : filteredList) {
                    if (battle.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(battle);
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
            filteredList = (ArrayList<Battle>) results.values;
            notifyDataSetChanged();
        }


    }
        @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.battle_cards, null, true);
        TextView battleName = (TextView) listViewItem.findViewById(R.id.battle_name);

        ImageView attackerImage = (ImageView) listViewItem.findViewById(R.id.attacker_image);
        ImageView defenderImage = (ImageView) listViewItem.findViewById(R.id.defender_image);

        TextView attackerName = (TextView) listViewItem.findViewById(R.id.attacker_name);
        TextView defenderName = (TextView) listViewItem.findViewById(R.id.defender_name);

        ImageView attackerStatus = (ImageView) listViewItem.findViewById(R.id.attacker_status);
        ImageView defenderStatus = (ImageView) listViewItem.findViewById(R.id.defender_status);
        Battle battleDetail = filteredList.get(position);
        battleName.setText(battleDetail.getName());//battleNames[position]);
        attackerName.setText(battleDetail.getAttacker_king());//attackerNames[position]);
        defenderName.setText(battleDetail.getDefender_king());//defenderNames[position]);
            attackerImage.setImageResource(R.drawable.jeoffrey);
            defenderImage.setImageResource(R.drawable.s3e9_robb_stark_main);

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
    public Battle getItem(int i) {
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
