import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServers_2imxUOOdfRpt {
  public static void startOrExitJvm(Server server) {
    System.out.println(">>>>>> Start server");

    try {
      WebAppContext wac = (WebAppContext) ((HandlerWrapper) server.getHandler()).getHandler();
      wac.setThrowUnavailableOnStartupException(true);

      server.start();
      System.out.println(">>>>>> Server started successfully");
    }
    catch (Throwable t) {
      System.err.println(">>>>>> Error encountered during server start");
      t.printStackTrace();

      try {
        Thread.sleep(750);
      }
      catch (InterruptedException ignored) {
      }
      System.exit(132);
    }
  }
}
