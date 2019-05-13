package cn.com.li1.file;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * File类描述的是一个文件或文件夹。（文件夹也可以称为目录）
 * 
 * 该类的出现是对文件系统的中的文件以及文件夹进行对象的封装。 结论：File对象也可以表示不存在的文件。
 * 其实代表了一个抽象路径
 * 
 * 构建一个File类的实例并不会在机器上创建一个文件.不管文件是否存在都可以创建任意文件名的File实例,
 * 可以调用File实例的exists方法判断文件或目录是否存在
 * 
 */
public class FileTest {

	
	@Test
	public void test001() {
		File file1=new File("e；/");
		File file2=new File("e；/","a.txt");
		System.out.println(file1);
		System.out.println(file2);
	}
	
	/**
	 * 测试mkdirs与createNewFile应用
	 */
	@Test
	public void test002() {
//		String fileName="e:\\a\\b.txt";//刚开始没有文件夹，创建了正确的文件夹和文件
//		String fileName="e:\\a\\b";//路径不正确，返回拦截
//		String fileName="e:";//路径不正确，返回拦截
		String fileName="e:\\a\\b.txt";
		// 是不是正确的文件路径
        if (fileName.lastIndexOf(File.separatorChar) == -1
                || !fileName.substring(fileName.lastIndexOf(File.separatorChar))
                        .contains("."))
        {
        	System.out.println("返回");
            return;
        }
        File file = new File(fileName);
        // 不存在目录时先创建
        if (!file.exists())
        {
            File uploadDir = file.getParentFile();
            if (!uploadDir.isDirectory())
            {
                uploadDir.mkdirs();
                try {
					file.createNewFile();
					//用流上传时会自动创建文件，不需要再一次创建
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
	}
	
	@Test
	public void test003() {
		
		File file1=new File("e:\\a");
		System.out.println(file1.isDirectory());//不存在为false
		File file2=new File("e:\\a.txt");
		System.out.println(file2.isFile());//不存在为false
		System.out.println(file2.mkdirs());//会创建为a.txt的名字的文件
		
		File file = new File("e:\\a\\b");
        if(!file.exists()) {
            File uploadDir = file.getParentFile();
            if (!uploadDir.isDirectory())
            {
                uploadDir.mkdirs();
                try {
					System.out.println(file.createNewFile());//会创建b的文件
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
	}
	
	/**
	 * 测试正确文件路径：正则表达式
	 */
	@Test
	public void test004() {
		String source = "z:\\java\\kl\\$kls\\";
//		String source = args[0]+File.separator;
//		String targer = args[1]+File.separator;
		System.out.println(source);
//		System.out.println(targer);
		if(source.matches("^[A-z]:\\\\(.+?\\\\)*$") && source.matches("^[A-z]:\\\\(.+?\\\\)*$")){
			System.out.println("磁盘路径正确。。。");
		}
	}
	
	/**
	 * 测试目录还是文件
	 */
	@Test
	public void test005() {
		
	}

}
