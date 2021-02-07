package com.ivan.app.cipher;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

public final class AlphabetState {

    @Value("${alphabet.initialState}")
    private char[] immutableAlphabet;

    private StringBuilder changebleAlphabet;
    private char[] lastChangedValue = "".toCharArray();

    public AlphabetState() {
        changebleAlphabet = new StringBuilder();
    }

    @PostConstruct
    private void init() {
        changebleAlphabet.append(immutableAlphabet);
    }

    public void changeAlphabet(char[] newValues) throws UnknownCharacterException {
        Set<Character> alphabetSet = new LinkedHashSet<>();

        for (char c : newValues) {
            alphabetSet.add(c);
        }

        for (char c : immutableAlphabet) {
            alphabetSet.add(c);
        }

        if (alphabetSet.size() != changebleAlphabet.length()) {
            throw new UnknownCharacterException("");
        }
        lastChangedValue = newValues;

        Iterator<Character> alphabetSetIterator = alphabetSet.iterator();

        int i = 0;
        while (alphabetSetIterator.hasNext()) {
            char ch = alphabetSetIterator.next();
            this.changebleAlphabet.setCharAt(i++, ch);
        }
    }

    public char[] getLastChangedValue() {
        return lastChangedValue;
    }

    public int getAlphabetLength() {
        return changebleAlphabet.length();
    }

    public String getKeyProcessedAlphabet() {
        return new String(changebleAlphabet);
    }

    public static class UnknownCharacterException extends Exception {

        private static final long serialVersionUID = 1L;

        UnknownCharacterException(String errorMessage) {
            super(errorMessage);
        }

    }
}
