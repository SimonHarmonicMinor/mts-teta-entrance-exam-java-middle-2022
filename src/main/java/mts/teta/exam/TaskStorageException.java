package mts.teta.exam;

class TaskStorageException extends ErrorResponseException{

}

class InvalidAccessRightsException extends ErrorResponseException{
    String getErrorResponse()
    {
        return ResultType.ACCESS_DENIED.name();
    }
}

class TaskAlreadyExistsException extends TaskStorageException
{

}

class TaskNotFoundException extends TaskStorageException
{

}

class InvalidTaskStateException extends TaskStorageException
{

}