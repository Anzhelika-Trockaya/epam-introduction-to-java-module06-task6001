package by.epam.task6001.bean;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private String login;
    private String name;
    private String email;
    private UserRole role;
    private String password;

    public User() {
        this.login = "";
        this.name = "";
        this.email = "";
        this.role = UserRole.USER;
        this.password = "";
    }

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

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                role == user.role &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, name, email, role, password);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                '}';
    }
}
