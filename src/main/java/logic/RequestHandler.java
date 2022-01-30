package logic;

import objects.Request;

public class RequestHandler {

    public String processLine(String line){
        String result;
        if (!TaskActions.isCommandFormatCorrect(line)) return "WRONG_FORMAT";
        else{
            Request request = new Request(line);
            String command = request.getCommand();
            TaskActions taskActions = new TaskActions();
            switch (command){
                case "CREATE_TASK":
                    result = taskActions.createTask(request);
                    break;
                case "DELETE_TASK":
                    result = taskActions.deleteTask(request);
                    break;
                case "CLOSE_TASK":
                    result = taskActions.closeTask(request);
                    break;
                case "REOPEN_TASK":
                    result = taskActions.reopenTask(request);
                    break;
                case "LIST_TASK":
                    result = taskActions.getUserTaskList(request);
                    break;
                default:
                    return "ERROR";
            }
        }

        return result;
    }
}
