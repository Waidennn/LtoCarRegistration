package accounts;

import services.AdminService;

public class AdminAccount extends Account {
    private String adminCode;
    private AdminService adminService = new AdminService(this);

    public AdminAccount(String username, String passwordHash, String adminCode) {
        super(username, passwordHash, "ADMIN");
        this.adminCode = adminCode;
    }

    @Override
    public void showDashboard() {
        adminService.adminMenu();
    }
}
