package cn.com.agree.ab.transfer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import cn.com.agree.ab.transfer.main.BcptToLfcMain;
import cn.com.agree.ab.transfer.main.FcToLfcMain;

public class TransferAction implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		if (TransferGui.sourcefile.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请选择待转换文件！");
		} else if (TransferGui.projectName.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入转换后项目名！");
		} else {
			String select = (String) TransferGui.combol.getSelectedItem();
			String sourcepath = TransferGui.sourcefile.getText();
			String projectName = TransferGui.projectName.getText();
			try {
				if (select.equals("bcpt转换lfc")) {
					BcptToLfcMain
							.main(new String[] { projectName, sourcepath });
				} else {
					FcToLfcMain.main(new String[] { projectName, sourcepath });
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
