package com.ivan.app;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public abstract class NativeLibUtils {
  static public void resolveLibrary(final String libName) {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    URL res = loader.getResource("nativeLibs/"+libName);
    File file;
    try {
      file = Paths.get(res.toURI()).toFile();
      String absolutePath = file.getAbsolutePath();
      System.load(absolutePath);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }
}
