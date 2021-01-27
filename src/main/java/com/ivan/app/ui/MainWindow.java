package com.ivan.app.ui;

import java.awt.*;

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
  private OutputPanel outputPanel;



  @PostConstruct
  public void onInit() {
    setTitle(windowName);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(windowWidth, windowHeight));
    setResizable(false);
    setLocationRelativeTo(null);
    this.setLayout(new BorderLayout());

    add(inputPanel, BorderLayout.LINE_START);
    add(outputPanel, BorderLayout.LINE_END);

    setVisible(true);
  }

  public MainWindow() {
    super();
  }

}