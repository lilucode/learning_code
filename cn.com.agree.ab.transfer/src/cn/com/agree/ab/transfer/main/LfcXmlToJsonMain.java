package cn.com.agree.ab.transfer.main;

import java.io.File;

import com.alibaba.fastjson.JSON;

import cn.com.agree.ab.transfer.afa.parser.LfcXmlParser;
import cn.com.agree.ab.transfer.afa.parser.XmlParseException;
import cn.com.agree.ab.transfer.runtime.lfc.LogicFlowControl;
import cn.com.agree.ab.transfer.util.FormatUtil;

/**
 * 
 * @author Administrator
 * 转换入口
 * 交易：xml格式转换为5.0格式
 *
 */
public class LfcXmlToJsonMain {

	public static void main(String[] args) throws XmlParseException {

//		File lfcFile=new File("E:\\ABIDE\\ABIDE-DaLian-master-20170609-1727\\workspace\\token\\trade\\token\\Token.lfc");
		File lfcFile=new File("E:\\ABIDE\\ABIDE-DaLian-master-20170609-1727\\workspacepufa\\server\\mpsp\\business\\OnResume.lfc");
		if(lfcFile!=null&lfcFile.getName().endsWith(".lfc")) {
			LfcXmlParser lfcXmlParser=new LfcXmlParser();
			LogicFlowControl lfc=lfcXmlParser.parse(lfcFile);
			String content = JSON.toJSONString(lfc);
			content = FormatUtil.formatJson(content);
			System.out.println(content);
		}
		
	}

}
