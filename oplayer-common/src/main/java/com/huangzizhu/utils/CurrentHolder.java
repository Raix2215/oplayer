package com.huangzizhu.utils;

public class CurrentHolder {

    private static final ThreadLocal<Integer> CURRENT_LOCAL_INT = new ThreadLocal<>();
    private static final ThreadLocal<String> CURRENT_LOCAL_STRING = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> CURRENT_LOCAL_BOOL = new ThreadLocal<>();

    public static void setCurrentId(Integer id) {
        CURRENT_LOCAL_INT.set(id);
    }
    public static void setCurrentName(String username) {
        CURRENT_LOCAL_STRING.set(username);
    }
    public static void setCurrentIsAdmin(Boolean isAdmin) {
        CURRENT_LOCAL_BOOL.set(isAdmin);
    }

    public static Integer getCurrentId() {
        return CURRENT_LOCAL_INT.get();
    }
    public static String getCurrentName() {return CURRENT_LOCAL_STRING.get();}
    public static Boolean getCurrentIsAdmin() {return CURRENT_LOCAL_BOOL.get();}

    public static void removeInt() {
        CURRENT_LOCAL_INT.remove();
    }
    public static void removeString() {CURRENT_LOCAL_STRING.remove();}
    public static void removeBool() {CURRENT_LOCAL_BOOL.remove();}
    public static void removeAll() {
        CURRENT_LOCAL_INT.remove();
        CURRENT_LOCAL_STRING.remove();
        CURRENT_LOCAL_BOOL.remove();
    }
}
