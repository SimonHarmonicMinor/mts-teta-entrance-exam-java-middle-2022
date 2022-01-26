package mts.teta.exam;


public class TestApplication {

  public static void main(String[] args) {
    try
    {
    Server server=new Server();
    server.start();
    System.out.println("Server started");
    System.out.println("Press ENTER to exit");
    System.in.read();
    }
    catch (Exception e)
    {
       System.err.println("Error: "+e.getMessage());
    }
  }

}
