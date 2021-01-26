package com.ivan.app.cipher;

import com.ivan.app.NativeLibUtils;

public class CaesarCipherCImpl implements CaesarCipher {

  static {
    NativeLibUtils.resolveLibrary("libCaesarCipherCImpl_c.so");
  }

  @Override
  public native void encrypt(char[] plainText, int key, char[] alph);

  @Override
  public native void decrypt(char[] encryptedText, int key, char[] alph);
  
}
