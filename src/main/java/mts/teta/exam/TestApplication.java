package mts.teta.exam;


public class TestApplication {

    public static void main(String[] args) {
        try {
            Server server = new Server();
            int port = 9090;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
            server.start(port);
            System.out.println("Server started");
            System.out.println("Press ENTER to exit");
            System.in.read();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
