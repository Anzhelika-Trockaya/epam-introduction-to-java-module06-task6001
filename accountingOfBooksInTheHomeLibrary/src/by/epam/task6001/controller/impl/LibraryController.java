package by.epam.task6001.controller.impl;

import by.epam.task6001.bean.User;
import by.epam.task6001.bean.UserRole;
import by.epam.task6001.controller.Controller;
import by.epam.task6001.controller.ControllerException;
import by.epam.task6001.controller.impl.command.Command;
import by.epam.task6001.controller.impl.command.CommandProvider;
import by.epam.task6001.service.ServiceException;
import by.epam.task6001.service.ServiceProvider;
import by.epam.task6001.service.UserService;
import by.epam.task6001.view.UserActionViewer;
import by.epam.task6001.view.ViewProvider;

import java.util.ArrayList;
import java.util.List;

public class LibraryController implements Controller {
    private User currentUser;
    private CommandProvider commandProvider;

    public LibraryController() {
        currentUser = null;
        commandProvider = null;
    }

    @Override
    public String doAction(String request) {
        String commandName;
        Command currentCommand;
        String response;
        String[] params;
        ViewProvider viewProvider;
        UserActionViewer viewer;

        viewProvider = ViewProvider.getInstance();
        viewer = viewProvider.getUserActionViewer();

        if (request != null) {

            commandName = request.split("\\s")[0];

            if (commandName.equals("authorization")) {
                params = takeParams(request);
                response = authorization(params, viewer);

            } else if (commandName.equals("exit")) {

                currentUser = null;
                response = null;
                System.exit(0);

            } else if (currentUser != null) {

                if (!commandName.equals("suggestBook")) {
                    params = takeParams(request);
                } else {
                    params = takeParamsAndAddUserNameParam(request);
                }

                try {
                    currentCommand = commandProvider.getCommand(commandName);
                    response = currentCommand.execute(params);

                } catch (ControllerException e) {
                    response = viewer.getExceptionResponse("Incorrect request: " + request);
                }

            } else {
                response = viewer.getExceptionResponse("User is not authorization!");
            }

        } else {
            response = viewer.getExceptionResponse("Incorrect request: null");
        }

        return response;
    }

    private String authorization(String[] params, UserActionViewer viewer) {
        String response;

        commandProvider = new CommandProvider();

        try {
            currentUser = getUser(params);

            if (currentUser != null) {
                if (UserRole.ADMIN == currentUser.getRole()) {
                    commandProvider.putAdminCommands();
                } else {
                    commandProvider.putUserCommands();
                }
            }

            response = viewer.getAuthorizationResponse(currentUser);

        } catch (ServiceException | ControllerException e) {
            response = viewer.getExceptionResponse(e);
        }

        return response;
    }

    private User getUser(String[] params) throws ServiceException, ControllerException {
        User user;
        ServiceProvider serviceProvider;
        UserService userService;
        String login;
        String password;

        if (params[0].matches("login=.+") && params[1].matches("password=.+")) {
            serviceProvider = ServiceProvider.getInstance();
            userService = serviceProvider.getUserService();

            login = params[0].substring(6);
            password = params[1].substring(9);
            user = userService.authorization(login, password);
            return user;
        } else {
            throw new ControllerException("User params is not correct!");
        }
    }

    private String[] takeParams(String request) {
        String[] paramsArray;
        List<String> paramsList;

        paramsArray = null;
        paramsList = takeParamsList(request);

        if (paramsList.size() > 0) {
            paramsArray = paramsList.toArray(new String[0]);
        }

        return paramsArray;
    }

    private List<String> takeParamsList(String request) {
        StringBuilder parameterBuilder;
        List<String> paramsList;
        char currentChar;
        boolean isQuotationMarkOpen;

        paramsList = new ArrayList<>();
        parameterBuilder = new StringBuilder();

        int i = 1;
        while (i < request.length() && request.charAt(i - 1) != ' ') {
            i++;
        }

        isQuotationMarkOpen=false;

        for (; i < request.length(); i++) {

            currentChar = request.charAt(i);

            if(currentChar=='"'){
                isQuotationMarkOpen=!isQuotationMarkOpen;
            }

            if (currentChar != ' ' || isQuotationMarkOpen) {
                parameterBuilder.append(currentChar);
            } else {
                paramsList.add(parameterBuilder.toString());
                parameterBuilder = new StringBuilder();
            }

        }

        if(!parameterBuilder.isEmpty()){
            paramsList.add(parameterBuilder.toString());
        }

        return paramsList;
    }

    private String[] takeParamsAndAddUserNameParam(String request) {
        String[] params;
        List<String> paramsWithoutUserName;
        int indexOfUserNameParam;

        indexOfUserNameParam = 0;
        paramsWithoutUserName = takeParamsList(request);
        params = new String[paramsWithoutUserName.size() + 1];
        params[indexOfUserNameParam] = "userName=\"" + currentUser.getName() + "\"";

        for (int i = 0; i < paramsWithoutUserName.size(); i++) {
            params[i + 1] = paramsWithoutUserName.get(i);
        }

        return params;
    }
}
