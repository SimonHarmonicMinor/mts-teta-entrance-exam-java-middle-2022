package com.example.demo.ResultHandler;

public class Result {
    private ResultTypes result;
    private String resultArg;

    public String getResult() {
        if(resultArg != null && !resultArg.isEmpty())
            return result + " " + resultArg;
        return String.valueOf(result);
    }

    public void setResult(ResultTypes result) {
        this.result = result;
    }

    public void setResultArg(String resultArg) {
        this.resultArg = resultArg;
    }

}
