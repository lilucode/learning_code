package cn.com.li1.type.byteutil;

import java.io.File;

public class StringNull {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String fileName="e:\\";
		String str=fileName.substring(fileName.lastIndexOf(File.separatorChar));
		System.out.println(str);
		if(str.contains(".")) {
			System.out.println("是文件");
		}else {
			System.out.println("不是文件");
		}
		File file=new File("e:");
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getPath());
	}

}
