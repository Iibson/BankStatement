package pl.nullreference.bankstatement.model.bankstatement;

import java.util.Locale;

public enum Category {
    NONE,
    FOOD,
    TRANSPORT,
    SALARY,
    ENTERTAINMENT;

    public String toString() {
        return this.name().substring(0, 1).toUpperCase(Locale.ROOT) + this.name().substring(1).toLowerCase(Locale.ROOT);
    }
}
