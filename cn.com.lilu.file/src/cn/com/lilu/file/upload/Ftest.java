package cn.com.lilu.file.upload;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class Ftest extends JFrame { // �����࣬�̳�Jframe��
	private JScrollPane scrollPane;
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null; // ����������
	private JTextArea jTextArea = null; // �����ı������
	private JPanel controlPanel = null; // ����������
	private JButton openButton = null; // ������ť����
	private JButton closeButton = null; // ������ť����
	
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
		}
		return jTextArea;
	}
	
	private JPanel getControlPanel() {
		if (controlPanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setVgap(1);
			controlPanel = new JPanel();
			controlPanel.setLayout(flowLayout);
			controlPanel.add(getOpenButton(), null);
			controlPanel.add(getCloseButton(), null);
		}
		return controlPanel;
	}
	
	private JButton getOpenButton() {
		if (openButton == null) {
			openButton = new JButton();
			openButton.setText("д���ļ�"); // �޸İ�ť����ʾ��Ϣ
			openButton
					.addActionListener(new java.awt.event.ActionListener() {
						// ��ť�ĵ����¼�
						public void actionPerformed(ActionEvent e) {
							// �����ļ�����
							File file = new File("word.txt");
							try {
								// ����FileWriter����
								FileWriter out = new FileWriter(file);
								// ��ȡ�ı������ı�
								String s = jTextArea.getText();
								out.write(s); // ����Ϣд������ļ�
								out.close(); // �����ر�
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					});
		}
		return openButton;
	}
	
	private JButton getCloseButton() {
		if (closeButton == null) {
			closeButton = new JButton();
			closeButton.setText("��ȡ�ļ�"); // �޸İ�ť����ʾ��Ϣ
			closeButton
					.addActionListener(new java.awt.event.ActionListener() {
						// ��ť�ĵ����¼�
						public void actionPerformed(ActionEvent e) {
							File file = new File("word.txt"); // �����ļ�����
							try {
								// ����FileReader����
								FileReader in = new FileReader(file);
								char byt[] = new char[1024]; // ����char������
								int len = in.read(byt); // ���ֽڶ�������
								// �����ı������ʾ��Ϣ
								jTextArea.setText(new String(byt, 0, len));
								in.close(); // �ر���
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					});
		}
		return closeButton;
	}
	
	public Ftest() {
		super();
		initialize();
	}
	
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getControlPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	
	public static void main(String[] args) { // ������
		Ftest thisClass = new Ftest(); // �����������
		thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		thisClass.setVisible(true); // ���øô���Ϊ��ʾ״̬
	}
	/**
	 * @return
	 */
	protected JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getJTextArea());
		}
		return scrollPane;
	}
}
