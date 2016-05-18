package brush.util;

public class WebtrendsBase64
{
  private static final byte[] BASE64_URL_SAFE_LOOKUP = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
  private static final byte[] BASE64_STANDARD_LOOKUP = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
  private static final int ENCODE_MASK_6BITS = 63;
  private static final byte LAST_BYTE_PADDER = 61;
  public static int PADDING_LEN = 1;

  public static byte[] encode(byte[] paramArrayOfByte, boolean paramBoolean, int paramInt, Base64EncodingSet paramBase64EncodingSet)
  {
    if ((null == paramArrayOfByte) || (0 == paramArrayOfByte.length))
      return paramArrayOfByte;
    if (paramInt % 4 != 0)
      paramInt = paramInt / 4 * 4;
    else if (paramInt == 0)
      paramInt = -1;
    byte[] arrayOfByte1;
    if (paramBase64EncodingSet == Base64EncodingSet.STANDARD_SET)
      arrayOfByte1 = BASE64_STANDARD_LOOKUP;
    else
      arrayOfByte1 = BASE64_URL_SAFE_LOOKUP;
    byte[] arrayOfByte2 = paramArrayOfByte;
    int i = GetCodeLength(arrayOfByte2.length, paramInt);
    byte[] arrayOfByte3 = new byte[i];
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i3;
    int i2;
    for (i2 = 0; i2 < arrayOfByte2.length; i2++)
    {
      i3 = arrayOfByte2[i2];
      if (i3 < 0)
        i3 += 256;
      j = (i2 + 1) % 3;
      i1 = (i1 << 8) + i3;
      switch (j)
      {
      case 0:
        k = 3;
        break;
      case 1:
        k = 1;
        break;
      case 2:
        k = 2;
      }
      if (k == 3)
      {
        arrayOfByte3[(m++)] = arrayOfByte1[(i1 >> 18 & 0x3F)];
        arrayOfByte3[(m++)] = arrayOfByte1[(i1 >> 12 & 0x3F)];
        arrayOfByte3[(m++)] = arrayOfByte1[(i1 >> 6 & 0x3F)];
        arrayOfByte3[(m++)] = arrayOfByte1[(i1 >> 0 & 0x3F)];
        n += 4;
      }
      if (n == paramInt)
      {
        arrayOfByte3[(m++)] = 10;
        n = 0;
      }
    }
    i2 = 0;
    switch (k)
    {
    case 1:
      arrayOfByte3[(m++)] = arrayOfByte1[(i1 >> 2 & 0x3F)];
      arrayOfByte3[(m++)] = arrayOfByte1[(i1 << 4 & 0x3F)];
      i2 = 2;
      break;
    case 2:
      arrayOfByte3[(m++)] = arrayOfByte1[(i1 >> 10 & 0x3F)];
      arrayOfByte3[(m++)] = arrayOfByte1[(i1 >> 4 & 0x3F)];
      arrayOfByte3[(m++)] = arrayOfByte1[(i1 << 2 & 0x3F)];
      i2 = 1;
    }
    if (paramBoolean)
      for (i3 = 0; i3 < i2; i3++)
        arrayOfByte3[(m++)] = 61;
    if ((paramInt > 0) && (arrayOfByte3[m] != 10))
      arrayOfByte3[(m++)] = 10;
    byte[] arrayOfByte4 = new byte[m];
    System.arraycopy(arrayOfByte3, 0, arrayOfByte4, 0, m);
    return arrayOfByte4;
  }

  public static byte[] encode(byte[] paramArrayOfByte, boolean paramBoolean, Base64EncodingSet paramBase64EncodingSet)
  {
    return encode(paramArrayOfByte, paramBoolean, 76, paramBase64EncodingSet);
  }

  public static byte[] encode(byte[] paramArrayOfByte, Base64EncodingSet paramBase64EncodingSet)
  {
    return encode(paramArrayOfByte, true, 76, paramBase64EncodingSet);
  }

  public static byte[] encode(byte[] paramArrayOfByte)
  {
    return encode(paramArrayOfByte, true, 76, Base64EncodingSet.STANDARD_SET);
  }

  public static byte[] encodeURLSafe(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    return encode(paramArrayOfByte, paramBoolean, 0, Base64EncodingSet.URL_SAFE);
  }

  private static int GetCodeLength(int paramInt1, int paramInt2)
  {
    int i = paramInt1 + paramInt1 / 3;
    if (paramInt1 % 3 != 0)
      i++;
    if (i % 4 != 0)
      i += 4 - i % 4;
    if (paramInt2 > 0)
    {
      i += i / paramInt2 * PADDING_LEN;
      if (i % paramInt2 != 0)
        i += PADDING_LEN;
    }
    return i + 1;
  }

  public static enum Base64EncodingSet
  {
    URL_SAFE, STANDARD_SET;
  }
}

/* Location:           D:\aspire\android-hexin\HeXinTravel\libs\WebtrendsAndroidClientLib.jar
 * Qualified Name:     com.webtrends.mobile.analytics.utils.WebtrendsBase64
 * JD-Core Version:    0.6.2
 */