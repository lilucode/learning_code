package cn.com.lilu.file;

import java.io.File;
import java.io.IOException;

public class FileTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file=new File("word.txt");
		if(file.exists()) {
			file.delete();
		}else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

	}

}
