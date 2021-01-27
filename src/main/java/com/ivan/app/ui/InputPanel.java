package com.ivan.app.ui;

import java.awt.*;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:data.properties")
public class InputPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  @Value("${panel.menu.height}")
  private int height;

  @Value("${panel.menu.width}")
  private int width;

  public InputPanel() {
    super();
  }

  @PostConstruct
  private void init(){
    setBackground(new Color(222, 222, 222));
    setBounds(0, 0, width, height);
    setPreferredSize(new Dimension(width, height));

    JLabel inputtextLabel = new JLabel("Input text");
    inputtextLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JTextArea inputTextArea = new JTextArea(20,20);//set the object properties and behaviour
    inputTextArea.setText("");
    inputTextArea.setLineWrap(true);
    inputTextArea.setWrapStyleWord(true);
    inputTextArea.setMargin(new Insets(5, 5, 5,5));

    JPanel inputControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel inputTextPanel = new JPanel();
    inputTextPanel.setLayout(new BorderLayout());
    inputTextPanel.setBounds(0, 0, width-20, height-50);
    inputTextPanel.setPreferredSize(new Dimension(width-20, height-50));
    inputTextPanel.add(inputtextLabel, BorderLayout.NORTH);
    inputTextPanel.add(new JScrollPane(inputTextArea), BorderLayout.CENTER);
    inputTextPanel.add(inputControlPanel, BorderLayout.SOUTH);

    add(inputTextPanel);

    JButton decryptButton = new JButton("Decrypt");
    add(decryptButton);

    JButton encryptButton = new JButton("Encrypt");
    add(encryptButton);

    setFocusable(true);
    requestFocus();
  }

}
