package brush.util;

import java.io.UnsupportedEncodingException;

public class WebtrendsEncodeUtils
{
  private static final String ISO_8859_1 = "ISO-8859-1";
  private static final String UTF_8 = "UTF-8";
  private static final String UTF_16BE = "UTF-16BE";
  private static final String US_ASCII = "US-ASCII";
  private static final String DEFAULT_STR_ENCODING = "ISO-8859-1";

  public static String toBase64HASH(int paramInt)
  {
    return toBase64HASH(Integer.toString(paramInt));
  }

  public static String toBase64HASH(String paramString)
  {
    return toBase64HASH(paramString, "ISO-8859-1");
  }

  public static String toBase64HASH(String paramString1, String paramString2)
  {
    byte[] arrayOfByte = null;
    arrayOfByte = longToByte(defaultHash(paramString1));
    return Base64URLEncode(arrayOfByte);
  }

  public static String Base64Encode(String paramString)
  {
    try
    {
      return Base64URLEncode(paramString.getBytes("ISO-8859-1"));
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    return Base64URLEncode(paramString.getBytes());
  }

  public static String Base64URLEncode(byte[] paramArrayOfByte)
  {
    String str;
    try
    {
      str = new String(WebtrendsBase64.encodeURLSafe(paramArrayOfByte, false));
    }
    catch (Exception localException)
    {
      str = "BASE64_CONVERSION_FAILED";
    }
    return str.toString();
  }

  public static long defaultHash(String paramString)
  {
    return APHash(paramString);
  }

  public static long APHash(String paramString)
  {
    long l = -1431655766L;
    for (int i = 0; i < paramString.length(); i++)
      if ((i & 0x1) == 0)
        l ^= l << 7 ^ paramString.charAt(i) * (l >> 3);
      else
        l ^= (l << 11) + paramString.charAt(i) ^ l >> 5 ^ 0xFFFFFFFF;
    return l;
  }

  public static byte[] longToByte(long paramLong)
  {
    byte[] arrayOfByte = new byte[8];
    int i = 56;
    int j = 0;
    while (i >= 0)
    {
      arrayOfByte[(j++)] = ((byte)(int)(paramLong >>> i));
      i -= 8;
    }
    return arrayOfByte;
  }
}

