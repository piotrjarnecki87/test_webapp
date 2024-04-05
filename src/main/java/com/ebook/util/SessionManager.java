package com.ebook.util;

import com.ebook.model.User;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static Map<String, User> sessions = new HashMap<>(); // mapa przechowująca aktywne sesje

    // Metoda do dodawania sesji po zalogowaniu
    public static void addSession(String sessionId, User user) {
        sessions.put(sessionId, user);
    }

    // Metoda sprawdzająca czy sesja istnieje
    public static boolean sessionExists(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    // Metoda do usuwania sesji po wylogowaniu
    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    // Metoda do pobierania użytkownika na podstawie sesji
    public static User getUserBySessionId(String sessionId) {
        return sessions.get(sessionId);
    }
}
