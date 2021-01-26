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
  for (i;; i++)
    if (c == *(alph + i))
      return i;
}

void encrypt(char *str, int strLength, int k, char *alph, unsigned int alphLen)
{
  int i, j;
  unsigned short charIndexinAlph;

  for (i = 0; i < strLength * 2; i += 2)
  {
    charIndexinAlph = charIndexInAlph(*(str + i), alph); //*(str + i) - _alph[0];
    k %= alphLen;

    j = charIndexinAlph + k;
    if (j >= alphLen)
    {
      j -= alphLen;
    }

    *(str + i) = *(alph + j);
  }
}

void decrypt(char *str, int len, int k)
{
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

JNIEXPORT void JNICALL Java_com_ivan_app_cipher_CaesarCipherCImpl_encrypt(JNIEnv *env, jobject obj, jcharArray str, jint k, jcharArray alph)
{
  int plainTextLength = (*env)->GetArrayLength(env, str) * 2;
  char *plainText = (char *)(*env)->GetCharArrayElements(env, str, 0);

  int alphabetTextLength = (*env)->GetArrayLength(env, alph) * 2;
  char *alphabetText = (char *)(*env)->GetCharArrayElements(env, alph, 0);

  struct str *normalizedPlainStr = normalize(plainText, plainTextLength);
  // for (int i = 0; i < normalizedPlainStr->size; i++)
  //   printf("%c", *(normalizedPlainStr->str + i));
  // printf("\n\n%d ", normalizedPlainStr->size);
  (*env)->ReleaseCharArrayElements(env, str, (jchar*)plainText, JNI_ABORT);

  


  // (*env)->NewCharArray()
  // (*env)->SetCharArrayRegion(env, str, 0, textLength, (jchar*)arr);
}

JNIEXPORT void JNICALL Java_com_ivan_app_cipher_CaesarCipherCImpl_decrypt(JNIEnv *env, jobject obj, jcharArray str, jint k, jcharArray alph)
{
  int textLength = (*env)->GetArrayLength(env, str);
  char *arr = (char *)(*env)->GetCharArrayElements(env, str, 0);

  // decrypt(arr, textLength, abs((int)k));

  (*env)->SetCharArrayRegion(env, str, 0, textLength, (jchar *)arr);
  (*env)->ReleaseCharArrayElements(env, str, (jchar *)arr, 0);
}
