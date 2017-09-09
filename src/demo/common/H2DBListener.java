package demo.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecside.common.H2DriverManagerDataSource;

/**
 * 该类的职责是在WebApp启动时自动开启h2服务. 依然使用Server方式，不受AppServer的影响.
 * 
 * @author frank
 * @author calvin
 */
public class H2DBListener implements ServletContextListener {
	protected static Log logger = LogFactory.getLog(H2DBListener.class);

	public static String webRootRealPath;


	public void contextInitialized(ServletContextEvent sce) {
		logger.info("h2Listener initialize...");

		webRootRealPath = sce.getServletContext().getRealPath("/").replace('\\', '/');
		
		H2DriverManagerDataSource.setRoot(webRootRealPath);

	}

	public void contextDestroyed(ServletContextEvent sce) {
		try {
			// SET DB_CLOSE_DELAY 10

			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// do nothing
		}
		logger.info("h2db stoped...");
	}
}
