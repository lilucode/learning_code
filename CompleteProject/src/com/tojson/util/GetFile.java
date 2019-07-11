package com.tojson.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

//��ȡ�ļ��������е�.xml�ļ�
public class GetFile {
	//�ļ��������ļ���
	public String[] getFilStrings(File file) {
		ArrayList<String> listFileName = new ArrayList<String>();
		getAllFileName(file.toString(),listFileName);
		StringBuilder sBuilder = new StringBuilder();
		for (String fileString : listFileName) {
			if (fileString.contains(".xml")||fileString.contains(".lfc")) {
				sBuilder.append(fileString+",");
			}
		}
		String[] arr=new String(sBuilder).split(",");
		return arr;
	}
	public static void getAllFileName(String path, ArrayList<String> listFileName) {
		File file = new File(path);
		File[] files = file.listFiles();
		String[] names = file.list();
		if (names != null) {
			String[] completNames = new String[names.length];
			for (int i = 0; i < names.length; i++) {
				completNames[i] = path + "\\"+names[i];
			}
			listFileName.addAll(Arrays.asList(completNames));
		}
		for (File a : files) {
			 if(a.isDirectory()){//����ļ����������ļ��У���ȡ���ļ����µ������ļ�ȫ·����
	                getAllFileName(a.getAbsolutePath(),listFileName);
	            }
		}
	}
}
