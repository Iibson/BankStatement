package pl.nullreference.bankstatement.model.bankstatement;

import java.util.Locale;

public enum Category {
    NONE,
    FOOD,
    TRANSPORT,
    SALARY,
    ENTERTAINMENT;

    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
