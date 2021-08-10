package logic;

import java.util.Scanner;

public class UserMenu extends Menu{
    private Library library;
    private Scanner scanner;
    private String input;

    public UserMenu(Library library){
        scanner= new Scanner(System.in);
        this.library=library;
    }

    @Override
    public void start() {
        print();
        processInput();
    }
    protected void print(){
        System.out.println();
        System.out.println("   MENU   ");
        System.out.println(" - To see books press 1");
        System.out.println(" - To search book press 2");
        System.out.println();
        System.out.println(" - To exit press 0");
    }
    protected void processInput(){

    }



}
