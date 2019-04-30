package cn.com.li1.file;

import java.io.File;
import java.io.IOException;

public class TestCreateFile {

	public static void main(String[] args) {

//		String fileName="e:\\a\\b.txt";//刚开始没有文件夹，创建了正确的文件夹和文件
//		String fileName="e:\\a\\b";//路径不正确，返回拦截
//		String fileName="e:";//路径不正确，返回拦截
		String fileName="e:\\a\\b.txt";
		// 是不是正确的文件路径
        if (fileName.lastIndexOf(File.separatorChar) == -1
                || !fileName.substring(fileName.lastIndexOf(File.separatorChar))
                        .contains("."))
        {
        	System.out.println("返回");
            return;
        }
        File file = new File(fileName);
        // 不存在目录时先创建
        if (!file.exists())
        {
            File uploadDir = file.getParentFile();
            if (!uploadDir.isDirectory())
            {
                uploadDir.mkdirs();
                try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
	}

}
