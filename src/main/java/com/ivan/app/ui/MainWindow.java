package com.ivan.app.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:data.properties")
public class MainWindow extends JFrame {

  private static final long serialVersionUID = 1L;

  @Value("${window.height}")
  private int windowHeight;

  @Value("${window.width}")
  private int windowWidth;

  @Value("${window.name}")
  private String windowName;

  @Autowired
  private InputPanel inputPanel;

  @Autowired
  private EncryptPanel encryptPanel;

  @PostConstruct
  public void onInit() {
    setTitle(windowName);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(windowWidth, windowHeight));
    setResizable(false);
    setLocationRelativeTo(null);
    setLayout(new FlowLayout(FlowLayout.LEFT));

    add(inputPanel);
    add(encryptPanel);

    setVisible(true);
  }

  public MainWindow() {
    super();
  }

}