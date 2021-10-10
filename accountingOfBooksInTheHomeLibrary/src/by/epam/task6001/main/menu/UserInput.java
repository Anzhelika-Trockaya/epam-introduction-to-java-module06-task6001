package by.epam.task6001.main.menu;

import java.util.Scanner;

public class UserInput {
    private final Scanner scanner = new Scanner(System.in);

    public String readNextLine(String message){
        String line;

        System.out.print(message);
        line=scanner.nextLine();

        return line;
    }

    /*public int readIntValue(String message){
        int value;
        String line;
        String intRegex;

        intRegex=
        line=readNextLine(message);
        if(line.matches)
    }*/
}
