package test.analysis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.com.agree.ab.a5.runtime.lfc.LogicFlowControl;
import cn.com.agree.afa.compiler.model.BCModel;
import cn.com.agree.afa.compiler.parser.ide3.BcptBeanParse;

public class BcptToLfcMain {

	public static String projectName;

	public static void main(String[] args) throws Exception {
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
				try (FileOutputStream fop = new FileOutputStream(file3);) {
					fop.write(content.getBytes());
					fop.flush();
					j++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("总共生成lfc文件：" + j + "，生成的文件目录为：" + lfcFile.getAbsolutePath());

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
