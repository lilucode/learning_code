package cn.com.lilu.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		File file=new File("E:\\git\\aase\\codescan\\agree-runtime\\bin\\main\\csd\\A7634\\private");
		String[] listDir=null;
		if(file.isDirectory()) {
			listDir=file.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					File file=new File(dir,name);
					if(file.isDirectory()) {
						return true;
					}
					return false;
				}
				
			});
		}
		System.out.println(Arrays.toString(listDir));
	}
}
