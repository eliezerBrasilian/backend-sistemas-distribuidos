package app.core;

public record Message(
    String title
) {
    @Override
    public String toString(){
        return  "{title: \"" + title + "\"}";

    }
}
