import org.eclipse.jetty.server.Server;

public class JettyServers_2imxUOOdfRpt {
  public static void startOrExitJvm(Server server) {
    try {
      server.start();
    }
    catch (Exception e) {
      e.printStackTrace();

      try {
        server.stop();
      }
      catch (Exception e1) {
        e1.printStackTrace();
      }
    }
  }
}
