package cn.com.li1.file;

import java.io.File;
import java.io.IOException;

public class TestDir {

	public static void main(String[] args) {

		File file1=new File("e:\\a");
		System.out.println(file1.isDirectory());//不存在为false
		File file2=new File("e:\\a.txt");
		System.out.println(file2.isFile());//不存在为false
		System.out.println(file2.mkdirs());//会创建为a.txt的名字的文件
		
		File file = new File("e:\\a\\b");
        if(!file.exists()) {
            File uploadDir = file.getParentFile();
            if (!uploadDir.isDirectory())
            {
                uploadDir.mkdirs();
                try {
					System.out.println(file.createNewFile());//会创建b的文件
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
	}

}
