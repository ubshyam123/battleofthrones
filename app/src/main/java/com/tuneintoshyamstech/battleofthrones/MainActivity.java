package com.tuneintoshyamstech.battleofthrones;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tuneintoshyamstech.battleofthrones.db.BattleDatasource;
import com.tuneintoshyamstech.battleofthrones.model.Battle;
import com.tuneintoshyamstech.battleofthrones.model.King;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    public static final String JSON_URL = "http://starlord.hackerearth.com/gotjson";
    private Button buttonGet;
    private ListView listView;
    BattleDatasource datasource;
    private MenuItem searchMenuItem;
    private SearchView searchView;
    BattleList battleList;
    KingList kingList;
    List<Battle> loadedBattles;
    List<King> loadedKings;
    List<RatingRequirement> battleRatings = new ArrayList<RatingRequirement>();
    HashMap<String,Double> kingScores = new HashMap<String, Double>();
    String btl = "battle";

    class RatingRequirement{
        String attackerName;
        double attackerScore;
        int attackerOutcome;

        String defenderName;
        double defenderScore;
        int defenderOutcome;

        public String getAttackerName() {
            return attackerName;
        }

        public void setAttackerName(String attackerName) {
            this.attackerName = attackerName;
        }

        public double getAttackerScore() {
            return attackerScore;
        }

        public void setAttackerScore(double attackerScore) {
            this.attackerScore = attackerScore;
        }

        public int getAttackerOutcome() {
            return attackerOutcome;
        }

        public void setAttackerOutcome(int attackerOutcome) {
            this.attackerOutcome = attackerOutcome;
        }

        public String getDefenderName() {
            return defenderName;
        }

        public void setDefenderName(String defenderName) {
            this.defenderName = defenderName;
        }

        public double getDefenderScore() {
            return defenderScore;
        }

        public void setDefenderScore(double defenderScore) {
            this.defenderScore = defenderScore;
        }

        public int getDefenderOutcome() {
            return defenderOutcome;
        }

        public void setDefenderOutcome(int defenderOutcome) {
            this.defenderOutcome = defenderOutcome;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final Menu menu = navigationView.getMenu();
        MenuItem firstitem = menu.getItem(0);
        firstitem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                populateBattleList(loadedBattles);
                return false;
            }

        });
        MenuItem seconditem = menu.getItem(1);
        seconditem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                populateKingList(loadedKings);
                return false;
            }

        });

        //App logic starts here
        datasource = new BattleDatasource(this);
        datasource.open();
        List<Battle> loadedBattleFromDb = datasource.getAllBattles();
        //if(loadedBattleFromDb.size()<1){
            sendRequest();
        //}else{
           // loadedBattles = loadedBattleFromDb;
          //  populateBattleList(loadedBattles);
       // }
        //buttonGet = (Button) findViewById(R.id.buttonGet);
        listView = (ListView) findViewById(R.id.battle_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("type",btl);
                startActivity(intent);
            }
        });

    }

    private void sendRequest() {

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ParseJSON parsedJson = parseBattlesFromJson(response);
                        List<Battle> battles = storeBattlesInDB(parsedJson);
                        populateBattleList(battles);
                        //showJSON(parsedJson);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private List<Battle> storeBattlesInDB(ParseJSON parsedJson) {
        loadedBattles = new ArrayList<Battle>();

        int totalBattles = parsedJson.battleNames.length;
        //Calculating and saving the Battle details to DB
        for(int i=0;i<totalBattles;i++){
            Battle battle = new Battle();
            //battle.setBattle_number(parsedJson.);
            battle.setBattle_number(parsedJson.ids[i]);
            battle.setName(parsedJson.battleNames[i]);
            battle.setYear(parsedJson.years[i]);
            battle.setAttacker_king(parsedJson.attackerNames[i]);
            battle.setDefender_king(parsedJson.defenderNames[i]);
            battle.setAttacker_1(parsedJson.attacker1names[i]);
            battle.setAttacker_2(parsedJson.attacker2names[i]);
            battle.setAttacker_3(parsedJson.attacker3names[i]);
            battle.setAttacker_4(parsedJson.attacker4names[i]);
            battle.setDefender_1(parsedJson.defender1names[i]);
            battle.setDefender_2(parsedJson.defender2names[i]);
            battle.setDefender_3(parsedJson.defender3names[i]);
            battle.setDefender_4(parsedJson.defender4names[i]);
            battle.setAttacker_outcome(parsedJson.attackerStatuses[i]);
            battle.setBattle_type(parsedJson.battleTypes[i]);
            battle.setMajor_death(parsedJson.majorDeaths[i]);
            battle.setMajor_capture(parsedJson.majorCaptures[i]);
            battle.setAttacker_size(parsedJson.attackerSizes[i]);
            battle.setDefender_size(parsedJson.defenderSizes[i]);
            battle.setAttacker_commander(parsedJson.attackerCommanders[i]);
            battle.setDefender_commander(parsedJson.defenderCommanders[i]);
            battle.setAttacker_score(computeBattlescore(parsedJson.attackerSizes[i],parsedJson.defenderSizes[i],parsedJson.attackerStatuses[i],true));
            battle.setDefender_score(computeBattlescore(parsedJson.attackerSizes[i],parsedJson.defenderSizes[i],parsedJson.attackerStatuses[i],false));
            //TODO add other 4
            battle = datasource.create(battle);
            loadedBattles.add(battle);
            //Setting values required for ELO rating calculation
            if(!kingScores.containsKey(parsedJson.attackerNames[i])){
                kingScores.put(parsedJson.attackerNames[i],400d);
            }

            double attackerScore = kingScores.get(parsedJson.attackerNames[i]);

            if(!kingScores.containsKey(parsedJson.defenderNames[i])){
                kingScores.put(parsedJson.defenderNames[i],400d);
            }

            double defenderScore = kingScores.get(parsedJson.defenderNames[i]);
            int attackerWin = 0;
            int defenderWin = 0;
            if(parsedJson.attackerStatuses[i] .equalsIgnoreCase("win")){
                attackerWin = 1;
                defenderWin = 0;
            }else if(parsedJson.attackerStatuses[i] .equalsIgnoreCase("loss")){
                attackerWin = 0;
                defenderWin = 1;
            }

            RatingRequirement ratingReq= new RatingRequirement();
            ratingReq.setAttackerName(parsedJson.attackerNames[i]);
            ratingReq.setAttackerScore(attackerScore);
            ratingReq.setAttackerOutcome(attackerWin);
            ratingReq.setDefenderName(parsedJson.defenderNames[i]);
            ratingReq.setDefenderScore(defenderScore);
            ratingReq.setDefenderOutcome(defenderWin);

            ratingReq = computeELOscore(ratingReq);
            kingScores.put(parsedJson.attackerNames[i],ratingReq.getAttackerScore());
            kingScores.put(parsedJson.defenderNames[i],ratingReq.getDefenderScore());
            battleRatings.add(ratingReq);
        }
        Toast.makeText(MainActivity.this,"Latest Battles downloaded Successfully",Toast.LENGTH_SHORT).show();
        storeKingDetailsInDB(loadedBattles);
        return loadedBattles;


    }

    private void storeKingDetailsInDB(List<Battle> loadedBattles) {
        List<String> kingNames = datasource.getAllUniqueKingNames();
        loadedKings = new ArrayList<King>();
        for(int i=0;i<kingNames.size();i++){
            King king = new King();
            king.setKing_name(kingNames.get(i));
            king.setBattles(datasource.getBattlesForKingName(kingNames.get(i)));
            king.setBattles_attacked(datasource.getBattlesAttackedForKingName(kingNames.get(i)));
            king.setBattles_defended(datasource.getBattlesDefendedForKingName(kingNames.get(i)));
            king.setScores(getScoreForKing(kingNames.get(i),king.getBattles()));
            king.setBattles_won(getWonBattlesForKing(king.getBattles()));
            king.setBattles_lost(getLostBattlesForKing(king.getBattles()));
            king.setAttack_score(computeAttackscoreForKing(kingNames.get(i),king.getBattles_attacked()));
            king.setDefend_score(computeDefendscoreForKing(kingNames.get(i),king.getBattles_defended()));
            king.setStrength(king.getAttack_score()>king.getDefend_score()?"Attack":"Defend");
            king.setString_type(getStrengthType(getWonBattlesForKing(king.getBattles())));
            king = datasource.create(king);
            if(!king.getKing_name().isEmpty())
            loadedKings.add(king);
        }

        Log.i("Hi","Hiiii");
    }

    private String getStrengthType(String wonBattlesForKing) {
        String[] temp = wonBattlesForKing.split(",");
        HashMap<String,Integer> hm = new HashMap<String,Integer>();

        double totalAttackScore=0;
        int count = 0;
        for(int j=0;j<temp.length-1;j++){
            Battle battle = (Battle) loadedBattles.get(Integer.parseInt(temp[j]));
            hm.put(battle.getBattle_type(),hm.get(battle.getBattle_type())!=null?((int)hm.get(battle.getBattle_type())+1):1);
        }
        Map.Entry<String,Integer> maxEntry = null;

        for(Map.Entry<String,Integer> entry : hm.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        return maxEntry==null?"Ambush":maxEntry.getKey();
    }

    private double computeAttackscoreForKing(String kingName, String battles) {
        String[] temp = battles.split(",");
        double totalAttackScore=0;
        for(int j=0;j<temp.length-1;j++){
            Battle battle = (Battle) loadedBattles.get(Integer.parseInt(temp[j]));
            int attackSize = Integer.parseInt(battle.getAttacker_size().equalsIgnoreCase("")?"1":battle.getAttacker_size());
            int defendSize = Integer.parseInt(battle.getDefender_size().equalsIgnoreCase("")?"1":battle.getDefender_size());
            double tempAttack = attackSize/(defendSize+attackSize);
            double tempDefend = 1- tempAttack;
            if(battle.getAttacker_outcome().equalsIgnoreCase("win")) {
                totalAttackScore += tempDefend;
            }else{
                totalAttackScore+= (-1 * tempAttack);
            }
        }
        return totalAttackScore/temp.length;
    }

    private double computeDefendscoreForKing(String kingName, String battles) {
        String[] temp = battles.split(",");
        double totalDefendScore=0;
        for(int j=0;j<temp.length-1;j++){
            Battle battle = (Battle) loadedBattles.get(Integer.parseInt(temp[j]));
            int attackSize = Integer.parseInt(battle.getAttacker_size().equalsIgnoreCase("")?"1":battle.getAttacker_size());
            int defendSize = Integer.parseInt(battle.getDefender_size().equalsIgnoreCase("")?"1":battle.getDefender_size());
            double tempAttack = attackSize/(defendSize+attackSize);
            double tempDefend = 1- tempAttack;
            if(battle.getAttacker_outcome().equalsIgnoreCase("win")) {
                totalDefendScore += tempAttack;
            }else{
                totalDefendScore+= (-1 * tempDefend);
            }
        }
        return totalDefendScore/temp.length;
    }

    private String getScoreForKing(String kingName, String battles) {
        String finalScores = "";
        String[] temp = battles.split(",");
        for(int j=0;j<temp.length-1;j++){
            RatingRequirement rr = (RatingRequirement) battleRatings.get(Integer.parseInt(temp[j]));
            if(rr.getAttackerName().equalsIgnoreCase(kingName)) {
                finalScores += rr.getAttackerScore()+",";
            }else if(rr.getDefenderName().equalsIgnoreCase(kingName)){
                finalScores += rr.getDefenderName()+",";
            }
        }
        finalScores = finalScores.length()==0?"":finalScores.substring(0,finalScores.length()-1);
        return finalScores;
    }

    private String getWonBattlesForKing(String battles) {
        String finalScores = "";
        String[] temp = battles.split(",");
        for(int j=0;j<temp.length-1;j++){
            RatingRequirement rr = (RatingRequirement) battleRatings.get(Integer.parseInt(temp[j]));
            if(rr.getAttackerOutcome() == 1 || rr.getDefenderOutcome() == 1) {
                finalScores += temp[j]+",";
            }
        }
        finalScores = finalScores.length()==0?"":finalScores.substring(0,finalScores.length()-1);
        return finalScores;
    }

    private String getLostBattlesForKing(String battles) {
        String finalScores = "";
        String[] temp = battles.split(",");
        for(int j=0;j<temp.length-1;j++){
            RatingRequirement rr = (RatingRequirement) battleRatings.get(Integer.parseInt(temp[j]));
            if(rr.getAttackerOutcome() == 0 || rr.getDefenderOutcome() == 0) {
                finalScores += temp[j]+",";
            }
        }
        finalScores = finalScores.length()==0?"":finalScores.substring(0,finalScores.length()-1);
        return finalScores;
    }

    public float computeBattlescore(String attackerSize, String defenderSize,String attackerStatus, boolean isAttacker) {

        return 0;
    }

    /**
     * Method to compute the ELO score. In case attacker/defender king is empty default score of 400 is used for computation
     * @param ratingRequirement
     * @return
     */
    public RatingRequirement computeELOscore(RatingRequirement ratingRequirement) {

        double currentAttackerRating = ratingRequirement.getAttackerScore();
        double currentDefenderRating = ratingRequirement.getDefenderScore();

        int k= 32;
        double updatedAttackerRating = Math.pow(10,(currentAttackerRating/400));
        double updatedDefenderRating = Math.pow(10,(currentDefenderRating/400));
        double e1 = updatedAttackerRating/(updatedAttackerRating+updatedDefenderRating);
        double e2 = updatedDefenderRating/(updatedAttackerRating+updatedDefenderRating);
        int s1 = ratingRequirement.getAttackerOutcome();
        int s2 = ratingRequirement.getDefenderOutcome();
        double finalAttackerRating = currentAttackerRating + (k*(s1-e1));
        double finalDefenderRating = currentDefenderRating + (k*(s2-e2));
        ratingRequirement.setAttackerScore(finalAttackerRating);
        ratingRequirement.setDefenderScore(finalDefenderRating);
        return ratingRequirement;
    }

    private ParseJSON parseBattlesFromJson(String response) {
        ParseJSON pj = new ParseJSON(response);
        pj.parseJSON();
        return pj;
    }

    private void showJSON(ParseJSON parsedJson){

        /*battleList = new BattleList(this, parsedJson.battleNames,
                parsedJson.attackerNames,
                parsedJson.defenderNames,
                parsedJson.attackerStatuses);
        listView.setAdapter(battleList);*/
    }

    private void populateBattleList(List<Battle> battles){
        String[] battleNames = new String[battles.size()];
        String[] attackerNames = new String[battles.size()];
        String[] defenderNames= new String[battles.size()];
        String[] attackerStatuses= new String[battles.size()];
        int battleListSize = battles.size();
        for(int i=0;i<battleListSize;i++){
            Battle battle = (Battle) battles.get(i);
            battleNames[i] = battle.getName();
            attackerNames[i] = battle.getAttacker_king();
            defenderNames[i] = battle.getDefender_king();
            attackerStatuses[i] = battle.getAttacker_outcome();
        }
        battleList = new BattleList(this, battleNames,
                attackerNames,
                defenderNames,
                attackerStatuses,
                battles);
        listView.setAdapter(battleList);
    }

    public void populateKingList(List<King> kings){
        kingList = new KingList(this, kings);
        btl = "king";
        listView.setAdapter(kingList);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            // Handle the camera action
            Toast.makeText(this,"Nav Manage clicked",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this,"Nav Gallery clicked",Toast.LENGTH_SHORT).show();
        }  else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        battleList.getFilter().filter(newText);

        return true;
    }
}
