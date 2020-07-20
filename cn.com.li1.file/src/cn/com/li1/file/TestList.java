package cn.com.li1.file;

import java.util.ArrayList;
import java.util.List;

public class TestList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<Integer> list = new ArrayList() ;
		list.add(1);
		list.add(2);
		list.add(1);
		long count= list.stream().distinct().count();
		boolean isRepeat = count < list.size();
		System.out.println(count);//输出2
		System.out.println(isRepeat);//输出true
	}

}
