package by.epam.task6001.main;


import by.epam.task6001.controller.Controller;
import by.epam.task6001.controller.impl.LibraryController;
import by.epam.task6001.main.menu.*;

//Задание 1: создать консольное приложение “Учет книг в домашней библиотеке”.
//Общие требования к заданию:
//• Система учитывает книги как в электронном, так и в бумажном варианте.
//• Существующие роли: пользователь, администратор.
//• Пользователь может просматривать книги в каталоге книг, осуществлять поиск
//книг в каталоге.
//• Администратор может модифицировать каталог.
//• *При добавлении описания книги в каталог оповещение о ней рассылается на
//e-mail всем пользователям
//• **При просмотре каталога желательно реализовать постраничный просмотр
//• ***Пользователь может предложить добавить книгу в библиотеку, переслав её
//администратору на e-mail.
//• Каталог книг хранится в текстовом файле.
//• Данные аутентификации пользователей хранятся в текстовом файле. Пароль
//не хранится в открытом виде
public class Main {
    public static void main(String[] args) {
        LibraryMenu libraryMenu;
        UserInput userInput;
        Controller controller;

        controller = new LibraryController();
        userInput = new UserInput();

        libraryMenu = authorizationAndGetLibraryMenu(userInput, controller);

        while (true) {
            libraryMenu.startMainMenu();
        }
    }

    private static LibraryMenu authorizationAndGetLibraryMenu(UserInput userInput, Controller controller) {
        AuthorizationMenu authorizationMenu;
        String request;
        String response;

        authorizationMenu = new AuthorizationMenu();

        request = authorizationMenu.authorizationRequest(userInput);
        response = controller.doAction(request);

        while (!response.startsWith("0")) {
            request = authorizationMenu.authorizationRequest(userInput);
            response = controller.doAction(request);
        }

        if (response.startsWith("0 ADMIN ")) {
            return new AdminLibraryMenu(userInput, controller);
        } else if (response.startsWith("0 USER ")) {
            return new UserLibraryMenu(userInput, controller);
        }

        return null;
    }
}
