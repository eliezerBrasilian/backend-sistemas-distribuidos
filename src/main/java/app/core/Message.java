package app.core;

public record Message(
        String type,
        String object,
        int valor,
        String datetime) {
}
