import org.eclipse.jetty.server.Server;

public class JettyServers_2imxUOOdfRpt {
  public static void startOrExitJvm(Server server) {
    System.out.println(">>>>>> Start server");

    try {
      server.start();
      System.out.println(">>>>>> Server started successfully");
    }
    catch (Throwable t) {
      System.err.println(">>>>>> Error encountered during server start");
      t.printStackTrace();
      System.exit(132);
    }
  }
}
