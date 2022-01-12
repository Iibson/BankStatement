package pl.nullreference.bankstatement.model.source;

import java.util.Locale;

public enum SourceType {
    DIRECTORY,
    ENDPOINT;

    public String toString() {
        return this.name().substring(0,1).toUpperCase(Locale.ROOT) + this.name().substring(1).toLowerCase(Locale.ROOT);
    }
}
