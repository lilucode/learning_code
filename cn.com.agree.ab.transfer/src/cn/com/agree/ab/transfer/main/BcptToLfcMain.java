package cn.com.agree.ab.transfer.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.com.agree.ab.transfer.afa.model.BCModel;
import cn.com.agree.ab.transfer.afa.parser.BcptBeanParse;
import cn.com.agree.ab.transfer.lfc.util.BcptToLfcUtil;
import cn.com.agree.ab.transfer.lfc.util.FormatUtil;
import cn.com.agree.ab.transfer.runtime.lfc.LogicFlowControl;


public class BcptToLfcMain {

	public static String projectName;

	public static void main(String[] args) throws Exception {
//		args=new String[] {"demo-s","./bcpt/"};
//		args=new String[] {"demo-s","D:\\software\\afa\\5.0交易源码\\afa\\functionModule\\businessComponent"};
		
		if (args.length != 2) {
			System.err.println("传入参数不合法，需要传入两个参数，含义分别为：项目名、要转换的文件夹路径");
			return;
		}
		projectName = args[0];

		ArrayList<File> listFiles = new ArrayList<File>();
		File baseFile = new File(args[1]);
		if(!baseFile.exists()) {
			System.err.println(baseFile.getAbsolutePath() + "不存在");
			return;
		}
		getAllFile(baseFile, listFiles);

		File lfcFile = new File(baseFile, "lfc");
		if (!lfcFile.exists()) {
			lfcFile.mkdir();
		}

		int j = 0;
		for (File file : listFiles) {
			String name = file.getName();
			if (name.endsWith(".bcpt")) {
				BcptBeanParse bbp = new BcptBeanParse();
				BCModel bm = bbp.parse(file);
				BcptToLfcUtil blu = new BcptToLfcUtil(file.getAbsolutePath());
				LogicFlowControl lfc = blu.parse(bm);
				String content = JSON.toJSONString(lfc);
				content = FormatUtil.formatJson(content);

				Path relative = baseFile.toPath().relativize(file.getParentFile().toPath());
				File file3 = new File(lfcFile, relative + "/" + bm.getName() + ".lfc");
				if (!file3.exists()) {
					File uploadDir = file3.getParentFile();
					if (!uploadDir.exists()) {
						uploadDir.mkdirs();
					}
					file3.createNewFile();
				}
				try (FileOutputStream fos = new FileOutputStream(file3);
						OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8")) {
					osw.write(content);
					osw.flush();
					j++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("bcpt转换lfc总共生成文件：" + j + "，生成的文件目录为：" + lfcFile.getAbsolutePath());

	}

	public static void getAllFile(File baseFile, List<File> listFile) {
		File[] files = baseFile.listFiles();
		for (File sub : files) {
			if (sub.isDirectory()) {
				getAllFile(sub, listFile);
			} else {
				listFile.add(sub);
			}
		}
	}

}
