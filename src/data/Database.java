package data;

import java.util.ArrayList;
import java.util.List;

import accounts.Account;

public class Database {
    public static List<Account> accounts = new ArrayList<>();
    public static List<String> auditLogs = new ArrayList<>();

    public static void addLog(String log) {
        auditLogs.add(log);
    }
}