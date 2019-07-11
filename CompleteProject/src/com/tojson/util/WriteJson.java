package com.tojson.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

//生成json文件并保存到对应的
public class WriteJson {
	public void write(String string, String fileString) {
		try {
			File file = new File(fileString);
			File fileParent = file.getParentFile(); 
			if(!fileParent.exists()){  
			    fileParent.mkdirs();  
			}  
			file.createNewFile();
			BufferedWriter writer = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (fileString,true),"UTF-8"));
			writer.write(string);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
