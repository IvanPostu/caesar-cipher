package com.ivan.app.cipher;

import com.ivan.app.NativeLibUtils;

public class CaesarCipherCImpl implements CaesarCipher {

  static {
    NativeLibUtils.resolveLibrary("libCaesarCipherCImpl_c.so");
  }

  private native char[] encryptNative(char[] plainText, int key, char[] alph);

  private native char[] decryptNative(char[] encryptedText, int key, char[] alph);

  @Override
  public char[] encrypt(char[] plainText, int key, char[] alph) {
    return encryptNative(plainText, key, alph);
  }

  @Override
  public char[] decrypt(char[] encryptedText, int key, char[] alph) {
    return decryptNative(encryptedText, key, alph);
  }
  
}
