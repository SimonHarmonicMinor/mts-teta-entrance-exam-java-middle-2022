package mts.teta.exam;

class InvalidSyntaxCommandException extends ErrorResponseException{
    @Override
    String getErrorResponse()
    {
        return ResultType.WRONG_FORMAT.name();
    }
}
