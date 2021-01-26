package com.ivan.app.cipher;

public interface CaesarCipher {
  
  void encrypt(char[] plainText, int key, char[] alph);

  void decrypt(char[] encryptedText, int key, char[] alph);

}
