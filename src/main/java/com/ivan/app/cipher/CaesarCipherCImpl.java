package com.ivan.app.cipher;

import java.util.*;
import java.util.stream.Collectors;

import com.ivan.app.NativeLibUtils;

public class CaesarCipherCImpl implements CaesarCipher {

  static {
    NativeLibUtils.resolveLibrary("libCaesarCipherCImpl_c.so");
  }

  private native char[] encryptNative(char[] plainText, int key, char[] alph);

  private native char[] decryptNative(char[] encryptedText, int key, char[] alph);

  private boolean charArrayContainsAlphMissmatch(char[] charArray, char[] alph){

    Set<Character> uniqueCharacters = new HashSet<>();
    for(char c : charArray){
      if(c!=' ') uniqueCharacters.add(Character.toUpperCase(c));
    }

    boolean containsUnknownCharacters = !uniqueCharacters.stream().filter(c -> {
      for(char c1 : alph){
        if(c == c1) return false;
      }

      return true;
    }).collect(Collectors.toList()).isEmpty();

    return containsUnknownCharacters;
  }

  @Override
  public char[] encrypt(char[] plainText, int key, char[] alph) {
    if(plainText == null || alph == null){
      throw new NullPointerException();
    }

    if(charArrayContainsAlphMissmatch(plainText, alph)){
      throw new AlphabetMissmatchException();
    }


    return encryptNative(plainText, key, alph);
  }

  @Override
  public char[] decrypt(char[] encryptedText, int key, char[] alph) {
    if(encryptedText == null || alph == null){
      throw new NullPointerException();
    }


    return decryptNative(encryptedText, key, alph);
  }
  
}
