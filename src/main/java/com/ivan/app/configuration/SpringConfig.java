package com.ivan.app.configuration;

import com.ivan.app.cipher.AlphabetState;
import com.ivan.app.cipher.CaesarCipher;
import com.ivan.app.cipher.CaesarCipherCImpl;
import com.ivan.app.cipher.KeyState;
import com.ivan.app.ui.InputPanel;
import com.ivan.app.ui.MainWindow;
import com.ivan.app.ui.OutputPanel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configuration
@PropertySource("classpath:data.properties")
public class SpringConfig {

    @Bean
    public MainWindow getMainWindow() {
        return new MainWindow();
    }

    @Bean
    public InputPanel getInputPanel() {
        return new InputPanel();
    }

    @Bean
    public OutputPanel getOutputPanel() {
        return new OutputPanel();
    }

    @Scope("singleton")
    @Bean
    public AlphabetState getAlphabetState() {
        return new AlphabetState();
    }

    @Bean
    public KeyState getKeyState() {
        return new KeyState(0, getAlphabetState().getAlphabetLength());
    }

    @Bean
    public CaesarCipher getCaesarCipher() {
        return new CaesarCipherCImpl();
    }

}
