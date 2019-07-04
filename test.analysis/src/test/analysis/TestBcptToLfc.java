package test.analysis;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import com.alibaba.fastjson.JSON;

import cn.com.agree.afa.compiler.model.BCModel;
import cn.com.agree.afa.compiler.parser.ide3.BcptParser;

public class TestBcptToLfc {

	public static void main(String[] args) throws Exception {

		ArrayList<String> listFileName = new ArrayList<String>();
		getAllFileName("./bcpt/", listFileName);

		int i = 0;
		int j = 0;
		for (String name : listFileName) {
			File file = new File(name);
			if (name.contains(".bcpt")) {

				i++;
//				System.out.println(name);
				BcptParser bp = new BcptParser();
				BCModel bm = bp.parse(file);
				String content = JSON.toJSONString(bp.getLfc());
				// System.out.println(content);
				content = FormatUtil.formatJson(content);

				name = name.substring(0, name.lastIndexOf("\\")).replaceAll("bcpt", "lfc") + "\\"+bp.componentName + ".lfc";
				File file3 = new File(name);
				if (!file3.exists()) {
					File uploadDir = file3.getParentFile();
					if (!uploadDir.isDirectory()) {
						uploadDir.mkdirs();
						file3.createNewFile();
					}
				}
				FileOutputStream fop = new FileOutputStream(file3);
				fop.write(content.getBytes());
				j++;
				fop.flush();
				fop.close();
			}
		}
		System.out.println("总共有bcpt文件：" + i);
		System.out.println("成功生成lfc文件：" + j);

	}

	public static void getAllFileName(String path, ArrayList<String> listFileName) {
		File file = new File(path);
		File[] files = file.listFiles();
		String[] names = file.list();
		if (names != null) {
			String[] completNames = new String[names.length];
			for (int i = 0; i < names.length; i++) {
				completNames[i] = path + names[i];
			}
			listFileName.addAll(Arrays.asList(completNames));
		}
		for (File a : files) {
			if (a.isDirectory()) {// 如果文件夹下有子文件夹，获取子文件夹下的所有文件全路径。
				getAllFileName(a.getAbsolutePath() + "\\", listFileName);
			}
		}
	}

}
