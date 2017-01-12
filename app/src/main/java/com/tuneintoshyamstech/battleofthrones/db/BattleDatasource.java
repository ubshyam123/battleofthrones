package com.tuneintoshyamstech.battleofthrones.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tuneintoshyamstech.battleofthrones.model.Battle;
import com.tuneintoshyamstech.battleofthrones.model.King;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shyam_2 on 1/7/2017.
 */

public class BattleDatasource {
    private SQLiteOpenHelper dbhelper;
    private SQLiteDatabase database;

    public BattleDatasource(Context context){
        dbhelper = new BattleOpenHelper(context);
    }

    public void open(){
        database = dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }

    public Battle create(Battle battle){
        ContentValues values = new ContentValues();
        values.put(BattleOpenHelper.COLUMN_BATTLE_ID,battle.getBattle_number());
        values.put(BattleOpenHelper.COLUMN_BATTLE_NAME,battle.getName());
        values.put(BattleOpenHelper.COLUMN_YEAR,battle.getYear());
        values.put(BattleOpenHelper.COLUMN_ATTACKER_KING,battle.getAttacker_king());
        values.put(BattleOpenHelper.COLUMN_DEFENDER_KING,battle.getDefender_king());
        values.put(BattleOpenHelper.COLUMN_ATTACKER_1,battle.getAttacker_1());
        values.put(BattleOpenHelper.COLUMN_ATTACKER_2,battle.getAttacker_2());
        values.put(BattleOpenHelper.COLUMN_ATTACKER_3,battle.getAttacker_3());
        values.put(BattleOpenHelper.COLUMN_ATTACKER_4,battle.getAttacker_4());
        values.put(BattleOpenHelper.COLUMN_DEFENDER_1,battle.getDefender_1());
        values.put(BattleOpenHelper.COLUMN_DEFENDER_2,battle.getDefender_2());
        values.put(BattleOpenHelper.COLUMN_DEFENDER_3,battle.getDefender_3());
        values.put(BattleOpenHelper.COLUMN_DEFENDER_4,battle.getDefender_4());
        values.put(BattleOpenHelper.COLUMN_ATTACKER_OUTCOME,battle.getAttacker_outcome());
        values.put(BattleOpenHelper.COLUMN_BATLLE_TYPE,battle.getBattle_type());
        values.put(BattleOpenHelper.COLUMN_MAJOR_DEATH,battle.getMajor_death());
        values.put(BattleOpenHelper.COLUMN_MAJOR_CAPTURE,battle.getMajor_capture());
        values.put(BattleOpenHelper.COLUMN_ATTACKER_SIZE,battle.getAttacker_size());
        values.put(BattleOpenHelper.COLUMN_DEFENDER_SIZE,battle.getDefender_size());
        values.put(BattleOpenHelper.COLUMN_ATTACKER_COMMANDER,battle.getAttacker_commander());
        values.put(BattleOpenHelper.COLUMN_DEFENDER_COMMANDER,battle.getDefender_commander());
        values.put(BattleOpenHelper.COLUMN_SUMMER,battle.getSummer());
        values.put(BattleOpenHelper.COLUMN_LOCATION,battle.getLocation());
        values.put(BattleOpenHelper.COLUMN_REGION,battle.getRegion());
        values.put(BattleOpenHelper.COLUMN_NOTE,battle.getNote());

        long insertId = database.insert(BattleOpenHelper.TABLE_BATTLE,null,values);
        return battle;
    }


    public King create(King king){
        ContentValues values = new ContentValues();
        values.put(BattleOpenHelper.COLUMN_KING_NAME,king.getKing_name());
        values.put(BattleOpenHelper.COLUMN_BATTLES,king.getBattles());
        values.put(BattleOpenHelper.COLUMN_ATTACKED,king.getBattles_attacked());
        values.put(BattleOpenHelper.COLUMN_DEFENDED,king.getBattles_defended());
        values.put(BattleOpenHelper.COLUMN_ATTACK_SCORE,king.getAttack_score());
        values.put(BattleOpenHelper.COLUMN_DEFEND_SCORE,king.getDefend_score());
        values.put(BattleOpenHelper.COLUMN_SCORES,king.getScores());
        values.put(BattleOpenHelper.COLUMN_BATTLES_WON,king.getBattles_won());
        values.put(BattleOpenHelper.COLUMN_BATTLES_LOST,king.getBattles_lost());
        values.put(BattleOpenHelper.COLUMN_STRENGTH,king.getStrength());
        values.put(BattleOpenHelper.COLUMN_STRENGTH_TYPE,king.getString_type());

        long insertId = database.insert(BattleOpenHelper.TABLE_KING,null,values);
        king.setId(insertId);
        return king;
    }



