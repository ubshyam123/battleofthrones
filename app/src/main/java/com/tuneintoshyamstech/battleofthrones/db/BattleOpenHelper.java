package com.tuneintoshyamstech.battleofthrones.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shyam_2 on 1/7/2017.
 */

public class BattleOpenHelper extends SQLiteOpenHelper {
    public static final String LOGCAT = "BATTLEDB";

    public static final String DATABASE_NAME = "battleofthrones.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_BATTLE = "battle";
    public static final String TABLE_KING = "king";

    public static final String COLUMN_BATTLE_ID = "battle_number";
    public static final String COLUMN_BATTLE_NAME = "name";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_ATTACKER_KING = "attacker_king";
    public static final String COLUMN_DEFENDER_KING = "defender_king";
    public static final String COLUMN_ATTACKER_1 = "attacker_1";
    public static final String COLUMN_ATTACKER_2 = "attacker_2";
    public static final String COLUMN_ATTACKER_3 = "attacker_3";
    public static final String COLUMN_ATTACKER_4 = "attacker_4";

    public static final String COLUMN_DEFENDER_1 = "defender_1";
    public static final String COLUMN_DEFENDER_2 = "defender_2";
    public static final String COLUMN_DEFENDER_3 = "defender_3";
    public static final String COLUMN_DEFENDER_4 = "defender_4";

    public static final String COLUMN_ATTACKER_OUTCOME = "attacker_outcome";
    public static final String COLUMN_BATLLE_TYPE ="battle_type";
    public static final String COLUMN_MAJOR_DEATH ="major_death";
    public static final String COLUMN_MAJOR_CAPTURE = "major_capture";
    public static final String COLUMN_ATTACKER_SIZE = "attacker_size";
    public static final String COLUMN_DEFENDER_SIZE ="defender_size";

    public static final String COLUMN_ATTACKER_COMMANDER = "attacker_commander";
    public static final String COLUMN_DEFENDER_COMMANDER = "defender_commander";
    public static final String COLUMN_SUMMER = "summer";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_REGION = "region";
    public static final String COLUMN_NOTE = "note";

    public static final String COLUMN_KING_ID = "id";
    public static final String COLUMN_KING_NAME = "king_name";
    public static final String COLUMN_BATTLES = "battles";
    public static final String COLUMN_ATTACKED = "attacked";
    public static final String COLUMN_DEFENDED = "defended";
    public static final String COLUMN_ATTACK_SCORE = "attack_score";
    public static final String COLUMN_DEFEND_SCORE = "defend_score";
    public static final String COLUMN_SCORES = "scores";
    public static final String COLUMN_BATTLES_WON = "battles_won";
    public static final String COLUMN_BATTLES_LOST = "battles_lost";
    public static final String COLUMN_STRENGTH = "strength";
    public static final String COLUMN_STRENGTH_TYPE = "strength_type";


    public static final String TABLE_BATTLE_CREATE = "CREATE TABLE "+ TABLE_BATTLE + "("+
            COLUMN_BATTLE_ID + " INTEGER PRIMARY KEY, "+
            COLUMN_BATTLE_NAME + " TEXT, "+
            COLUMN_YEAR + " TEXT, "+
            COLUMN_ATTACKER_KING + " TEXT, "+
            COLUMN_DEFENDER_KING + " TEXT, "+
            COLUMN_ATTACKER_1 + " TEXT, "+
            COLUMN_ATTACKER_2 + " TEXT, "+
            COLUMN_ATTACKER_3 + " TEXT, "+
            COLUMN_ATTACKER_4 + " TEXT, "+

            COLUMN_DEFENDER_1 + " TEXT, "+
            COLUMN_DEFENDER_2 + " TEXT, "+
            COLUMN_DEFENDER_3 + " TEXT, "+
            COLUMN_DEFENDER_4 + " TEXT, "+

            COLUMN_ATTACKER_OUTCOME + " TEXT, "+
            COLUMN_BATLLE_TYPE + " TEXT, "+
            COLUMN_MAJOR_DEATH + " TEXT, "+
            COLUMN_MAJOR_CAPTURE + " TEXT, "+
            COLUMN_ATTACKER_SIZE + " TEXT, "+
            COLUMN_DEFENDER_SIZE + " TEXT, "+

            COLUMN_ATTACKER_COMMANDER + " TEXT, "+
            COLUMN_DEFENDER_COMMANDER + " TEXT, "+
            COLUMN_SUMMER + " TEXT, "+
            COLUMN_LOCATION + " TEXT, "+
            COLUMN_REGION + " TEXT, "+
            COLUMN_NOTE + " TEXT"+
            ")";

    public static final String TABLE_KING_CREATE = "CREATE TABLE "+ TABLE_KING + "("+
            COLUMN_KING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_KING_NAME + " TEXT, "+
            COLUMN_BATTLES + " TEXT, "+
            COLUMN_ATTACKED + " TEXT, "+
            COLUMN_DEFENDED + " TEXT, "+
            COLUMN_ATTACK_SCORE + " TEXT, "+
            COLUMN_DEFEND_SCORE + " TEXT, "+
            COLUMN_SCORES + " TEXT, "+
            COLUMN_BATTLES_WON + " TEXT, "+
            COLUMN_BATTLES_LOST + " TEXT, "+
            COLUMN_STRENGTH + " TEXT, "+
            COLUMN_STRENGTH_TYPE + " TEXT"+
            ")";

    public BattleOpenHelper(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_BATTLE_CREATE);
        db.execSQL(TABLE_KING_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_BATTLE);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_KING);
    }
}
