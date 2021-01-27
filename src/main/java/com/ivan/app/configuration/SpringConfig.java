package com.ivan.app.configuration;

import com.ivan.app.ui.EncryptPanel;
import com.ivan.app.ui.MainWindow;
import com.ivan.app.ui.InputPanel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:data.properties")
public class SpringConfig {

  @Bean
  public MainWindow getMainWindow() {
    return new MainWindow();
  }

  @Bean
  public InputPanel getMenuPanel() {
    return new InputPanel();
  }

  @Bean
  public EncryptPanel getMainPanel() {
    return new EncryptPanel();
  }

}
