package mts.teta.exam;

class TaskStorageException extends ErrorResponseException {
    TaskStorageException(String message) {
        super(message);
    }
}

class InvalidAccessRightsException extends ErrorResponseException {
    InvalidAccessRightsException() {
        super("Unauthorized operation");
    }

    @Override
    String getErrorResponse() {
        return ResultType.ACCESS_DENIED.name();
    }
}

class TaskAlreadyExistsException extends TaskStorageException {
    TaskAlreadyExistsException() {
        super("Task already exists");
    }
}

class TaskNotFoundException extends TaskStorageException {
    TaskNotFoundException() {
        super("Task not found");
    }
}

class InvalidTaskStateException extends TaskStorageException {
    InvalidTaskStateException() {
        super("Unable apply command to task in it's current state");
    }
}