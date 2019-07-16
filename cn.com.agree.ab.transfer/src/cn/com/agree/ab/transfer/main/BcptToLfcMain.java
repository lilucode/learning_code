package cn.com.agree.ab.transfer.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.alibaba.fastjson.JSON;

import cn.com.agree.ab.transfer.afa.model.BCModel;
import cn.com.agree.ab.transfer.afa.parser.BcptParse;
import cn.com.agree.ab.transfer.lfc.util.BcptToLfcUtil;
import cn.com.agree.ab.transfer.lfc.util.FormatUtil;
import cn.com.agree.ab.transfer.runtime.lfc.LogicFlowControl;


public class BcptToLfcMain {

	public static String projectName;
	
	public static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws Exception {
//		args=new String[] {"demo-s","./bcpt/"};
		
		projectName = args[0];

		ArrayList<File> listFiles = new ArrayList<File>();
		File baseFile = new File(args[1]);
		if(!baseFile.exists()) {
			JOptionPane.showMessageDialog(null,baseFile.getAbsolutePath() + "不存在");
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
				BcptParse bbp = new BcptParse();
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
//		System.out.println("bcpt转换lfc总共生成文件：" + j + "，生成的文件目录为：" + lfcFile.getAbsolutePath());
		JOptionPane.showMessageDialog(null, sb+"\nbcpt转换lfc总共生成文件：" + j + "，生成的文件目录为：" + lfcFile.getAbsolutePath());
		sb.delete(0, sb.length());

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
