package logic;

public enum MenuText {

    UserMainMenu("----------------   MENU   ----------------\n" +
            " - To view books enter 1\n"+
            " - To search book enter 2\n"+
            " - To suggest a book enter 3\n\n"+
            " - To exit enter 0\n"),
    AdminMainMenu("----------------   MENU   ----------------\n" +
            " - To view books enter 1\n"+
            " - To search book enter 2\n"+
            " - To add book enter 3\n"+
            " - To remove book enter 4\n\n"+
            " - To exit enter 0\n"),
    SearchBookMenu("---------------  Search book ---------------\n"+
        "Select an parameter to search:\n"+
        " - title - enter 1\n"+
        " - author - enter 2\n"+
        " - id - enter 3\n\n"+
        " - To return to the main menu enter any other symbol\n"),


    ;
    private String text;
    private final String indent="\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";


    MenuText(String text) {
        this.text=indent+text;
    }
    public String getText(){
        return text;
    }
}
