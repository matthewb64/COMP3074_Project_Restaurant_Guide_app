package com.restaurantguide;

public class Utility {

    public static boolean isEmpty(String s) {
        if (s.equals(null)) {
            return true;
        }
        return false;
    }

    public static boolean phoneInvalid(String s){
        if (s.matches("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")){
            return false;
        }
        return true;
    }

    public static double locValid(double v){
        try {
            double num = v;
            return v;
        }
        catch (NullPointerException e){
            v = 0;
            e.getStackTrace();
        }
        return v;
    }
}
