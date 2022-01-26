package mts.teta.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(CommandProcessor.class);
    private final TaskStorage taskStorage = new TaskStorage();

    String ProcessCommandText(String commandText) {
        try {
            var command = new Command();
            command.Parse(commandText);
            taskStorage.CheckCommandAccessRights(command);
            taskStorage.ExecuteCommand(command);
            return command.getResponse();

        } catch (ErrorResponseException e) {
            LOG.error("Error during command proceeding", e);
            return e.getErrorResponse();
        } catch (Exception e) {
            LOG.error("Error during command proceeding", e);
            return ResultType.ERROR.name();
        }
    }
}
