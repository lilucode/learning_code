package cn.com.agree.ab.transfer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import cn.com.agree.ab.transfer.main.BcptToLfcMain;
import cn.com.agree.ab.transfer.main.FcToLfcMain;
import cn.com.agree.ab.transfer.main.XmlToJsonMain;

public class TransferAction implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		if (TransferGui.sourcefile.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请选择待转换文件！");
		} else if (TransferGui.projectName.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入转换后项目名！");
		} else {
			String select = (String) TransferGui.combol.getSelectedItem();
			String sourcePath = TransferGui.sourcefile.getText();
			String projectName = TransferGui.projectName.getText();
			try {
				if (select.equals("bcpt转换lfc")) {
					BcptToLfcMain.main(new String[] { projectName, sourcePath });
				} else if(select.equals("fc转换lfc")){
					FcToLfcMain.main(new String[] { projectName, sourcePath});
				}else {
					XmlToJsonMain.main(new String[] { projectName, sourcePath});
				}
				JOptionPane.showMessageDialog(null, "转换完成！");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
