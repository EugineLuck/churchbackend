package co.ke.emtechhouse.es.utils.Security.jwt;

public class UserRequestContext {
    private static final ThreadLocal<String> currentUser = new InheritableThreadLocal<>();

    public static String getCurrentUser() {
        return currentUser.get();
    }

    public static void setCurrentUser(String memberNumber) {
        currentUser.set(memberNumber);
    }

    public static void clear() {
        currentUser.set(null);
    }
}