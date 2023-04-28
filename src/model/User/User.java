package model.User;

import model.Enum.PlayerColor;

public class User extends Player implements Comparable{
    private static String username;
    private static String password;
    private static double score;
    private final PlayerColor usercolor = PlayerColor.RED;
    //FIXME:How to judge whether it is the user or the opponent by using merely color?

    public User() {
    }

    public User(String username, String password, double scores) {
        this.username = username;
        this.password = password;
        this.score = scores;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static double getScore() {
        return score;
    }

    public static void setScore(double score) {
        User.score = score;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
