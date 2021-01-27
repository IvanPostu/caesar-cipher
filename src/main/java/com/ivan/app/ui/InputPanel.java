package com.ivan.app.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.ivan.app.cipher.AlphabetState;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:data.properties")
public class InputPanel extends JPanel {

  private static final Logger logger = LogManager.getLogger(InputPanel.class);
  private static final long serialVersionUID = 1L;

  @Autowired
  private MainWindow mainWindow;

  public void setMainWindow(@Lazy MainWindow mainWindow) {
    this.mainWindow = mainWindow;
  }

  @Autowired
  private AlphabetState alphabetState;

  @Value("${panel.input.height}")
  private int height;

  @Value("${panel.input.width}")
  private int width;

  public InputPanel() {
    super();
  }

  private void changeAlphabet() {
    String alphabet = alphabetState.toString();
    String result = (String) JOptionPane.showInputDialog(mainWindow, "Set alphabet", "Dialog box",
        JOptionPane.PLAIN_MESSAGE, null, null, alphabet);
    if (result != null && result.length() > 0) {
      alphabetState.setAlphabet(result.toCharArray());
      logger.info("New alphabet value is {}", result);
    } else {
      logger.warn("Empty alph setted!!!");
    }
  }

  @PostConstruct
  private void init() {
    setBackground(new Color(222, 222, 222));
    setBounds(0, 0, width, height);
    setPreferredSize(new Dimension(width, height));

    JLabel inputtextLabel = new JLabel("Input text");
    inputtextLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JTextArea inputTextArea = new JTextArea(20, 20);
    inputTextArea.setText("");
    inputTextArea.setLineWrap(true);
    inputTextArea.setWrapStyleWord(true);
    inputTextArea.setMargin(new Insets(5, 5, 5, 5));

    JPanel inputControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel inputTextPanel = new JPanel();
    inputTextPanel.setLayout(new BorderLayout());
    inputTextPanel.setBounds(0, 0, width - 20, height - 80);
    inputTextPanel.setPreferredSize(new Dimension(width - 20, height - 80));
    inputTextPanel.add(inputtextLabel, BorderLayout.NORTH);
    inputTextPanel.add(new JScrollPane(inputTextArea), BorderLayout.CENTER);
    inputTextPanel.add(inputControlPanel, BorderLayout.SOUTH);

    add(inputTextPanel);

    JButton decryptButton = new JButton("Decrypt");
    add(decryptButton);

    JButton encryptButton = new JButton("Encrypt");
    add(encryptButton);

    JButton changeAlphabet = new JButton("Change Alphabet");
    changeAlphabet.addActionListener((e) -> {
      changeAlphabet();
    });
    add(changeAlphabet);

    setFocusable(true);
    requestFocus();
  }

}
