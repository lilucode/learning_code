package cn.com.agree.ab.transfer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import cn.com.agree.ab.transfer.main.BcptToLfcMain;
import cn.com.agree.ab.transfer.main.FcToLfcMain;

public class TransferAction implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		if (TransferGui.sourcefile.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请选择待转换文件！");
		} else if (TransferGui.targetfile.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请选择转换后文件存放目录！");
		} else {
			String select = (String) TransferGui.combol.getSelectedItem();
			String sourcepath = TransferGui.sourcefile.getText();
			String projectName = TransferGui.targetfile.getText();
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
