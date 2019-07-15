package cn.com.agree.ab.transfer.gui;

import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class TransferGui extends JFrame {

	private static final long serialVersionUID = 1L;
	/* 主窗体里面的若干元素 */
	private JFrame mainForm = new JFrame("文件转换"); // 主窗体
	private JLabel label1 = new JLabel("请选择待转换的文件：");
	private JLabel label2 = new JLabel("请选择存放lfc文件的项目名：");
	public static JTextField sourcefile = new JTextField(); // 选择待转换文件路径的文本域
	public static JTextField projectName = new JTextField(10); // 选择转换后的项目名称
	public static JButton buttonBrowseSource = new JButton("浏览"); // 浏览按钮
	public static JButton buttonTransfer = new JButton("生成.lfc文件"); 
	public static String[] str = { "bcpt转换lfc", "fc转换lfc" };
	public static JComboBox combol = new JComboBox(str);

	public TransferGui() {

		Container container = mainForm.getContentPane();
		/* 设置主窗体属性 */
		mainForm.setSize(400, 350);// 设置主窗体大小
		mainForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// 设置主窗体关闭按钮样式
		mainForm.setLocationRelativeTo(null);// 设置居于屏幕中央
		mainForm.setResizable(false);// 设置窗口不可缩放
		mainForm.setLayout(null);
		mainForm.setVisible(true);// 显示窗口
		/* 设置各元素位置布局 */
		combol.setBounds(30, 10, 160, 50);
		label1.setBounds(30, 70, 300, 30);
		sourcefile.setBounds(30, 110, 260, 30);
		buttonBrowseSource.setBounds(300, 110, 60, 30);
		label2.setBounds(30, 160, 300, 30);
		projectName.setBounds(30, 190, 200, 30);
		buttonTransfer.setBounds(120, 250, 160, 30);
		combol.setBorder(BorderFactory.createTitledBorder("转换方式："));
		/* 为各元素绑定事件监听器 */
		buttonBrowseSource.addActionListener(new BrowseAction()); // 为源文件浏览按钮绑定监听器，点击该按钮调用文件选择窗口
		// 为目标位置浏览按钮绑定监听器，点击该按钮调用文件选择窗口
		buttonTransfer.addActionListener(new TransferAction()); 
		// buttonDecrypt.addActionListener(new DecryptAction()); //
		sourcefile.setEditable(false);// 设置源文件文本域不可手动修改
		// targetfile.setEditable(false);// 设置目标位置文本域不可手动修改
		container.add(combol);
		container.add(label1);
		container.add(label2);
		container.add(sourcefile);
		container.add(projectName);
		container.add(buttonBrowseSource);
		container.add(buttonTransfer);
	}

	public static void main(String[] args) {

		new TransferGui();
	}

}
