package cn.com.agree.ab.transfer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;

/**
 * 获取选中的文件路径和存放目录路径
 */
public class BrowseAction implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(TransferGui.buttonBrowseSource)) {
			JFileChooser fcDlg = new JFileChooser();
			fcDlg.setDialogTitle("请选择待转换的文件...");
			fcDlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fcDlg.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String filepath = fcDlg.getSelectedFile().getPath();
				TransferGui.sourcefile.setText(filepath);
			}
		}
	}
}