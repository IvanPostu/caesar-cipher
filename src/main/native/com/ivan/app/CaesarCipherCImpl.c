#include <stdio.h>
#include <math.h>
#include <memory.h>
#include <stdlib.h>
#include "com_ivan_app_cipher_CaesarCipherCImpl.h"

struct str
{
  char *str;
  int size;
};

int charIndexInAlph(char c, char *alph)
{
  int i = 0;
  for (i;; i += 2)
    if (c == *(alph + i))
      return i;
}

struct str *encrypt(struct str *normalizedPlainStr, int k, char *alph, unsigned int alphLen)
{
  int i, j;
  unsigned short charIndexinAlph;
  struct str *res = (struct str *)malloc(sizeof(struct str));
  res->size = normalizedPlainStr->size;
  res->str = (char *)malloc(sizeof(char) * normalizedPlainStr->size);

  for (i = 0; i < normalizedPlainStr->size; i += 2)
  {
    charIndexinAlph = charIndexInAlph(*(normalizedPlainStr->str + i), alph);
    k %= alphLen;

    j = charIndexinAlph + k;
    if (j >= alphLen)
    {
      j -= alphLen;
    }

    char c = *(alph + j);
    *(res->str + i) = c;
    *(res->str + i + 1) = 0;
  }

  return res;
}

struct str *decrypt(struct str *encryptedStr, int k, char *alph, unsigned int alphLen)
{
  int i, j;
  unsigned short charIndexinAlph;
  struct str *res = (struct str *)malloc(sizeof(struct str));
  res->size = encryptedStr->size;
  res->str = (char *)malloc(sizeof(char) * encryptedStr->size);

  for (i = 0; i < encryptedStr->size; i += 2)
  {
    charIndexinAlph = charIndexInAlph(*(encryptedStr->str + i), alph);
    k %= alphLen;

    j = charIndexinAlph - k;
    if (j < 0)
    {
      j = alphLen - abs(j);
    }

    char c = *(alph + j);
    *(res->str + i) = c;
    *(res->str + i + 1) = 0;
  }

  return res;
}

/**
 * Înainte de criptare textul va fi transformat 
 * în majuscule și vor fi eliminate spațiile.
 * */
struct str *normalize(char *str, int size)
{
  int newSize = size;

  for (int i = 0; i < size; i += 2)
  {
    if (*(str + i) == ' ')
    {
      newSize -= 2;
    }
  }

  char *newStr = (char *)malloc(sizeof(char) * newSize);
  char ch[2];

  for (int i = 0, j = 0; i < size; i += 2)
  {
    ch[0] = *(str + i);
    ch[1] = *(str + i + 1);

    if (ch[0] == ' ')
      continue;

    if (ch[0] >= 'a' && ch[0] <= 'z')
    {
      *(newStr + j++) = 'A' + ch[0] - 'a';
      *(newStr + j++) = ch[1];
      continue;
    }

    *(newStr + j++) = ch[0];
    *(newStr + j++) = ch[1];
    printf("%c ", *(newStr + j - 1));
  }

  struct str *res = (struct str *)malloc(sizeof(struct str));
  res->size = newSize;
  res->str = newStr;

  return res;
}

JNIEXPORT jcharArray JNICALL Java_com_ivan_app_cipher_CaesarCipherCImpl_encryptNative(JNIEnv *env, jobject obj, jcharArray strPlain, jint k, jcharArray alph)
{
  int plainTextLength = (*env)->GetArrayLength(env, strPlain) * 2;
  char *plainText = (char *)(*env)->GetCharArrayElements(env, strPlain, 0);

  int alphabetTextLength = (*env)->GetArrayLength(env, alph) * 2;
  char *alphabetText = (char *)(*env)->GetCharArrayElements(env, alph, 0);

  int key = abs((int)k * 2);

  struct str *normalizedPlainStr = normalize(plainText, plainTextLength);
  (*env)->ReleaseCharArrayElements(env, strPlain, (jchar *)plainText, JNI_ABORT);
  struct str *encryptedStr = encrypt(normalizedPlainStr, key, alphabetText, alphabetTextLength);
  (*env)->ReleaseCharArrayElements(env, alph, (jchar *)alphabetText, JNI_ABORT);

  jcharArray result = (*env)->NewCharArray(env, encryptedStr->size / 2);
  (*env)->SetCharArrayRegion(env, result, 0, encryptedStr->size / 2, (jchar *)encryptedStr->str);

  free(normalizedPlainStr->str);
  free(normalizedPlainStr);

  free(encryptedStr->str);
  free(encryptedStr);

  return result;
}

JNIEXPORT jcharArray JNICALL Java_com_ivan_app_cipher_CaesarCipherCImpl_decryptNative(JNIEnv *env, jobject obj, jcharArray encryptedStr, jint k, jcharArray alph)
{
  int encryptedStrCharArrLength = (*env)->GetArrayLength(env, encryptedStr) * 2;
  char *encryptedStrCharArr = (char *)(*env)->GetCharArrayElements(env, encryptedStr, 0);

  int alphabetTextLength = (*env)->GetArrayLength(env, alph) * 2;
  char *alphabetText = (char *)(*env)->GetCharArrayElements(env, alph, 0);

  int key = abs((int)k * 2);

  struct str* encryptedStrStruct = (struct str*)malloc(sizeof(struct str));
  encryptedStrStruct->size = encryptedStrCharArrLength;
  encryptedStrStruct->str = encryptedStrCharArr;

  struct str *decryptedStr = decrypt(encryptedStrStruct, key, alphabetText, alphabetTextLength);
  (*env)->ReleaseCharArrayElements(env, alph, (jchar *)alphabetText, JNI_ABORT);
  (*env)->ReleaseCharArrayElements(env, encryptedStr, (jchar *)encryptedStrCharArr, JNI_ABORT);

  jcharArray result = (*env)->NewCharArray(env, decryptedStr->size / 2);
  (*env)->SetCharArrayRegion(env, result, 0, decryptedStr->size / 2, (jchar *)decryptedStr->str);

  free(encryptedStrStruct);

  free(decryptedStr->str);
  free(decryptedStr);

  return result;
}
