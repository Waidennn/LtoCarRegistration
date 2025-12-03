package accounts;

public abstract class Account {
    protected String username;
    protected String passwordHash;
    protected String role;

    public Account(String username, String passwordHash, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return role; }

    public abstract void showDashboard();
}
