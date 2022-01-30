package com.example.demo;

import com.example.demo.domain.Command;
import com.example.demo.helper.Parser;
import com.example.demo.helper.RequestAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void testStringToCommand(){
        Parser parser = new Parser();

        //ошибка в количесвте входных переменных
        //на выходе ожидается незаполненный класс
        assertEquals(new Command(), parser.stringToCommand("VASYA CREATE 123 fsdf sfgsg"));
        assertEquals(new Command(), parser.stringToCommand("Vasy Create"));
        assertEquals(new Command(), parser.stringToCommand("MISHA LIST_TASK"));
        assertNotEquals(new Command("Vasy", RequestAction.LIST_TASK, "56"), parser.stringToCommand("Vasy LIST_TASK 56 fsdf wer f"));
        assertEquals(new Command(), parser.stringToCommand(""));
        assertEquals(new Command(), parser.stringToCommand("       "));
        assertEquals(new Command(), parser.stringToCommand("VASYA CREATE  12"));


        //ошибка в формате входной команды
        //на выходе ожидается незаполненный класс
        assertEquals(new Command(), parser.stringToCommand("VASYA CREATE 123"));
        assertEquals(new Command(), parser.stringToCommand("Vasy Create_TAsk 56"));
        assertEquals(new Command(), parser.stringToCommand("Vasy   56"));
        assertEquals(new Command(), parser.stringToCommand("MISHA DELETE_TASk 56"));
        assertEquals(new Command(), parser.stringToCommand("MISHA CLOSETASK 56"));
        assertEquals(new Command(), parser.stringToCommand("MISHA reopen_task 56"));
        assertEquals(new Command(), parser.stringToCommand("MISHA LIST_TASKS VASYA"));


        //корректное выполнение функции
        assertNotEquals(new Command(), parser.stringToCommand("Vasy DELETE_TASK 56"));
        assertEquals(new Command("Vasy", RequestAction.LIST_TASK, "56"), parser.stringToCommand("Vasy LIST_TASK 56"));
        assertEquals(new Command("Vasy", RequestAction.LIST_TASK, "56777"), parser.stringToCommand("Vasy LIST_TASK 56777"));
        assertEquals(new Command("Vasy", RequestAction.REOPEN_TASK, "56777"), parser.stringToCommand("Vasy REOPEN_TASK 56777"));
        assertEquals(new Command("Vasy", RequestAction.CLOSE_TASK, "56777"), parser.stringToCommand("Vasy CLOSE_TASK 56777"));
        assertEquals(new Command("Vasy", RequestAction.DELETE_TASK, "56777"), parser.stringToCommand("Vasy DELETE_TASK 56777"));
        assertEquals(new Command("Vasy", RequestAction.CREATE_TASK, "56777"), parser.stringToCommand("Vasy CREATE_TASK 56777"));
    }

    @Test
    void testParserResult(){
        //return Command
        //По результатам работы класса Parser сравнение c null проиходит по полю action
        Command command = new Command();
        assertNull(command.getAction());
        assertNotSame(command.getAction(), RequestAction.CREATE_TASK);

        Command command2 = new Command("123", RequestAction.CLOSE_TASK, "456");
        assertNotNull(command2.getAction());
    }

}
