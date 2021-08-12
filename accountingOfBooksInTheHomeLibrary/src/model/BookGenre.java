package model;

public enum BookGenre {
    Fantasy,
    Mystery,
    Thriller,
    Romance,
    Novel,
    Epic_Novel,
    History,
    Drama,
    Biography,
    Fairy_Tale,
    Comic,
    Short_Story,
    Legend;

    public static boolean isExistGenre(String genre) {
        for (BookGenre genre1 : values()) {
            if (genre1.toString().equals(genre)) {
                return true;
            }
        }
        return false;
    }
}
