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
//将javaBean转换为json
public class GetBean {
	public static String Bean(String fileString) throws DocumentException {
		GetXml getXml = new GetXml();
		//调用格式化工具类，将JavaBean对象转为JSONString后对String格式的json数据进行格式化
		FormatUtil formatUtil = new FormatUtil();
		//使用ToJsonString将javaBean转换为json格式的String字符串
		String string = JSON.toJSONString(getXml.getXml(fileString));
		String string2 = string.replace("[\r\n" + 
				"			{\r\n" + 
				"				\r\n" + 
				"			}\r\n" + 
				"		]", "[]");
		return formatUtil.formatJson(string2);
	}
}
