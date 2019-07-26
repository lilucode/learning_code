package com.tojson.realize;

import java.io.File;

import com.tojson.getXml.GetBean;
import com.tojson.util.CheckName;
import com.tojson.util.GetFile;

import com.tojson.util.GetXmlUtil;
import com.tojson.util.WriteJson;

public class FolderRealize {
	public void Xml2Json(String sourseFolder,String targetFolder,int i) {
		try {
			GetFile getFile = new GetFile();
			File file = new File(sourseFolder);
			//File file = new File("E://repo//XxwGit.github.io//_includes//XmlTest");
			String[] files = getFile.getFilStrings(file);
			GetXmlUtil getXml = new GetXmlUtil();
			String jsonName = getXml.getName(files[i]);
			String target = files[i].replace(sourseFolder, "");
			
			CheckName checkName = new CheckName();
			
			String finalFile = checkName.name(target);

			String formateString = GetBean.Bean(files[i]);
			
			WriteJson setJson = new WriteJson();
			setJson.write(formateString, targetFolder+finalFile);
			System.out.println(jsonName+"转换成功");
			
		} catch (Exception e) {
			// TODO: handle exception
			GetFile getFile = new GetFile();
			File file = new File(sourseFolder);
			String[] files = getFile.getFilStrings(file);
			GetXmlUtil getXml = new GetXmlUtil();
			String jsonName = getXml.getName(files[i]);
			System.out.println(jsonName+"转换失败");
			e.printStackTrace();
		}
		
	}
	
	public int getLength(String sourseFolder) {
		GetFile getFile = new GetFile();
		File file = new File(sourseFolder);
		String[] files = getFile.getFilStrings(file);
		return files.length;
	}
}
