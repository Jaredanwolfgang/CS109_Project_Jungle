package model.User;


import model.Enum.PlayerType;

public class User {
    private String username;
    private String password;
    private double score;
    private PlayerType playerType;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 1000;
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

    public String getPassword() {
        return password;
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
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(username).append(',').append(password).append(",").append(score).append(",");
        if(playerType == PlayerType.HUMAN){
            sb.append("HUMAN");
        }else if(playerType == PlayerType.AI){
            sb.append("AI");
        }
        return sb.toString();
    }
}
