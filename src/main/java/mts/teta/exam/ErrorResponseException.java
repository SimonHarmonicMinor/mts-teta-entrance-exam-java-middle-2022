package mts.teta.exam;

class ErrorResponseException extends Exception{
    ErrorResponseException(String message)
    {
        super(message);
    }
    String getErrorResponse()
    {
        return ResultType.ERROR.name();
    }
}
