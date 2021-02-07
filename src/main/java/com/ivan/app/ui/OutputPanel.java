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
import com.ivan.app.cipher.KeyState.KeyStateException;
import com.ivan.app.cipher.KeyState.KeyUnadmesibleValueException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:data.properties")
public class OutputPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger(OutputPanel.class);

    public OutputPanel() {
        super();
    }

    @Autowired
    private KeyState keyState;

    @Autowired
    private MainWindow mainWindow;

    @Value("${panel.input.height}")
    private int height;

    @Value("${panel.input.width}")
    private int width;

    private JButton keyStateButton;
    private JTextArea outputTextArea;

    public void setOutputText(String text) {
        outputTextArea.setText(text);
    }

    private void changeKey(ActionEvent e) {
        String result = (String) JOptionPane.showInputDialog(mainWindow, "Set key", "Key",
                JOptionPane.PLAIN_MESSAGE, null, null, String.format("%d", keyState.getK()));
        if (result != null && result.length() > 0) {

            try {
                int newK = Integer.parseInt(result);
                keyState.setK(newK);
                keyStateButton.setText(String.format("K=%d", keyState.getK()));
                LOG.info("key1: {}", keyState.getK());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainWindow, "Numarul introdus este invalid!",
                        "Warning", JOptionPane.WARNING_MESSAGE);

                LOG.warn("Parse key value with error", ex);
            } catch (KeyStateException ex) {

                String msg = String.format("Valoarea introdusa trebuie sa fie intre %d si %d",
                        keyState.getMinLength(), keyState.getMaxLength());
                JOptionPane.showMessageDialog(mainWindow, msg, "Warning",
                        JOptionPane.WARNING_MESSAGE);
                LOG.warn(ex);
            } catch (KeyUnadmesibleValueException ex) {

                String msg = String.format("Valoarea introdusa nu trebuie sa fie %d sau %d",
                        keyState.getMinLength(), keyState.getMaxLength());
                JOptionPane.showMessageDialog(mainWindow, msg, "Warning",
                        JOptionPane.WARNING_MESSAGE);
                LOG.warn(ex);
            }

        } else {
            LOG.info("Use old K value {}", keyState.getK());
        }
    }

    @PostConstruct
    private void init() {
        setBackground(new Color(222, 222, 222));
        setBounds(0, 0, width, height);
        setPreferredSize(new Dimension(width + 20, height));

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
        outputTextPanel.setBounds(0, 0, width - 50, height - 80);
        outputTextPanel.setPreferredSize(new Dimension(width - 50, height - 80));
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
