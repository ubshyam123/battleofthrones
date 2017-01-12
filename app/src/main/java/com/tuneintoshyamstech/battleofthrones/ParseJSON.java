package com.tuneintoshyamstech.battleofthrones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shyam_2 on 1/7/2017.
 */

public class ParseJSON {
    public static int[] ids;
    public static String[] battleNames;
    public static String[] attackerNames;
    public static String[] defenderNames;
    public static String[] attackerStatuses;
    public static int[] years;
    public static String[] attacker1names;
    public static String[] attacker2names;
    public static String[] attacker3names;
    public static String[] attacker4names;
    public static String[] defender1names;
    public static String[] defender2names;
    public static String[] defender3names;
    public static String[] defender4names;
    public static String[] battleTypes;
    public static int[] majorDeaths;
    public static int[] majorCaptures;
    public static String[] attackerSizes;
    public static String[] defenderSizes;
    public static String[] attackerCommanders;
    public static String[] defenderCommanders;
    public static String[] summers;
    public static String[] locations;
    public static String[] regions;
    public static String[] notes;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_ID = "battle_number";
    public static final String KEY_BATTLE_NAME = "name";
    public static final String KEY_ATTACKER_NAME = "attacker_king";
    public static final String KEY_DEFENDER_NAME = "defender_king";
    public static final String KEY_ATTACKER_STATUS = "attacker_outcome";
    public static final String KEY_YEAR = "year";
    public static final String KEY_ATTACKER_1 = "attacker_1";
    public static final String KEY_ATTACKER_2 = "attacker_2";
    public static final String KEY_ATTACKER_3 = "attacker_3";
    public static final String KEY_ATTACKER_4 = "attacker_4";
    public static final String KEY_DEFENDER_1 = "defender_1";
    public static final String KEY_DEFENDER_2 = "defender_2";
    public static final String KEY_DEFENDER_3 = "defender_3";
    public static final String KEY_DEFENDER_4 = "defender_4";
    public static final String KEY_BATTLE_TYPE = "battle_type";
    public static final String KEY_MAJOR_DEATH = "major_death";
    public static final String KEY_MAJOR_CAPTURE = "major_capture";
    public static final String KEY_ATTACKER_SIZE = "attacker_size";
    public static final String KEY_DEFENDER_SIZE= "defender_size";
    public static final String KEY_ATTACKER_COMMANDER = "attacker_commander";
    public static final String KEY_DEFENDER_COMMANDER = "defender_commander";
    public static final String KEY_SUMMER = "summer";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_REGION = "region";
    public static final String KEY_NOTE = "note";


    private JSONArray battles = null;

    private String json;

    public ParseJSON(String json){
        this.json = "{\"result\":"+json+"}";
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            battles = jsonObject.getJSONArray(JSON_ARRAY);
            years = new int[battles.length()];
            ids = new int[battles.length()];
            battleNames = new String[battles.length()];
            attackerNames = new String[battles.length()];
            defenderNames = new String[battles.length()];
            attackerStatuses = new String[battles.length()];
            attacker1names = new String[battles.length()];
            attacker2names = new String[battles.length()];
            attacker3names = new String[battles.length()];
            attacker4names = new String[battles.length()];
            defender1names = new String[battles.length()];
            defender2names = new String[battles.length()];
            defender3names = new String[battles.length()];
            defender4names = new String[battles.length()];
            battleTypes = new String[battles.length()];
            majorDeaths = new int[battles.length()];
            majorCaptures = new int[battles.length()];
            attackerSizes = new String[battles.length()];
            defenderSizes = new String[battles.length()];
            attackerCommanders = new String[battles.length()];
            defenderCommanders = new String[battles.length()];
            summers = new String[battles.length()];
            locations = new String[battles.length()];
            regions = new String[battles.length()];
            notes = new String[battles.length()];

            for(int i=0;i<battles.length();i++){
                JSONObject jo = battles.getJSONObject(i);
                ids[i] = jo.getInt(KEY_ID);
                battleNames[i] = jo.getString(KEY_BATTLE_NAME);
                attackerNames[i] = jo.getString(KEY_ATTACKER_NAME);
                defenderNames[i] = jo.getString(KEY_DEFENDER_NAME);
                attackerStatuses[i] = jo.getString(KEY_ATTACKER_STATUS);
                years[i] = jo.getInt(KEY_YEAR);
                attacker1names[i] = jo.getString(KEY_ATTACKER_1);
                attacker2names[i] = jo.getString(KEY_ATTACKER_2);
                attacker3names[i] = jo.getString(KEY_ATTACKER_3);
                attacker4names[i] = jo.getString(KEY_ATTACKER_4);
                defender1names[i] = jo.getString(KEY_DEFENDER_1);
                defender2names[i] = jo.getString(KEY_DEFENDER_2);
                defender3names[i] = jo.getString(KEY_DEFENDER_3);
                defender4names[i] = jo.getString(KEY_DEFENDER_4);
                battleTypes[i] = jo.getString(KEY_BATTLE_TYPE);
                majorDeaths[i] = jo.getInt(KEY_MAJOR_DEATH);
                majorCaptures[i] = jo.getInt(KEY_MAJOR_CAPTURE);
                attackerSizes[i] = jo.getString(KEY_ATTACKER_SIZE);
                defenderSizes[i] = jo.getString(KEY_DEFENDER_SIZE);
                attackerCommanders[i] = jo.getString(KEY_ATTACKER_COMMANDER);
                defenderCommanders[i] = jo.getString(KEY_DEFENDER_COMMANDER);
                summers[i] = jo.getString(KEY_SUMMER);
                locations[i] = jo.getString(KEY_LOCATION);
                regions[i] = jo.getString(KEY_REGION);
                notes[i] = jo.getString(KEY_NOTE);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
