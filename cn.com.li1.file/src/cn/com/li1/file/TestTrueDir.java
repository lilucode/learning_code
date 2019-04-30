package cn.com.li1.file;

import java.io.File;

public class TestTrueDir {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String source = "z:\\java\\kl\\$kls\\";
//		String source = args[0]+File.separator;
//		String targer = args[1]+File.separator;
		System.out.println(source);
//		System.out.println(targer);
		if(source.matches("^[A-z]:\\\\(.+?\\\\)*$") && source.matches("^[A-z]:\\\\(.+?\\\\)*$")){
			System.out.println("磁盘路径正确。。。");
		}
	}

}
