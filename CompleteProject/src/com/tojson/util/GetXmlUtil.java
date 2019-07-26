package com.tojson.util;

import java.io.File;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
//获取xml文件将其转换为String
public class GetXmlUtil {
	//获取xml文件的名字并修改为.json文件
	public String getName(String string) {
		File file = new File(string.trim());
		if (file.getName().contains(".xml")) {
			return file.getName().replace(".xml", ".json");
		}else if (file.getName().contains(".bcpt")) {
			return file.getName().replace(".bcpt", ".json");
		}else {
			return null;
		}
	}
	
	//将xml文件先转换为String的形式
	public String turnDocumentToString(String string) {
		try {
			// 读取 xml 文件
			File fileinput = new File(string);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fileinput);
			
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
