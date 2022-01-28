package com.example.demo.service.parsing;

import com.example.demo.type.MyLittleBean;
import java.util.Arrays;
import java.util.List;

@MyLittleBean
public class ParserImpl implements Parser {

    @Override
    public List<String> parseToList(String input) {
        return Arrays.asList(input.split(" "));

    }
}
