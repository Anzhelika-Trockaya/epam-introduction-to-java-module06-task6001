package model;

public class User {
    private final String login;
    private final String name;
    private final String email;
    private final UserRole role;
    private final String password;

    public User(String login, String name, String email, UserRole role, String password) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
}
