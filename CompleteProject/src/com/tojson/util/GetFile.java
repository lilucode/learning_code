package com.tojson.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

//获取文件夹中所有的.xml文件
public class GetFile {
	//文件下所有文件的
	public String[] getFilStrings(File file) {
		ArrayList<String> listFileName = new ArrayList<String>();
		getAllFileName(file.toString(),listFileName);
		StringBuilder sBuilder = new StringBuilder();
		for (String fileString : listFileName) {
			if (fileString.contains(".xml")||fileString.contains(".bcpt")) {
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
			 if(a.isDirectory()){//如果文件夹下有子文件夹，获取子文件夹下的所有文件全路径。
	                getAllFileName(a.getAbsolutePath(),listFileName);
	            }
		}
	}
}
