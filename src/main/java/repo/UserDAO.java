package repo;

import model.Role;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final List<User> store = new ArrayList<>();

    public ArrayList<User> getAllUsers(){
        ArrayList<User> users = new ArrayList<>();

        for (User u : store) {
                users.add(u);
        }

        return users;
    }

    public User getUserByUsername(String userName) throws Exception {

        for (User u : store) {
            if(u.getName().equals(userName)){
                return u;
            }
        }

        throw new Exception("No such user");
    }

    public boolean add(final User user) {
        for (User u : store) {
            if (u.getName().equals(user.getName()) && u.getPassword().equals(user.getPassword())) {
                return false;
            }
        }

        return store.add(user);
    }

    public Role getRoleByLoginPassword(final String login, final String password) {
        Role result = Role.GUEST;

        for (User user : store) {
            if (user.getName().equals(login) && user.getPassword().equals(password)) {
                result = user.getRole();
            }
        }

        return result;
    }

    public boolean isExist(final String login, final String password) {
        boolean result = false;

        for (User user : store) {
            if (user.getName().equals(login) && user.getPassword().equals(password)) {
                result = true;
                break;
            }
        }

        return result;
    }
}
