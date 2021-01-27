package com.ivan.app.cipher;

import org.springframework.beans.factory.annotation.Value;

public final class AlphabetState {

  @Value("${alphabet.initialState}")
  private char[] alphabet;

  public AlphabetState() {
  }

  public void setAlphabet(char[] alphabet) {
    this.alphabet = alphabet;
  }

  public char[] getAlphabet() {
    return alphabet;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    for(char c : alphabet){
      builder.append(c);
    }

    return builder.toString();
  }

  

} 
