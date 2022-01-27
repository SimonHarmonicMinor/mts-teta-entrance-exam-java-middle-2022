package mts.teta.exam;

class InvalidSyntaxCommandException extends ErrorResponseException{
    InvalidSyntaxCommandException(String text)
    {
        super("Invalid command syntax: "+text);
    }
    @Override
    String getErrorResponse()
    {
        return ResultType.WRONG_FORMAT.name();
    }
}
