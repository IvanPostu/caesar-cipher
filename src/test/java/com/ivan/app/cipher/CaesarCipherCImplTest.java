package com.ivan.app.cipher;

import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodName.class)
public class CaesarCipherCImplTest {
  


  @Test
  public  void encryptDecryptSimpleTestCase(){
    CaesarCipher cipher = new CaesarCipherCImpl();
    
    char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWX".toCharArray();
    char[] init = "Hello World".toCharArray();

    char[] encryptedAndDecrypted = cipher
      .decrypt(cipher.encrypt(init, 3, alphabet), 3, alphabet);

    char[] expectedEncryptedAndDecrypted = "HELLOWORLD".toCharArray();

    Assertions.assertArrayEquals(expectedEncryptedAndDecrypted, encryptedAndDecrypted);
  }

  @Test
  public  void encryptDecryptExceptionCaseTest(){
    CaesarCipherCImpl cipher = new CaesarCipherCImpl();
    
    Assertions.assertThrows(NullPointerException.class, () -> {
      cipher.encrypt(null, 2, "aaa".toCharArray());
    });

    Assertions.assertThrows(NullPointerException.class, () -> {
      cipher.encrypt("aaa".toCharArray(), 2, null);
    });

    Assertions.assertThrows(NullPointerException.class, () -> {
      cipher.decrypt(null, 2, "aaa".toCharArray());
    });

    Assertions.assertThrows(NullPointerException.class, () -> {
      cipher.decrypt("aaa".toCharArray(), 2, null);
    });

  }

  @Test
  public  void encryptAlphabetMissmatchExceptionCaseTest(){
    CaesarCipherCImpl cipher = new CaesarCipherCImpl();
    
    Assertions.assertThrows(AlphabetMissmatchException.class, () -> {
      cipher.encrypt("abc def0".toCharArray(), 2, "ABCDEFGH".toCharArray());
    });

    Assertions.assertDoesNotThrow(() -> {
      cipher.encrypt("abc defaaaaa".toCharArray(), 2, "ABCDEFGH".toCharArray());
    });

  }


}