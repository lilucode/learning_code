package cn.com.agree.ab.transfer.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;

import cn.com.agree.ab.transfer.afa.model.Constant;
import cn.com.agree.ab.transfer.afa.parser.AdeXmlParser;
import cn.com.agree.ab.transfer.afa.parser.CsdXmlParser;
import cn.com.agree.ab.transfer.afa.parser.LfcXmlParser;
import cn.com.agree.ab.transfer.afa.parser.XmXmlParser;
import cn.com.agree.ab.transfer.afa.parser.XmlParseException;
import cn.com.agree.ab.transfer.csd.Ade;
import cn.com.agree.ab.transfer.csd.CloudServiceDefinition;
import cn.com.agree.ab.transfer.runtime.lfc.LogicFlowControl;
import cn.com.agree.ab.transfer.runtime.lfc.Xm;
import cn.com.agree.ab.transfer.util.FormatUtil;

public class XmlToJsonMain {

	private static final Log logger = LogFactory.getLog(XmlToJsonMain.class);
	
	public static void main(String[] args) throws XmlParseException, IOException {

		//清空数据
		Constant.XmList = new ArrayList<String>();
		Constant.ArgAdeList = new HashSet<String>();
		Constant.LfcList = new ArrayList<String>();
		Constant.RepeatLfcList = new HashSet<String>();
		
		ArrayList<File> listFiles = new ArrayList<File>();
//		Constant.ProjectName = "serverXml";
		//项目名
		Constant.ProjectName = args[0];
		// 到工程这一层目录
//		String dir = "E:\\ABIDE\\ABIDE-DaLian-master-20170609-1727\\workspacepufa\\server";
		//转换文件路径
		String dir = args[1];
		Constant.TransferPath=args[1];
		File baseFile = new File(dir);
		if (!baseFile.exists()) {
			JOptionPane.showMessageDialog(null, baseFile.getAbsolutePath() + "不存在");
			return;
		}
		// 获取路径下所有文件
		getAllFile(baseFile, listFiles);

		// 存放转换后的文件
		File jsonFile = new File(baseFile.getParent(), "json");
		if (!jsonFile.exists()) {
			jsonFile.mkdir();
		}
		for (File file : listFiles) {
			// 转换csd
			if (file.getName().endsWith(".csd")) {
				try {
					CsdXmlParser csdXmlParser = new CsdXmlParser();
					CloudServiceDefinition csd = csdXmlParser.parse(file);
					String content = JSON.toJSONString(csd);
					createFile(file, jsonFile, dir, content,"");
				} catch (Exception e) {
					System.out.println(file.getName() + "转换出错");
					logger.error(e);
				}
			} else if (file.getName().endsWith(".ade")) {
				try {
					AdeXmlParser adeXmlParser = new AdeXmlParser();
					Ade ade = adeXmlParser.parse(file);
					String content = JSON.toJSONString(ade);
					createFile(file, jsonFile, dir, content,"");
				} catch (Exception e) {
					System.out.println(file.getName() + "转换出错");
				}
			}
		}
		
		//转换lfc文件
		for (File file : listFiles) {
			if (file.getName().endsWith(".lfc")) {
				String path = file.getAbsolutePath().replace(dir, "");
				path = path.replaceAll("\\\\", "/");
				try {
					LfcXmlParser lfcXmlParser = new LfcXmlParser();
					LogicFlowControl lfc = lfcXmlParser.parse(file);
					String content = JSON.toJSONString(lfc);
					if(Constant.LfcList.contains(path)) {
						content=content.replaceAll("inArgMap", "request");
						content=content.replaceAll("outArgMap", "response");
					}
					createFile(file, jsonFile, dir, content,"");
				} catch (Exception e) {
					System.out.println(file.getName() + "转换出错");
				}
			}
		}

		for (String lfcPath : Constant.RepeatLfcList) {
			System.out.println(lfcPath + "inArgMap()或outArgMap()引用因重复ade没有转换，请手动转换");
		}
		
		for (File file : listFiles) {
			// 转换xm文件
			if (file.getName().endsWith(".xm")) {
				String path = file.getAbsolutePath().replace(dir, "");
				path = path.replaceAll("\\\\", "/");
				if (!Constant.XmList.contains(path)) {
					try {
						XmXmlParser xmXmlParser = new XmXmlParser();
						Xm resXm = xmXmlParser.parse(file);
						String content = JSON.toJSONString(resXm);
						createFile(file, jsonFile, dir, content, "xm");
					} catch (Exception e) {
						System.out.println(file.getName() + "转换出错");
						logger.error(e);
					}
				}
			}
		}
		
		for (String adeName : Constant.ArgAdeList) {
			// 生成Args参数转换的ade文件
			Ade a = new Ade();
			String content = JSON.toJSONString(a);
			File file = new File(jsonFile, "/ade/" + adeName + ".ade");
			if (!file.exists()) {
				File uploadDir = file.getParentFile();
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}
				file.createNewFile();
			}
			try (FileOutputStream fos = new FileOutputStream(file);
					OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8")) {
				osw.write(content);
				osw.flush();
			} catch (IOException e) {
				e.printStackTrace();
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

	public static void createFile(File file, File jsonFile, String dir, String content,String targetDir) throws IOException {
		content = FormatUtil.formatJson(content);
		String filePath = file.getAbsolutePath();
		String newDir = filePath.replace(dir, targetDir);
		File targetFile = new File(jsonFile.getAbsoluteFile(), newDir);
		if (!targetFile.exists()) {
			File uploadDir = targetFile.getParentFile();
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}
			targetFile.createNewFile();
		}
		try (FileOutputStream fos = new FileOutputStream(targetFile);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8")) {
			osw.write(content);
			osw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
