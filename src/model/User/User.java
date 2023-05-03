package model.User;


import model.Enum.PlayerType;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private String username;
    private String password;
    private double score;
    private int wins;
    private int losses;
    private PlayerType playerType;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 1000;
        this.wins = 0;
        this.losses = 0;
        this.playerType = PlayerType.HUMAN;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean validatePassword(String password) {
        return Objects.hash(password) == Objects.hash(this.password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public double getWinRate(){
        if(wins + losses == 0){
            return 0.5;
        }
        return (double)wins / (wins + losses);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(username).append(',').append(password).append(",").append(wins).append(",").append(losses).append(",").append(score).append(",");
        if(playerType == PlayerType.HUMAN){
            sb.append("HUMAN");
        }else if(playerType == PlayerType.AI){
            sb.append("AI");
        }
        return sb.toString();
    }
}
