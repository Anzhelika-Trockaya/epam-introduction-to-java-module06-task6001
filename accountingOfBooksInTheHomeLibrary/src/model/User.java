package model;

public class User {
    private String login;
    private String name;
    private String email;
    private UserRole role;
    private String password;

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

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public UserRole getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
}
