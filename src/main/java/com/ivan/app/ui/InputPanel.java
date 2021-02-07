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

import com.ivan.app.cipher.AlphabetMissmatchException;
import com.ivan.app.cipher.AlphabetState;
import com.ivan.app.cipher.CaesarCipher;
import com.ivan.app.cipher.KeyState;
import com.ivan.app.cipher.AlphabetState.UnknownCharacterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:data.properties")
public class InputPanel extends JPanel {

    private static final Logger LOG = LogManager.getLogger(InputPanel.class);
    private static final long serialVersionUID = 1L;

    @Autowired
    private MainWindow mainWindow;

    public void setMainWindow(@Lazy MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Autowired
    private AlphabetState alphabetState;

    @Autowired
    private OutputPanel outputPanel;

    @Autowired
    private CaesarCipher caesarCipher;

    @Autowired
    private KeyState keyState;

    @Value("${panel.input.height}")
    private int height;

    @Value("${panel.input.width}")
    private int width;

    private JTextArea inputTextArea;

    public InputPanel() {
        super();
    }

    private void changeAlphabet() {
        String alphabet = new String(alphabetState.getLastChangedValue());
        String result = (String) JOptionPane.showInputDialog(mainWindow, "Set alphabet",
                "Dialog box", JOptionPane.PLAIN_MESSAGE, null, null, alphabet);

        if (result == null) {
            return;
        }

        try {
            alphabetState.changeAlphabet(result.toUpperCase().toCharArray());
        } catch (UnknownCharacterException e) {
            showAlert("Cheia 2 contine valori neadmisibile");
            LOG.warn(e);
        }
        LOG.info("Alphabet: \"{}\"", new String(alphabetState.getKeyProcessedAlphabet()));

    }

    private void showAlert(String msg) {
        LOG.info("showAlert with message {}", msg);
        JOptionPane.showMessageDialog(mainWindow, msg, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void encryptButtonClick(ActionEvent event) {
        String plainText = inputTextArea.getText();

        if (plainText == null || plainText.isEmpty()) {
            LOG.warn("Plain text is empty or null !!!");
            return;
        }

        char[] cipherAlphabet = alphabetState.getKeyProcessedAlphabet().toCharArray();


        try {
            char[] encrypted =
                    caesarCipher.encrypt(plainText.toCharArray(), keyState.getK(), cipherAlphabet);
            String encryptedText = new String(encrypted);
            outputPanel.setOutputText(encryptedText);
        } catch (AlphabetMissmatchException ex) {
            showAlert("Plain text contain unknown characters.");
            LOG.warn(ex);
        }

    }

    private void decryptButtonClick(ActionEvent event) {
        String encryptedText = inputTextArea.getText();

        if (encryptedText == null || encryptedText.isEmpty()) {
            LOG.warn("Encrypted text is empty or null !!!");
            return;
        }

        char[] cipherAlphabet = alphabetState.getKeyProcessedAlphabet().toCharArray();

        try {
            char[] decryptedCharacters = caesarCipher.decrypt(encryptedText.toCharArray(),
                    keyState.getK(), cipherAlphabet);

            String decryptedtext = new String(decryptedCharacters);
            outputPanel.setOutputText(decryptedtext);
        } catch (AlphabetMissmatchException ex) {
            showAlert("Input text contain unknown characters.");
            LOG.warn(ex);
        }

    }

    @PostConstruct
    private void init() {
        setBackground(new Color(222, 222, 222));
        setBounds(0, 0, width, height);
        setPreferredSize(new Dimension(width, height));

        JLabel inputtextLabel = new JLabel("Input text");
        inputtextLabel.setHorizontalAlignment(SwingConstants.CENTER);

        inputTextArea = new JTextArea(20, 20);
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
        decryptButton.addActionListener(this::decryptButtonClick);
        add(decryptButton);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(this::encryptButtonClick);
        add(encryptButton);

        JButton changeAlphabet = new JButton("Change Alphabet");
        changeAlphabet.addActionListener((e) -> changeAlphabet());
        add(changeAlphabet);

        setFocusable(true);
        requestFocus();
    }

}
