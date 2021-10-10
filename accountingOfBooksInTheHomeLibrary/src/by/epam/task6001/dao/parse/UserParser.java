package by.epam.task6001.dao.parse;

import by.epam.task6001.bean.User;
import by.epam.task6001.bean.UserRole;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserParser {
    private static final UserParser instance = new UserParser();

    private UserParser() {
    }

    public static UserParser getInstance() {
        return instance;
    }

    public User parse(String userString) throws ParseException {
        User user;
        String login;
        String name;
        String email;
        UserRole role;
        String password;

        String userRegex;
        Pattern pattern;
        Matcher matcher;

        userRegex = "login=\"(.+)\"\\sname=\"(.+)\"\\semail=\"(.+@[a-z]+\\.[a-z]+)\"\\srole=(USER|ADMIN)\\spassword=\"(.+)\"";
        pattern = Pattern.compile(userRegex);
        matcher = pattern.matcher(userString);

        if (matcher.find()) {
            login = matcher.group(1);
            name = matcher.group(2);
            email = matcher.group(3);
            role = UserRole.valueOf(matcher.group(4));
            password = matcher.group(5);

            user = new User(login, name, email, role, password);
            return user;

        } else {
            throw new ParseException("Incorrect users string!");
        }
    }
}
