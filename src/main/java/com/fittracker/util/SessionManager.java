package com.fittracker.util;

public class SessionManager {

    // global variables to be accessible application-wide
    private static int userId = -1; // -1 : not logged in

    public static void setUserId(int id) {
        userId = id;
    }

    public static int getLoggedInUserId() {
        return userId;
    }

    public static boolean isLoggedIn(){
        return userId != -1;
    }
}
