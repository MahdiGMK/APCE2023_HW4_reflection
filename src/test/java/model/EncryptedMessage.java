package model;

public class EncryptedMessage {
    private String text;
    public EncryptedMessage(String text){
        this.text = text;
    }

    public String getText() {
        return encrypt(text);
    }

    private static String encrypt(String text) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < text.length(); i++){
            builder.append((char)(text.charAt(i)^46));
        }
        return builder.toString();
    }
}
