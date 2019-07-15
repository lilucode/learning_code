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

import cn.com.agree.ab.transfer.afa.model.FCModel;
import cn.com.agree.ab.transfer.afa.parser.FcParser;
import cn.com.agree.ab.transfer.afa.parser.XmlParseException;
import cn.com.agree.ab.transfer.lfc.util.FcToLfcUtil;
import cn.com.agree.ab.transfer.lfc.util.FormatUtil;
import cn.com.agree.ab.transfer.runtime.lfc.LogicFlowControl;

public class FcToLfcMain {

	public static String projectName;

	public static void main(String[] args) throws XmlParseException, IOException {
//		args = new String[] { "demo-s", "./fc/" };
		
		projectName = args[0];
		ArrayList<File> listFiles = new ArrayList<File>();
		File baseFile = new File(args[1]);
		if (!baseFile.exists()) {
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
			if (name.endsWith(".fc")) {
				Path relative = baseFile.toPath().relativize(file.getParentFile().getParentFile().toPath());
				String fileName = relative.toString();
				String dir = fileName.substring(fileName.indexOf("\\") + 1);
				dir = dir.substring(0, dir.indexOf("\\"));
				fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
				File newFile = new File(lfcFile, dir + "/" + fileName + ".lfc");

				FcParser fc = new FcParser();
				FCModel fcModel = fc.parse(file);
				FcToLfcUtil ftl = new FcToLfcUtil(newFile.getParentFile().getAbsolutePath());
				LogicFlowControl lfc = ftl.parse(fcModel);
				String content = JSON.toJSONString(lfc);
				content = FormatUtil.formatJson(content);
				if (!newFile.exists()) {
					File uploadDir = newFile.getParentFile();
					if (!uploadDir.exists()) {
						uploadDir.mkdirs();
					}
					newFile.createNewFile();
				}
				try (FileOutputStream fos = new FileOutputStream(newFile);
						OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8")) {
					osw.write(content);
					osw.flush();
					j++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
//		System.out.println("fc转换lfc总共生成文件：" + j + "，生成的文件目录为：" + lfcFile.getAbsolutePath());
		JOptionPane.showMessageDialog(null, "fc转换lfc总共生成文件：" + j + "，生成的文件目录为：" + lfcFile.getAbsolutePath());
	}

	public static void getAllFile(File baseFile, List<File> listFile) {
		File[] files = baseFile.listFiles();
		if (files == null) {
			return;
		}
		for (File sub : files) {
			if (sub.isDirectory() && !sub.getName().startsWith("_")) {
				getAllFile(sub, listFile);
			} else {
				listFile.add(sub);
			}
		}
	}

}
