package mts.teta.exam;

public class ErrorResponseException extends Exception{
    String getErrorResponse()
    {
        return ResultType.ERROR.name();
    }
}
