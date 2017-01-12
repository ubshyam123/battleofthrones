package com.tuneintoshyamstech.battleofthrones.model;

/**
 * Created by Shyam_2 on 1/7/2017.
 */

public class King {

    long id;
    String king_name;
    String battles;
    String battles_attacked;
    String battles_defended;
    double attack_score;
    double defend_score;
    String scores;
    String battles_won;
    String battles_lost;
    String strength;
    String string_type;

    public String getBattles_defended() {
        return battles_defended;
    }

    public void setBattles_defended(String battles_defended) {
        this.battles_defended = battles_defended;
    }

    public String getKing_name() {
        return king_name;
    }

    public void setKing_name(String king_name) {
        this.king_name = king_name;
    }

    public String getBattles() {
        return battles;
    }

    public void setBattles(String battles) {
        this.battles = battles;
    }

    public String getBattles_attacked() {
        return battles_attacked;
    }

    public void setBattles_attacked(String battles_attacked) {
        this.battles_attacked = battles_attacked;
    }

    public double getAttack_score() {
        return attack_score;
    }

    public void setAttack_score(double attack_score) {
        this.attack_score = attack_score;
    }

    public double getDefend_score() {
        return defend_score;
    }

    public void setDefend_score(double defend_score) {
        this.defend_score = defend_score;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getBattles_won() {
        return battles_won;
    }

    public void setBattles_won(String battles_won) {
        this.battles_won = battles_won;
    }

    public String getBattles_lost() {
        return battles_lost;
    }

    public void setBattles_lost(String battles_lost) {
        this.battles_lost = battles_lost;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getString_type() {
        return string_type;
    }

    public void setString_type(String string_type) {
        this.string_type = string_type;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
