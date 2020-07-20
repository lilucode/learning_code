package cn.com.lilu.file.upload;
import java.io.*;

public class Example_01 { // ������
	public static void main(String[] args) { // ������
		try {
			// ����FileOutputStream����
			FileOutputStream fs = new FileOutputStream("word.txt");
			// ����DataOutputStream����
			DataOutputStream ds = new DataOutputStream(fs);
			ds.writeUTF("ʹ��writeUFT()����д������;"); // д������ļ�����
			ds.writeChars("ʹ��writeChars()����д������;");
			ds.writeBytes("ʹ��writeBytes()����д������.");
			ds.close(); // �����ر�
			// ����FileInputStream����
			FileInputStream fis = new FileInputStream("word.txt");
			// ����DataInputStream����
			DataInputStream dis = new DataInputStream(fis);
			System.out.print(dis.readUTF()); // ���ļ��������
		} catch (Exception e) {
			e.printStackTrace(); // ����쳣��Ϣ
		}
	}
}
