package com.tojson.realize;

import org.dom4j.DocumentException;
import com.tojson.getXml.GetBean;
import com.tojson.util.GetXmlUtil;
import com.tojson.util.WriteJson;
//xml��ʽתjson��ʽ
public class FileRealize {
	public void XmlToJson(String fileName, String targetFolder) throws DocumentException {
		try {
			GetXmlUtil getXml = new GetXmlUtil();
			//��ȡ�ļ�������
			String jsonName = getXml.getName(fileName);
			//��ȡ��ʽ��֮���json�ļ�
			String formateString = GetBean.Bean(fileName);
			//��json�ļ�д���ļ��б��浽Ŀ���ļ�����
			WriteJson setJson = new WriteJson();
			setJson.write(formateString, targetFolder + "//" + jsonName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}
