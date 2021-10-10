package by.epam.task6001.main.menu;

public enum MenuText {

    UserMainMenu("""
            ----------------   MENU   ----------------
             - To view books enter 1
             - To search book enter 2
             - To suggest a book enter 3

             - To exit enter 0
            """),
    AdminMainMenu("""
            ----------------   MENU   ----------------
             - To view books enter 1
             - To search book enter 2
             - To add book enter 3
             - To remove book enter 4

             - To exit enter 0
            """),
    SearchBookMenu("""
            ---------------  Search book ---------------
            Select an parameter to search:
             - title - enter 1
             - author - enter 2
             - id - enter 3

             - MENU - enter 0
            """),
    RemoveCommandTitle("--------------  Remove book --------------"),
    AddCommandTitle("---------------  Add book ---------------"),
    ViewCommandTitle("-------------    All books    ---------------"),
    FoundBooksTitle("--------     Found books:     --------"),
    SuggestBookTitle("---------------    Suggest a book    ---------------"),
    StartTitle("------------------------------ THE HOME LIBRARY ------------------------------");

    private final String text;

    MenuText(String text) {
        String indent = "\n\n\n\n";
        this.text = indent + text + "\n";
    }

    public String getText() {
        return text;
    }
}
