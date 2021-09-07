package com.epam.task6001.model;

public enum BookGenre {
    FANTASY,
    MYSTERY,
    THRILLER,
    ROMANCE,
    NOVEL,
    EPIC_NOVEL,
    HISTORY,
    DRAMA,
    BIOGRAPHY,
    FAIRY_TALE,
    COMIC,
    SHORT_STORY,
    LEGEND,
    OTHER;

    public static boolean isExistGenre(String genre) {
        for (BookGenre genre1 : values()) {
            if (genre1.toString().equals(genre)) {
                return true;
            }
        }
        return false;
    }
}
