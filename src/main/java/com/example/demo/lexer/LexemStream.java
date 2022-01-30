package com.example.demo.lexer;

import java.util.List;

public interface LexemStream extends List<SwpLexem> {
    String getCommand();
}
