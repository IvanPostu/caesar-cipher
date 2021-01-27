package com.ivan.app.ui;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:data.properties")
public class EncryptPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  @Value("${panel.menu.height}")
  private int height;

  @Value("${panel.menu.width}")
  private int width;

  public EncryptPanel() {
    super();

  }

  @PostConstruct
  private void init(){
    setBackground(Color.white);
    setBorder(BorderFactory.createLineBorder(Color.yellow));
    setBounds(0, 0, width, height);
    setPreferredSize(new Dimension(width, height));
    setLayout(new FlowLayout(FlowLayout.RIGHT));

    setFocusable(true);
    requestFocus();
  }

}
