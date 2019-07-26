package com.tojson.realize;

import org.dom4j.DocumentException;

import com.tojson.realize.FileRealize;
//使用线程进行转换操作的控制
public class FileThread implements Runnable{
	private String sourseFile;
	private String targetFolder;
	public FileThread(String sourseFile,String targetFolder) {
		// TODO Auto-generated constructor stub
		this.sourseFile = sourseFile;
		this.targetFolder = targetFolder;
	}
	@Override
	public void run() {
		FileRealize fileRealize = new FileRealize();
		try {
			fileRealize.XmlToJson(sourseFile, targetFolder);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
