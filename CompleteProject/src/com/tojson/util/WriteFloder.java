package com.tojson.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
//将文件写入文件夹
public class WriteFloder {
	public void main(String fileString,String foldString) throws Exception {
		//获取目标文件夹
		File file = new File(fileString);//文件原目录
		File destDir = new File(foldString);//文件夹目录
		if(!destDir.exists()) {
            destDir.mkdir();
        }
		//
		System.out.println(file.getName());
		FileInputStream fis = new FileInputStream(file);
		String fileName = file.getName();
		FileOutputStream fos = new FileOutputStream(new File(destDir, fileName));
		/*第五步：将文件重新写入目标文件夹*/
        copy(fis, fos);
        fis.close();
        fos.close();
	}
	public static void copy(InputStream in, OutputStream out) throws Exception {
        byte[] buf = new byte[1024];
        int len = 0;
        /*读取文件内容并写入文件字节流中*/
        while((len = in.read(buf))!=-1) {
            out.write(buf, 0, len);
        }
    }
}
