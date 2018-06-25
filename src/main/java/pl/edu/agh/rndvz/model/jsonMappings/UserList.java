package pl.edu.agh.rndvz.model.jsonMappings;

import pl.edu.agh.rndvz.model.User;

import java.util.List;

public class UserList {
    private List<User> users;

    public UserList() {
    }

    public UserList(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
