package com.ivan.app.cipher;

public interface CaesarCipher {
  
  char[] encrypt(char[] plainText, int key, char[] alph);

  char[] decrypt(char[] encryptedText, int key, char[] alph);

}
