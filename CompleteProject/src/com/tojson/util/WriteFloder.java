package com.tojson.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
//���ļ�д���ļ���
public class WriteFloder {
	public void main(String fileString,String foldString) throws Exception {
		//��ȡĿ���ļ���
		File file = new File(fileString);//�ļ�ԭĿ¼
		File destDir = new File(foldString);//�ļ���Ŀ¼
		if(!destDir.exists()) {
            destDir.mkdir();
        }
		//
		System.out.println(file.getName());
		FileInputStream fis = new FileInputStream(file);
		String fileName = file.getName();
		FileOutputStream fos = new FileOutputStream(new File(destDir, fileName));
		/*���岽�����ļ�����д��Ŀ���ļ���*/
        copy(fis, fos);
        fis.close();
        fos.close();
	}
	public static void copy(InputStream in, OutputStream out) throws Exception {
        byte[] buf = new byte[1024];
        int len = 0;
        /*��ȡ�ļ����ݲ�д���ļ��ֽ�����*/
        while((len = in.read(buf))!=-1) {
            out.write(buf, 0, len);
        }
    }
}
