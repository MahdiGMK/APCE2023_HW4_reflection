package model;

public class Message {
    private String text;

    public void setText(String text) {
        this.text = decrypt(text);
    }

    private static String decrypt(String text) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < text.length(); i++){
            builder.append((char)(text.charAt(i)^46));
        }
        return builder.toString();
    }

    public String getText() {
        return text;
    }
}
