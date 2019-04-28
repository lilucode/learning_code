package cn.com.li1.type.byteutil;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class BytePrint {

	public static void main(String[] args) {

		System.out.println(new Date(System.currentTimeMillis()));
		// 第一种：
		String[] arr = { "41", "a", "5", "g56" };
		String s1 = Arrays.toString(arr);
		System.err.println(s1);// [41, a, 5, g56]
		// 第二种：
		String s2 = StringUtils.join(arr);
		System.err.println(s2);// 41a5g56
		// 第三种：
		String s3 = StringUtils.join(arr, ",");
		System.err.println(s3);// 41,a,5,g56
		// 第四种：
		StringBuffer s4 = new StringBuffer();
		for (String string : arr) {
			s4.append(string);
		}
		System.err.println(s4.toString());// 41a5g56
	}

}
