package by.epam.task6001.controller.impl.command;

import by.epam.task6001.controller.ControllerException;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private final Map<String, Command> commands;

    public CommandProvider(){
        commands=new HashMap<>();
    }

    public void putUserCommands(){
        commands.put("viewBooks", new ViewBookCommand());
        commands.put("searchBooks", new SearchBookCommand());
        commands.put("countQuantity", new CountQuantityOfBooksCommand());
        commands.put("suggestBook", new SuggestBookCommand());
    }

    public void putAdminCommands(){
        commands.put("viewBooks", new ViewBookCommand());
        commands.put("searchBooks", new SearchBookCommand());
        commands.put("countQuantity", new CountQuantityOfBooksCommand());
        commands.put("addBook", new AddBookCommand());
        commands.put("removeBook", new RemoveBookCommand());
    }

    public Command getCommand(String commandName) throws ControllerException {
        Command command;

        if(commands.containsKey(commandName)) {
            command = commands.get(commandName);
            return command;
        } else{
            throw new ControllerException("Incorrect command name! "+commandName);
        }
    }
}
