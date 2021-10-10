package by.epam.task6001.main.menu;

public class AuthorizationMenu {
    public String authorizationRequest(UserInput userInput){
        String login;
        String password;
        String request;

        System.out.println(MenuText.StartTitle.getText()+"\n");

        login= userInput.readNextLine("Enter login: ");
        password= userInput.readNextLine("Enter password: ");

        request="authorization login="+login+" password="+password;

        return request;
    }

    public String authorizationRequest(String message, UserInput userInput){
        String login;
        String password;
        String request;

        System.out.println(MenuText.StartTitle.getText());
        System.out.println("\n"+message+"\n\n");

        login= userInput.readNextLine("Enter login: ");
        password= userInput.readNextLine("Enter password: ");

        request="authorization login="+login+"password="+password;

        return request;
    }
}
