package com.ivan.app;

import java.io.IOException;
import java.net.URISyntaxException;

import com.ivan.app.cipher.CaesarCipherCImpl;
import com.ivan.app.configuration.Log4jConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
  static {
    Log4jConfiguration.configure();
  }

  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws IOException, URISyntaxException {

    CaesarCipherCImpl c = new CaesarCipherCImpl();

    char[] ccc = "cifrul world".toCharArray();

    // c.decrypt(ccc, 2);
    c.encrypt(ccc, -28, "ABCDEFGHIJKLMNOPQRSTUVWX".toCharArray());
    
    char cc = 'a';

    // c.decrypt(ccc, -28, "ABCDEFGHIJKLMNOPQRSTUVWX".toCharArray());

    // cc = 'a';
  }

}