    public boolean checkLogin(String uname, String pwd){
        boolean loggedIn = false;
        String query = "SELECT * FROM "+BattleOpenHelper.TABLE_BATTLE +" where email==\""+uname + "\" and password==\""+pwd+"\"";
        Cursor cursor = database.rawQuery(query,null);

        if(cursor.getCount()>0){
            loggedIn = true;
        }
        cursor.close();
        return loggedIn;
    }

    public List<Battle> getAllBattles() {
        List<Battle> allBattles = new ArrayList<Battle>();
        String query = "SELECT * FROM " + BattleOpenHelper.TABLE_BATTLE;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Battle battle = new Battle();
                battle.setBattle_number(cursor.getInt(0));
                battle.setName(cursor.getString(1));
                battle.setYear(cursor.getInt(2));
                battle.setAttacker_king(cursor.getString(3));
                battle.setDefender_king(cursor.getString(4));
                battle.setAttacker_1(cursor.getString(5));
                battle.setAttacker_2(cursor.getString(6));
                battle.setAttacker_3(cursor.getString(7));
                battle.setAttacker_4(cursor.getString(8));
                battle.setDefender_1(cursor.getString(9));
                battle.setDefender_2(cursor.getString(10));
                battle.setDefender_3(cursor.getString(11));
                battle.setDefender_4(cursor.getString(12));
                battle.setAttacker_outcome(cursor.getString(13));
                battle.setBattle_type(cursor.getString(14));
                battle.setMajor_death(cursor.getInt(15));
                battle.setMajor_capture(cursor.getInt(16));
                battle.setAttacker_size(cursor.getString(17));
                battle.setDefender_size(cursor.getString(18));
                battle.setAttacker_commander(cursor.getString(19));
                battle.setDefender_commander(cursor.getString(20));
                battle.setSummer(cursor.getString(21));
                battle.setLocation(cursor.getString(22));
                battle.setRegion(cursor.getString(23));
                battle.setNote(cursor.getString(24));
                allBattles.add(battle);
            } while (cursor.moveToNext());
        }
        return allBattles;
    }

    public Battle getBattleByName(String battleName) {
        String query = "SELECT * FROM " + BattleOpenHelper.TABLE_BATTLE+" where "+BattleOpenHelper.COLUMN_BATTLE_NAME+"=\""+battleName+"\"";
        Cursor cursor = database.rawQuery(query, null);
        Battle battle = new Battle();
        if (cursor.moveToFirst()) {
            do {
                battle.setBattle_number(cursor.getInt(0));
                battle.setName(cursor.getString(1));
                battle.setYear(cursor.getInt(2));
                battle.setAttacker_king(cursor.getString(3));
                battle.setDefender_king(cursor.getString(4));
                battle.setAttacker_1(cursor.getString(5));
                battle.setAttacker_2(cursor.getString(6));
                battle.setAttacker_3(cursor.getString(7));
                battle.setAttacker_4(cursor.getString(8));
                battle.setDefender_1(cursor.getString(9));
                battle.setDefender_2(cursor.getString(10));
                battle.setDefender_3(cursor.getString(11));
                battle.setDefender_4(cursor.getString(12));
                battle.setAttacker_outcome(cursor.getString(13));
                battle.setBattle_type(cursor.getString(14));
                battle.setMajor_death(cursor.getInt(15));
                battle.setMajor_capture(cursor.getInt(16));
                battle.setAttacker_size(cursor.getString(17));
                battle.setDefender_size(cursor.getString(18));
                battle.setAttacker_commander(cursor.getString(19));
                battle.setDefender_commander(cursor.getString(20));
                battle.setSummer(cursor.getString(21));
                battle.setLocation(cursor.getString(22));
                battle.setRegion(cursor.getString(23));
                battle.setNote(cursor.getString(24));
            } while (cursor.moveToNext());
        }
        return battle;
    }

    public List<King> getAllKings() {
            List<King> allKings = new ArrayList<King>();
            String query = "SELECT * FROM " + BattleOpenHelper.TABLE_KING;
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    King king = new King();
                    king.setId(cursor.getInt(0));
                    king.setKing_name(cursor.getString(1));
                    king.setBattles(cursor.getString(2));
                    king.setBattles_attacked(cursor.getString(3));
                    king.setBattles_defended(cursor.getString(4));
                    king.setAttack_score(cursor.getInt(5));
                    king.setDefend_score(cursor.getInt(6));
                    king.setScores(cursor.getString(7));
                    king.setBattles_won(cursor.getString(8));
                    king.setBattles_lost(cursor.getString(9));
                    king.setStrength(cursor.getString(10));
                    king.setString_type(cursor.getString(11));
                    if(!king.getKing_name().equalsIgnoreCase(""))
                        allKings.add(king);
                } while (cursor.moveToNext());
            }
            return allKings;
    }

    public King getKingByName(String kingName) {
        List<King> allKings = new ArrayList<King>();
        String query = "SELECT * FROM " + BattleOpenHelper.TABLE_KING+" where "+BattleOpenHelper.COLUMN_KING_NAME+"=\""+kingName+"\"";
        Cursor cursor = database.rawQuery(query, null);
        King king = new King();
        if (cursor.moveToFirst()) {
            do {

                king.setId(cursor.getInt(0));
                king.setKing_name(cursor.getString(1));
                king.setBattles(cursor.getString(2));
                king.setBattles_attacked(cursor.getString(3));
                king.setBattles_defended(cursor.getString(4));
                king.setAttack_score(cursor.getInt(5));
                king.setDefend_score(cursor.getInt(6));
                king.setScores(cursor.getString(7));
                king.setBattles_won(cursor.getString(8));
                king.setBattles_lost(cursor.getString(9));
                king.setStrength(cursor.getString(10));
                king.setString_type(cursor.getString(11));
            } while (cursor.moveToNext());
        }
        return king;
    }


    public List<String> getAllUniqueKingNames(){
        List<String> kingNames = new ArrayList<String>();
        List<Battle> allBattles = new ArrayList<Battle>();
        String query1 =  "SELECT DISTINCT "+BattleOpenHelper.COLUMN_ATTACKER_KING+" FROM "+BattleOpenHelper.TABLE_BATTLE;
        String query2 =  "SELECT DISTINCT "+BattleOpenHelper.COLUMN_DEFENDER_KING+" FROM "+BattleOpenHelper.TABLE_BATTLE;
        Cursor cursor = database.rawQuery(query1,null);
        if(cursor.moveToFirst()){
            do{
                kingNames.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        Cursor cursor2 = database.rawQuery(query2,null);
        if(cursor2.moveToFirst()){
            do{
                kingNames.add(cursor2.getString(0));
            }while(cursor2.moveToNext());
        }
        Object[] st = kingNames.toArray();
        for (Object s : st) {
            if (kingNames.indexOf(s) != kingNames.lastIndexOf(s)) {
                kingNames.remove(kingNames.lastIndexOf(s));
            }
        }
        return kingNames;
    }

    public String getBattlesForKingName(String kingName){
        String battleList = "";
        List<Battle> allBattles = new ArrayList<Battle>();
        String query = "SELECT "+BattleOpenHelper.COLUMN_BATTLE_ID+" FROM "
                +BattleOpenHelper.TABLE_BATTLE +" WHERE "+BattleOpenHelper.COLUMN_ATTACKER_KING+" = \""+kingName+
                "\" OR "+BattleOpenHelper.COLUMN_DEFENDER_KING +"= \""+kingName+"\"";
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                battleList += cursor.getString(0) +",";
            }while(cursor.moveToNext());
            battleList = battleList.substring(0,battleList.length()-1);
        }
        return battleList;
    }

    public String getBattlesAttackedForKingName(String kingName){
        String battleList = "";
        List<Battle> allBattles = new ArrayList<Battle>();
        String query = "SELECT "+BattleOpenHelper.COLUMN_BATTLE_ID+" FROM "
                +BattleOpenHelper.TABLE_BATTLE +" WHERE "+BattleOpenHelper.COLUMN_ATTACKER_KING +"= \""+kingName+"\"";
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                battleList += cursor.getString(0) +",";
            }while(cursor.moveToNext());
            battleList = battleList.substring(0,battleList.length()-1);
        }
        return battleList;
    }
    public String getBattlesDefendedForKingName(String kingName){
        String battleList = "";
        List<Battle> allBattles = new ArrayList<Battle>();
        String query = "SELECT "+BattleOpenHelper.COLUMN_BATTLE_ID+" FROM "
                +BattleOpenHelper.TABLE_BATTLE +" WHERE "+BattleOpenHelper.COLUMN_DEFENDER_KING +"= \""+kingName+"\"";
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                battleList += cursor.getString(0) +",";
            }while(cursor.moveToNext());
            battleList = battleList.substring(0,battleList.length()-1);
        }
        return battleList;
    }

}
