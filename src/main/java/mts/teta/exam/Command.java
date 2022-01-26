package mts.teta.exam;

class Command {
    enum CommandType {
        CREATE_TASK,
        CLOSE_TASK,
        REOPEN_TASK,
        DELETE_TASK,
        LIST_TASK
    }

    private String user;
    private CommandType type;
    private String argument;

    private ResultType result;
    private String resultArgument;

    String getUser() {
        return user;
    }

    CommandType getType() {
        return type;
    }

    String getArgument() {
        return argument;
    }

    void setResult(ResultType result) {
        this.result = result;
    }

    ResultType getResult() {
        return result;
    }

    void setResultArgument(String resultArgument) {
        this.resultArgument = resultArgument;
    }

    String getResultArgument() {
        return resultArgument;
    }

    /**
     * Получить результат в текстовом виде для ответа
     * @return строковое представление ответа для отправки клиенту
     */
    String getResponse() {
        var res = result.name();
        if (resultArgument != null && !resultArgument.isEmpty()) {
            res += " " + resultArgument;
        }
        return res;
    }

    /**
     * Разбор команды из строки
     * @param commandText текст команды
     * @throws InvalidSyntaxCommandException неверная команда
     */
    void Parse(String commandText) throws InvalidSyntaxCommandException {
        try {

            var parts = commandText.split(" ");

            if (parts.length < 2 || parts.length > 3) {
                throw new InvalidSyntaxCommandException();
            }

            type = CommandType.valueOf(parts[1]);

            if (type != CommandType.LIST_TASK && parts.length < 3) {
                throw new InvalidSyntaxCommandException();
            }

            user = parts[0];

            switch (type) {
                case CREATE_TASK:
                case CLOSE_TASK:
                case REOPEN_TASK:
                case DELETE_TASK:
                    argument = parts[2];
                    break;
                case LIST_TASK:
                    if (parts.length > 2) {
                        argument = parts[2];
                    }
                    break;
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidSyntaxCommandException();
        }
    }
}
