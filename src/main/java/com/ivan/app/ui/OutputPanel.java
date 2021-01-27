package com.ivan.app.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.ivan.app.cipher.KeyState;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:data.properties")
public class OutputPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  private static final Logger logger = LogManager.getLogger(OutputPanel.class);

  public OutputPanel() {
    super();
  }

  @Autowired
  private KeyState keyState;

  @Autowired
  private MainWindow mainWindow;

  private JButton keyStateButton; 
  private JTextArea outputTextArea;

  public void setOutputText(String text){
    outputTextArea.setText(text);
  }

  private void changeKey(ActionEvent e){
    String result = (String) JOptionPane.showInputDialog(mainWindow, "Set alphabet", "Dialog box",
        JOptionPane.PLAIN_MESSAGE, null, null, String.format("%d", keyState.getK()));
    if (result != null && result.length() > 0) {
      
      try{
        int newK = Integer.parseInt(result);
        keyState.setK(newK);
        keyStateButton.setText(String.format("K=%d", newK));
      }catch(NumberFormatException ex){
        JOptionPane.showMessageDialog(
          mainWindow, "Number is invalid :(", "Warning", JOptionPane.WARNING_MESSAGE);
        
        logger.warn("Parse key value with error", ex);
      }

    } else {
      logger.info("Use old K value {}", keyState.getK());
    }
  }

  @PostConstruct
  private void init() {
    setBackground(new Color(222, 222, 222));

    JLabel outputTextLabel = new JLabel("Output text");
    outputTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

    outputTextArea = new JTextArea(20, 20);
    outputTextArea.setText("");
    outputTextArea.setLineWrap(true);
    outputTextArea.setWrapStyleWord(true);
    outputTextArea.setMargin(new Insets(5, 5, 5, 5));
    outputTextArea.setEditable(false);

    JPanel outputControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel outputTextPanel = new JPanel();
    outputTextPanel.setLayout(new BorderLayout());
    outputTextPanel.setBounds(0, 0, 230, 310);
    outputTextPanel.setPreferredSize(new Dimension(230, 310));
    outputTextPanel.add(outputTextLabel, BorderLayout.NORTH);
    outputTextPanel.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);
    outputTextPanel.add(outputControlPanel, BorderLayout.SOUTH);
    
    keyStateButton = new JButton(String.format("K=%d", keyState.getK()));
    outputControlPanel.add(keyStateButton);
    keyStateButton.addActionListener(this::changeKey);

    add(outputTextPanel, BorderLayout.NORTH);

    setFocusable(true);
    requestFocus();
  }

}
