package mts.teta.exam;

class InvalidSyntaxCommandException extends ErrorResponseException{
    String getErrorResponse()
    {
        return ResultType.WRONG_FORMAT.name();
    }
}
