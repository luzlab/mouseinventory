package edu.ucsf.mousedatabase.listeners;

import java.beans.Introspector;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.ucsf.mousedatabase.HTMLGeneration;
import edu.ucsf.mousedatabase.Log;
import edu.ucsf.mousedatabase.MGIConnect;
import edu.ucsf.mousedatabase.dataimport.ImportHandler;
import edu.ucsf.mousedatabase.objects.MouseMail;

public class ContextListener implements ServletContextListener
{

  @Override
  @SuppressWarnings ("rawtypes")
  public void contextDestroyed(ServletContextEvent event)
  {
    Log.Info("Mouse Inventory Web App Removed.");

    try
    {
      Introspector.flushCaches();
      for (Enumeration e = DriverManager.getDrivers(); e.hasMoreElements();)
      {
        Driver driver = (Driver) e.nextElement();
        if (driver.getClass().getClassLoader() == getClass().getClassLoader())
        {
          Log.Info("De-registering " + driver.toString());
          DriverManager.deregisterDriver(driver);
        }
      }
      Log.Info("Removed JDBC driver from DriverManager.");
    }
    catch (Throwable e)
    {
      System.err.println("Failed to cleanup ClassLoader for webapp");
      e.printStackTrace();
    }

  }

  @Override
  public void contextInitialized(ServletContextEvent event)
  {
    Log.Initialize();
    event.getServletContext();

    Log.Info("Mouse Inventory Web App Starting.");





    ImportHandler.InitializeDefinitions();

    Context initCtx;
    try
    {
      initCtx = new InitialContext();

        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        MGIConnect.Initialize(java.lang.System.getenv("MOUSEDATABASE_MGI_DRIVER_NAME"),
          java.lang.System.getenv("MOUSEDATABASE_MGI_DATABASE_URL"),
          java.lang.System.getenv("MOUSEDATABASE_MGI_DATABASE_USER"),
          java.lang.System.getenv("MOUSEDATABASE_MGI_DATABASE_PW"));


        HTMLGeneration.setGoogleAnalyticsId(
            java.lang.System.getenv("GOOGLE_ANALYTICS_ACCOUNT"),
            java.lang.System.getenv("GOOGLE_ANALYTICS_DOMAIN_SUFFIX"));
        HTMLGeneration.SiteName = java.lang.System.getenv("MOUSEDATABASE_SITE_NAME");
        HTMLGeneration.AdminEmail = java.lang.System.getenv("MOUSEDATABASE_ADMINISTRATOR_EMAIL");
        
        MouseMail.intitialize(java.lang.System.getenv("MOUSEDATABASE_SMTP_SERVER"),
            java.lang.System.getenv("MOUSEDATABASE_SMTP_USER"), 
            java.lang.System.getenv("MOUSEDATABASE_SMTP_PW"),
            Integer.parseInt(java.lang.System.getenv("MOUSEDATABASE_SMTP_PORT")),
            Boolean.parseBoolean(java.lang.System.getenv("MOUSEDATABASE_SMTP_SSL_ENABLED")));

    }
    catch (NamingException e) {
      Log.Error("Naming exception getting environment value",e);
    }
  }

}
