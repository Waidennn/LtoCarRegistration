package accounts;

import java.util.ArrayList;
import java.util.List;

import models.Car;
import services.UserService;

public class UserAccount extends Account {
    private List<Car> cars = new ArrayList<>();
    private UserService userService = new UserService(this);

    public UserAccount(String username, String passwordHash) {
        super(username, passwordHash, "USER");
    }

    public List<Car> getCars() { return cars; }

    @Override
    public void showDashboard() {
        userService.userMenu();
    }
}
