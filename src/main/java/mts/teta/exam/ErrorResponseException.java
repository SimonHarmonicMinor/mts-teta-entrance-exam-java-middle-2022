package mts.teta.exam;

class ErrorResponseException extends Exception{
    String getErrorResponse()
    {
        return ResultType.ERROR.name();
    }
}
