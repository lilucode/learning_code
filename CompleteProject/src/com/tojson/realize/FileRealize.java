package com.tojson.realize;

import org.dom4j.DocumentException;
import com.tojson.getXml.GetBean;
import com.tojson.util.GetXmlUtil;
import com.tojson.util.WriteJson;
//xml格式转json格式
public class FileRealize {
	public void XmlToJson(String fileName, String targetFolder) throws DocumentException {
		try {
			GetXmlUtil getXml = new GetXmlUtil();
			//获取文件的名称
			String jsonName = getXml.getName(fileName);
			//获取格式化之后的json文件
			String formateString = GetBean.Bean(fileName);
			//将json文件写入文件中保存到目标文件夹中
			WriteJson setJson = new WriteJson();
			setJson.write(formateString, targetFolder + "//" + jsonName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}
