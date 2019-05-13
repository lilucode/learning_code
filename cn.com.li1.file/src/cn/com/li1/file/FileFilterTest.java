package cn.com.li1.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import org.junit.Test;


public class FileFilterTest {

	@Test
	public void test001() {
		File f = null;
		File[] paths;

		try {
			f = new File("E:\\testdownload");

			// create new filename filter
			FilenameFilter fileNameFilter = new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if (name.lastIndexOf('.') > 0) {
						// get last index for '.' char
						int lastIndex = name.lastIndexOf('.');
						// get extension
						String str = name.substring(lastIndex);
						if (str.equals(".txt")) {
							return true;
						}
					}
					return false;
				}
			};
			// returns pathnames for files and directory
			paths = f.listFiles(fileNameFilter);

			for (File path : paths) {
				System.out.println(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test002() {
		//创建File对象  
        File file = new File("E:\\git_me\\learning_code\\cn.com.li1.file\\src\\cn\\com\\li1\\file");  
        //获取该目录下的所有文件  
        String[] files = file.list();  
          
        for (String f : files){  
            System.out.println(f);  
        }  
          
        //listFiles是获取该目录下所有文件和目录的绝对路径  
        File[] fs = file.listFiles();  
        for (File f : fs){  
            System.out.println(f);  
        }  
        
        File[] ff = file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return false;
			}
        	
        });
	}
	
	
	public void test003() {
		File file = new File("E:\\git_me\\learning_code\\cn.com.li1.file\\src\\cn\\com\\li1\\file\\uploadFile");
		File[] fs = file.listFiles();
		Arrays.sort(fs, new CompratorByLastModified());
		for (int i = 0; i < fs.length; i++) {
			System.out.println(fs[i].getName() + " " + new Date(fs[i].lastModified()).toLocaleString());
		}
	}

	static class CompratorByLastModified implements Comparator<File> {
		public int compare(File f1, File f2) {
			long diff = f1.lastModified() - f2.lastModified();
			if (diff > 0)
				return -1;// 倒序正序控制
			else if (diff == 0)
				return 0;
			else
				return 1;// 倒序正序控制
		}

		public boolean equals(Object obj) {
			return true;
		}
	}
	

}
