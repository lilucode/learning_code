package test.analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * 删除反编译源码中的注释
 * 
 * @author lisijia lisj@agree.com.cn
 * @date 2019年4月18日
 *
 */
public class DelComment {
	private static final String CHARSET_NAME = "UTF-8";

	public static void main(String[] args) throws IOException {
		String dirStr = "F:\\workspace\\2013B_GIT_4_0_1\\2013B_GIT_4_0_1\\test.analysis\\src\\cn\\com\\agree\\afa\\compiler";
		delComment(new File(dirStr));
		
		System.out.println("清理完成：");
	}

	private static void delComment(File file) throws IOException {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				delComment(f);
			}
		} else {
			delFileComment(file);
		}
	}

	private static void delFileComment(File file) throws IOException {
		File tmpFile = new File(file.getParentFile(), file.getName() + ".tmp");
		if(tmpFile.exists()) {
			tmpFile.delete();
		}
		boolean comment = false;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), CHARSET_NAME));
				BufferedWriter bw = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(tmpFile), CHARSET_NAME))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				/// * */
				if (comment) {
					if (line.contains("*/")) {
						// 跨行注释结束
						comment = false;
						continue;
					} else {
						// 跨行注释继续
						continue;
					}

				}

				if (line.contains("/*")) {
					if (line.contains("*/")) {
						// 同行注释，截取即可
						line = line.substring(line.indexOf("*/") + 3);
					} else {
						// 跨行注释开始
						comment = true;
						continue;
					}
				}
				bw.write(line + "\r\n");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		file.delete();
		boolean result = tmpFile.renameTo(file);
		System.out.println("清理完成：" + result + " " + file.getAbsolutePath());
	}
}
