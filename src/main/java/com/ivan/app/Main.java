package com.ivan.app;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.SwingUtilities;

import com.ivan.app.configuration.Log4jConfiguration;
import com.ivan.app.configuration.SpringConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  static {
    Log4jConfiguration.configure();
  }

  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws IOException, URISyntaxException {

    SwingUtilities.invokeLater(() -> {
      logger.info("SwingUtilities invokeLater call." );
      new AnnotationConfigApplicationContext(SpringConfig.class);
    });


  }

}
