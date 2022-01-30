package com.example.demo.parser;

import com.example.demo.lexer.LexemStream;
import com.example.demo.lexer.SwpLexem;
import com.example.demo.lexer.SwpToken;
import com.example.demo.model.CloseTask;
import com.example.demo.model.CreateTask;
import com.example.demo.model.DeleteTask;
import com.example.demo.model.ListTasks;
import com.example.demo.model.ReOpenTask;
import com.example.demo.model.SwpCommand;

public class SwpParser {
    private int position = 0;
    private static final String CREATE_TASK = "CREATE_TASK";
    private static final String CLOSE_TASK = "CLOSE_TASK";
    private static final String DELETE_TASK = "DELETE_TASK";
    private static final String LIST_TASK = "LIST_TASK";
    private static final String REOPEN_TASK = "REOPEN_TASK";

    public SwpCommand parse(LexemStream lexems) throws SwpParseException {
        SwpCommand command = null;

        String userId = consume(lexems, SwpToken.WORD).getValue();
        if (check(lexems, SwpToken.TASK_NAME, CREATE_TASK)) {
            command = new CreateTask(userId);
        } else if (check(lexems, SwpToken.TASK_NAME, CLOSE_TASK)) {
            command = new CloseTask(userId);
        } else if (check(lexems, SwpToken.TASK_NAME, DELETE_TASK)) {
            command = new DeleteTask(userId);
        } else if (check(lexems, SwpToken.TASK_NAME, LIST_TASK)) {
            command = new ListTasks(userId);
        } else if (check(lexems, SwpToken.TASK_NAME, REOPEN_TASK)) {
            command = new ReOpenTask(userId);
        }

        if (command == null) {
            throw parseException(lexems, "unsupported start of SWP command", get(lexems, 0).getPosition());
        }

        String args = consume(lexems, SwpToken.WORD).getValue();
        command.setArgs(args);

        consume(lexems, SwpToken.EOF);

        return command;
    }

    protected boolean check(final LexemStream lexems, SwpToken type, String value) {
        SwpLexem token = get(lexems, 0);
        if (token.getToken() != type) {
            return false;
        }
        if (!String.valueOf(token.getValue()).equalsIgnoreCase(String.valueOf(value))) {
            return false;
        }
        position++;
        return true;
    }

    protected SwpLexem consume(final LexemStream lexems, SwpToken token) throws SwpParseException {
        SwpLexem lexem = get(lexems, 0);
        if (lexem.getToken() != token) {
            throw parseException(lexems, "expected " + token + ", but found " + lexem, lexem.getPosition());
        }
        return lexems.get(position++);
    }


    protected SwpLexem get(final LexemStream tokens, int offset) {
        if (position + offset >= tokens.size()) {
            return new SwpLexem(SwpToken.EOF, null, tokens.getCommand().length());
        }

        return tokens.get(position + offset);
    }

    protected SwpParseException parseException(LexemStream lexems, String message, int position) {
        String command = lexems.getCommand();
        if(position + 1 <= command.length()) {
            command = command.substring(0, position + 1);
        } else {
            command = command.substring(0, position);
        }
        return new SwpParseException("Syntax Error (" + message + ") occurred in the vicinity of: " + command + ";");
    }
}