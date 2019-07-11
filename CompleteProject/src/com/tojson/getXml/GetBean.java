package com.tojson.getXml;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.tojson.pojo.Component;
import com.tojson.pojo.Csd;
import com.tojson.pojo.DataBasket;
import com.tojson.pojo.End;
import com.tojson.pojo.Endstep;
import com.tojson.pojo.FileDescription;
import com.tojson.pojo.Geometry;
import com.tojson.pojo.InArgs;
import com.tojson.pojo.InternalVars;
import com.tojson.pojo.JsonRootBean;
import com.tojson.pojo.Lfc;
import com.tojson.pojo.OutArgs;
import com.tojson.util.FormatUtil;

import jdk.nashorn.internal.parser.JSONParser;
//��javaBeanת��Ϊjson
public class GetBean {
	public static String Bean(String fileString) throws DocumentException {
		GetXml getXml = new GetXml();
		//���ø�ʽ�������࣬��JavaBean����תΪJSONString���String��ʽ��json���ݽ��и�ʽ��
		FormatUtil formatUtil = new FormatUtil();
		//ʹ��ToJsonString��javaBeanת��Ϊjson��ʽ��String�ַ���
		return formatUtil.formatJson(JSON.toJSONString(getXml.getXml(fileString)));
	}
	public static void main(String[] args) throws DocumentException {
		GetXml getXml = new GetXml();
		FormatUtil formatUtil = new FormatUtil();
		JsonRootBean jsonRootBean2 = getXml.getXml("token.xml");
		System.out.println(formatUtil.formatJson(JSON.toJSONString(jsonRootBean2)));
	}
}
