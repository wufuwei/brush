package brush.util;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.RandomUtils;

public class ImeiUtils {

	public static int makeImei() {
		String imeiString = String.valueOf(10000000 + RandomUtils.nextLong(0, 10000000))
				.substring(1)+ String.valueOf(10000000 + RandomUtils.nextLong(0, 10000000))
				.substring(1);
		System.out.println("imeiString:" + imeiString );
		char[] imeiChar = imeiString.toCharArray();
		int resultInt = 0;
		for (int i = 0; i < imeiChar.length; i++) {
			int a = Integer.parseInt(String.valueOf(imeiChar[i]));
			i++;
			final int temp = Integer.parseInt(String.valueOf(imeiChar[i])) * 2;
			final int b = temp < 10 ? temp : temp - 9;
			resultInt += a + b;
		}
		resultInt %= 10;
		resultInt = resultInt == 0 ? 0 : 10 - resultInt;
		System.out.println("imeiString:" + imeiString + resultInt);
		return resultInt;
	}

	
	/*
	*根据时间戳去生成
	*/
	public static String getUniqueId(Date paramDate)
		  {
		    String str1 = "2";
		    String str2 = String.valueOf(paramDate.getTime());
		    Random localRandom = new Random(paramDate.getTime());
		    for (int i = 2; i <= 32 - str2.length(); i++)
		      str1 = str1 + Integer.toHexString(localRandom.nextInt(16));
		    str1 = str1 + str2;
		    return str1;
		  }

	public static void main(String[] arags) {
		ImeiUtils.makeImei();
	}
}
