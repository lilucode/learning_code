package com.tojson.realize;

import com.tojson.realize.FolderRealize;
//使用线程逐个执行
public class FolderThread implements Runnable {
	private String sourseFolder;
	private String targetFolder;

	public FolderThread(String sourseFolder, String targetFolder) {
		// TODO Auto-generated constructor stub
		this.sourseFolder = sourseFolder;
		this.targetFolder = targetFolder;
	}

	@Override
	public void run() {
		FolderRealize tRealizeClass = new FolderRealize();
		int length = tRealizeClass.getLength(sourseFolder); // TODO Auto-generated method stub
		for (int i = 0; i < length; i++) {
			tRealizeClass.Xml2Json(sourseFolder, targetFolder, i);
		}
	}
	
	public static void main(String[] args) {
		
	}
}
