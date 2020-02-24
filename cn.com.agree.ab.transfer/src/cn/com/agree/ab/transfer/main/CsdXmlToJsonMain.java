package cn.com.agree.ab.transfer.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.alibaba.fastjson.JSON;

import cn.com.agree.ab.transfer.afa.model.Constant;
import cn.com.agree.ab.transfer.afa.parser.CsdXmlParser;
import cn.com.agree.ab.transfer.afa.parser.XmlParseException;
import cn.com.agree.ab.transfer.csd.CloudServiceDefinition;
import cn.com.agree.ab.transfer.util.FormatUtil;

public class CsdXmlToJsonMain {

	public static void main(String[] args) throws XmlParseException {

		ArrayList<File> listFiles = new ArrayList<File>();
		Constant.ProjectName = "demoTest";
		//到工程这一层目录
		File baseFile = new File("E:\\ABIDE\\ABIDE-DaLian-master-20170609-1727\\workspacepufa\\server\\mpsp");
		if(!baseFile.exists()) {
			JOptionPane.showMessageDialog(null,baseFile.getAbsolutePath() + "不存在");
			return;
		}
		//获取路径下所有文件
		getAllFile(baseFile, listFiles);

		//存放转换后的文件
		File jsonFile = new File(baseFile.getParent(), "json");
		if (!jsonFile.exists()) {
			jsonFile.mkdir();
		}
		for (File file : listFiles) {
			if (file.getName().endsWith(".csd")) {
				String filePath=file.getAbsolutePath();
				CsdXmlParser csdXmlParser = new CsdXmlParser();
				CloudServiceDefinition csd = csdXmlParser.parse(file);
				String content = JSON.toJSONString(csd);
				content = FormatUtil.formatJson(content);
				System.out.println(content);
			}
		}
		
		
	}
	
	public static void getAllFile(File baseFile, List<File> listFile) {
		File[] files = baseFile.listFiles();
		if (files == null) {
			return;
		}
		for (File sub : files) {
			if (sub.isDirectory()) {
				getAllFile(sub, listFile);
			} else {
				listFile.add(sub);
			}
		}
	}


}
