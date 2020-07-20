package cn.com.lilu.file.upload;
import java.io.*;

public class Student { // ������
	public static void main(String args[]) { // ������
		// �����ַ�������
		String content[] = { "�þò���", "�������", "����ϵ" };
		File file = new File("word.txt"); 
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bufw = new BufferedWriter(fw); 
			for (int k = 0; k < content.length; k++) { 
				bufw.write(content[k]);
				bufw.newLine(); 
			}
			bufw.close();
			fw.close(); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
		try {
			FileReader fr = new FileReader(file); 
			BufferedReader bufr = new BufferedReader(fr);
			String s = null; 
			int i = 0; 
			while ((s = bufr.readLine()) != null) { 
				i++; 
				System.out.println("��" + i + "��:" + s); 
			}
			bufr.close(); 
			fr.close();
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
}
