package com.tojson.swt;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import com.tojson.realize.FileThread;
import com.tojson.realize.FolderThread;
public class FileChooser {

	protected Shell shell;
	private Text sourseFile;
	private Text targetFile;
	public String sourceFileString;//源文件路径
	public String targetFileString;//文件转换存储路径
	public static void main(String[] args) {
		try {
			FileChooser window = new FileChooser();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	//创建窗口
	protected void createContents() {
		//设置窗口大小和名称
		shell = new Shell();
		shell.setSize(800, 500);
		shell.setText("Xml2Json");
		
		Label sourceLabel = new Label(shell, SWT.NONE);
		sourceLabel.setBounds(139, 127, 54, 20);
		sourceLabel.setSize(80, 55);
		sourceLabel.setText("目标文件夹：");

		sourseFile = new Text(shell, SWT.BORDER);
		sourseFile.setBounds(233, 127, 300, 20);
		
		Button sourseFileButton = new Button(shell, SWT.NONE);
		sourseFileButton.setBounds(550, 127, 95, 20);
		sourseFileButton.setText("选取目标文件夹");
		sourseFileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jfc.showDialog(new JLabel(), "选择文件夹");
				System.out.println(jfc.getSelectedFile());
				File file = jfc.getSelectedFile();
				if (file == null) {
					MessageDialog.openInformation(shell, "提示", "请选择目标文件夹");
				} else {
					sourseFile.setText(file.getAbsolutePath());
					sourceFileString = file.getAbsolutePath();
				}

			}
		});

		Label targetLabel = new Label(shell, SWT.NONE);
		targetLabel.setBounds(139, 181, 54, 20);
		targetLabel.setSize(80, 55);
		targetLabel.setText("输出文件夹：");

		targetFile = new Text(shell, SWT.BORDER);
		targetFile.setBounds(233, 177, 300, 20);
		Button targetFileButton = new Button(shell, SWT.NONE);
		targetFileButton.setBounds(550, 181, 95, 20);
		targetFileButton.setText("选取输出文件夹");
		targetFileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file==null) {
					MessageDialog.openInformation(shell, "提示", "请选择输出文件夹");
				}else {
					targetFile.setText(file.getAbsolutePath());
					targetFileString = file.getAbsolutePath();
				}
				
			}
		});

		Button Begin = new Button(shell, SWT.NONE);
		Begin.setBounds(213, 235, 72, 22);
		Begin.setText("开始转换");
		Begin.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String path = sourceFileString;
				File file = new File(path);
				//对选取的路径判断，文件和文件夹会进行两种不同的操作
				if (file.isDirectory()) {
					System.out.println("准换的目标是文件夹");
					FolderThread jsonThread = new FolderThread(sourceFileString, targetFileString);
					Thread thread = new Thread(jsonThread);
					thread.run();
					MessageDialog.openInformation(shell, "提示", "转换成功");
				}
				if (file.isFile()) {
					System.out.println("准换的目标是文件");
					FileThread jsonFileThread = new FileThread(sourceFileString, targetFileString);
					Thread thread = new Thread(jsonFileThread);
					thread.run();
					MessageDialog.openInformation(shell, "提示", "转换成功");
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			
			}
		});
	}
}