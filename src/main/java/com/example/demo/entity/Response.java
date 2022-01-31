package com.example.demo.entity;

import com.example.demo.entity.enums.Result;

import java.util.ArrayList;

/**
 * Описание класса ответа
 */
public class Response {
    /**
     * Результат {@link Result}
     */
    Result result;
    Object arg;

    public Response(Builder builder){
        this.result = builder.result;
        this.arg = builder.arg;
    }

    /**
     * Построитель ответов
     */
    public static class Builder{
        Result result;
        Object arg;

        public Builder result(Result result){
            this.result = result;
            return this;
        }

        public Builder arg(Object arg){
            this.arg = arg;
            return this;
        }

        public Response build(){
            return new Response(this);
        }
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder().append(result);
        if(arg != null){
            if(arg.getClass() == ArrayList.class){
                builder.append(" ").append(arg);
            }

        }
        return builder.toString();
    }

}
