package com.example.demo.lexer;

public class SwpLexem {
    private final SwpToken token;
    private final String value;
    private final int position;

    public SwpLexem(SwpToken token, String value, int position) {
        this.token = token;
        this.value = value;
        this.position = position;
    }

    public String getValue() {
        return value;
    }

    public SwpToken getToken() {
        return token;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "SwpLexem{" +
                "token=" + token +
                ", value='" + value + '\'' +
                ", position=" + position +
                '}';
    }
}
