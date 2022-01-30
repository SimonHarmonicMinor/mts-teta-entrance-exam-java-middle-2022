package objects;

public class Request {
    String userName;
    String command;
    String arg;

    public Request(String string) {
        String[] request = string.split("\\s");
        this.userName = request[0];
        this.command = request[1];
        this.arg = request[2];
    }

    public String getUserName() {
        return userName;
    }

    public String getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }
}
