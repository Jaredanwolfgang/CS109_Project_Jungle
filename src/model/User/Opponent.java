package model.User;

import model.Enum.PlayerColor;

public class Opponent extends Player implements Comparable{
    private static String username;
    private static String password;
    private static double score;
    private final PlayerColor opponentColor = PlayerColor.BLUE;

    public Opponent() {
    }

    public Opponent(String username, String password, double scores) {
        this.username = username;
        this.password = password;
        this.score = scores;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Opponent.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Opponent.password = password;
    }

    public static double getScore() {
        return score;
    }

    public static void setScore(double score) {
        Opponent.score = score;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
