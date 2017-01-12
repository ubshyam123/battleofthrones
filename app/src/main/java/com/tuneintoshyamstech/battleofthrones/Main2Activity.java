package com.tuneintoshyamstech.battleofthrones;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.tuneintoshyamstech.battleofthrones.db.BattleDatasource;
import com.tuneintoshyamstech.battleofthrones.model.Battle;
import com.tuneintoshyamstech.battleofthrones.model.King;

public class Main2Activity extends AppCompatActivity {
    BattleDatasource datasource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        datasource = new BattleDatasource(this);
        datasource.open();
        if(type.equalsIgnoreCase("battle")) {
            Battle battle = datasource.getBattleByName("Battle of the Golden Tooth"); // Currenty hardcoded need to get detail from passed intent

            TextView tv1 = (TextView) findViewById(R.id.cardbattlename);
            tv1.setText(battle.getName());
            TextView tv2 = (TextView) findViewById(R.id.cardbattledefender);
            tv2.setText(battle.getDefender_king());
            TextView tv3 = (TextView) findViewById(R.id.cardbattleattacker);
            tv3.setText(battle.getAttacker_king());
            TextView tv4 = (TextView) findViewById(R.id.cardbattleattackeroutcome);
            tv4.setText(battle.getAttacker_outcome());
            TextView tv5 = (TextView) findViewById(R.id.cardbattletype);
            tv5.setText(battle.getBattle_type());
        }else{
            King king = datasource.getKingByName("Robb Stark");
            TextView tv1 = (TextView) findViewById(R.id.cardbattlename);
            tv1.setText(king.getKing_name());
            TextView tv2 = (TextView) findViewById(R.id.cardbattledefender);
            tv2.setText("Battles WON: "+king.getBattles_won());
            TextView tv3 = (TextView) findViewById(R.id.cardbattleattacker);
            String scores = king.getScores();
            String[] sc = scores.split(",");
            tv3.setText("SCORE: " + scores);
            TextView tv4 = (TextView) findViewById(R.id.cardbattleattackeroutcome);
            tv4.setText("Strength: "+king.getStrength());
            TextView tv5 = (TextView) findViewById(R.id.cardbattletype);
            tv5.setText(king.getBattles_won());
        }




    }

}
