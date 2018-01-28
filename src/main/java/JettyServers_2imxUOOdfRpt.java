import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyServers_2imxUOOdfRpt {
  private static final Logger logger = LoggerFactory.getLogger(JettyServers_2imxUOOdfRpt.class);

  public static void startOrExitJvm(Server server) {
    logger.info(">>>>>> Start server");

    try {
      server.start();
      logger.info(">>>>>> Server started successfully");
    }
    catch (Throwable t) {
      logger.error(">>>>>> Error encountered during server start", t);
      System.exit(132);
    }
  }
}
