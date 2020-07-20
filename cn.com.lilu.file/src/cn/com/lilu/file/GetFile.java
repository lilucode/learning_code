package cn.com.lilu.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GetFile {

	public static void main(String[] args) {

		File file = new File("C:\\Users\\Administrator\\Desktop\\plugins\\test.txt");
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 1; i++) {
			sb.append(new String(filecontent));
		}
	}
}
