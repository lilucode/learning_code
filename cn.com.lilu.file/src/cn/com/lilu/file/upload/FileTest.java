package cn.com.lilu.file.upload;
import java.io.*;

public class FileTest { // ������
	public static void main(String[] args) { // ������
		File file = new File("word.txt"); // �����ļ�����
		try { // ��׽�쳣
			// ����FileOutputStream����
			FileOutputStream out = new FileOutputStream(file);
			// ����byte������
			byte buy[] = "����һֻСë¿���Ҵ���Ҳ���".getBytes();
			out.write(buy); // ����������Ϣд�뵽�ļ���
			out.close(); // �����ر�
		} catch (Exception e) { // catch��䴦���쳣��Ϣ
			e.printStackTrace(); // ����쳣��Ϣ
		}
		try {
			// ����FileInputStream�����
			FileInputStream in = new FileInputStream(file);
			byte byt[] = new byte[1024]; // ����byte����
			int len = in.read(byt); // ���ļ��ж�ȡ��Ϣ
			// ���ļ�����Ϣ���
			System.out.println("�ļ��е���Ϣ�ǣ�" + new String(byt, 0, len));
			in.close(); // �ر���
		} catch (Exception e) {
			e.printStackTrace(); // ����쳣��Ϣ
		}
	}
}
