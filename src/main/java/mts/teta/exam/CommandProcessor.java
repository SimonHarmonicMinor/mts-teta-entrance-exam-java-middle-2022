package mts.teta.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CommandProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(CommandProcessor.class);
    private final TaskStorage taskStorage = new TaskStorage();

    /**
     * Обработать команду в виде текста, пришедшего от клиента
     * @param commandText команда в текстовом виде, полученная сервером
     * @return ответ в текстововм виде, готовый для отсылки
     */
    String processCommandText(String commandText) {
        try {
            var command = new Command();
            command.parse(commandText);
            taskStorage.checkCommandAccessRights(command);
            taskStorage.executeCommand(command);
            return command.getResponse();

        } catch (ErrorResponseException e) {
            LOG.debug(String.format("Error during command proceeding: %s", e.getMessage()));
            return e.getErrorResponse();
        } catch (Exception e) {
            LOG.error("Error during command proceeding", e);
            return ResultType.ERROR.name();
        }
    }
}
