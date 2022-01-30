package com.example.demo.lexer;

import java.util.ArrayList;
import java.util.Set;

public class SwpLexer {
    private static final Set<String> KEYWORDS = Set.of("CREATE_TASK", "DELETE_TASK", "CLOSE_TASK", "REOPEN_TASK", "LIST_TASK");

    private State state = State.DEFAULT;
    private String value = "";
    private int position = -1;

    private final String line;

    public SwpLexer(String line) {
        this.line = line;
    }

    public LexemStream tokenize() throws TokenizeException {
        LexemStream lexems = new LexemArrayList(line);

        int ch;
        while ((ch = get()) != -1) {
            SwpLexem swpLexem = nextLexem(ch);
            if (swpLexem != null) {
                lexems.add(swpLexem);
            }
        }

        if (state != State.DEFAULT) {
            SwpLexem lexem = nextLexem(ch);
            if (lexem != null) {
                lexems.add(lexem);
            }
        }

        if (value != null && value.trim().length() > 0) {
            throw new TokenizeException("can't parse '" + value + "'");
        }

        lexems.add(new SwpLexem(SwpToken.EOF, null, position));
        return lexems;
    }

    private int get() {
        position += 1;
        return position < line.length() ? line.charAt(position) : -1;
    }

    private SwpLexem nextLexem(int c) throws TokenizeException {
        char ch = (char) c;
        switch (state) {
            case DEFAULT:
                if (value != null && !value.isBlank()) {
                    throw new TokenizeException("value '" + value + "' must be empty");
                }

                if (Character.isWhitespace(ch)) {
                    return null;
                } else if (Character.isJavaIdentifierStart(ch)) {
                    state = State.WORD;
                    value += ch;
                }
                break;
            case WORD:
                if (Character.isJavaIdentifierPart(ch)) {
                    value += ch;
                } else {
                    if (KEYWORDS.contains(value.toUpperCase())) {
                        return createToken(SwpToken.TASK_NAME, true);
                    }

                    return createToken(SwpToken.WORD, true);
                }
        }

        return null;
    }

    private SwpLexem createToken(SwpToken token, boolean back) {
        SwpLexem lexem = new SwpLexem(token, value, position);
        value = "";

        state = State.DEFAULT;
        if (back) {
            position -= 1;
        }
        return lexem;
    }

    private static class LexemArrayList extends ArrayList<SwpLexem> implements LexemStream {
        private final String command;

        public LexemArrayList(String command) {
            this.command = command;
        }

        @Override
        public String getCommand() {
            return command;
        }
    }


    private enum State {
        DEFAULT, WORD
    }
}
