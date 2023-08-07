package co.ke.emtechhouse.es.Auth.Security.jwt;


import co.ke.emtechhouse.es.Auth.utils.Session.Activesession;

public class CurrentUserContext {
    private static ThreadLocal<Activesession> currentActiveUser = new InheritableThreadLocal<>();

    public static Activesession getCurrentActiveUser() {
        return currentActiveUser.get();
    }

    public static void setCurrentActiveUser(Activesession entity) {
        currentActiveUser.set(entity);
    }

    public static void clear() {
        currentActiveUser.set(null);
    }
}