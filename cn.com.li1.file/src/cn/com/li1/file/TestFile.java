package cn.com.li1.file;

import java.io.File;
import java.io.IOException;

/**
 * File类描述的是一个文件或文件夹。（文件夹也可以称为目录）
 * 
 * 该类的出现是对文件系统的中的文件以及文件夹进行对象的封装。 结论：File对象也可以表示不存在的文件。其实代表了一个抽象路径
 * 
 * 构建一个File类的实例并不会在机器上创建一个文件.不管文件是否存在都可以创建任意文件名的File实例,
 * 可以调用File实例的exists方法判断文件或目录是否存在
 * 
 */
public class TestFile {

	public static void main(String[] args) {

		File file = new File("c:\\a.txt");

		// 检验指定路径下是否存在指定的目录或者文件.
		System.out.println(file.exists());
		// File对象是否是目录，目录不存在也是false
		System.out.println(file.isDirectory());
		// 对象是否是文件
		System.out.println(file.isFile());
		initFile();
		// 路径分割符
		System.out.println(File.separatorChar);

		File filePath1 = new File("E:\\ABIDE\\demo-s\\servers\\test.csd");// 绝对路径
		File filePath2 = new File("src\\a.txt");// 相对路径
		System.out.println(file.getAbsolutePath());
		System.out.println(filePath1.getAbsolutePath());
		System.out.println(filePath2.getAbsolutePath());

		// 默认的相对路径
		System.out.println("默认的相对路径:" + new File(".").getAbsolutePath());

		createFile();
		deleteFile();
		checkFile();
		getFile();
	}

	/**
	 * 1:创建File对象需要导包, import java.io.File
	 * 
	 * 2:File对象没有无参数构造.创建对象需要传参.
	 * 
	 * new File(String pathname); 通过将给定路径来创建一个新File实例。 
	 * 
	 * new File(String parent,String child); 根据parent路径名字符串和child路径名创建一个新File实例。
	 * parent是指上级目录的路径，完整的路径为parent+child. 
	 * 
	 * new File(File parent, String child);根据parent抽象路径名和child路径名创建一个新File实例。
	 * parent是指上级目录的路径，完整的路径为parent.getPath()+child.
	 */
	public static void initFile() {
		// file 是一个文件对象
		File file1 = new File("c:/a.txt");
		System.out.println(file1.isFile());
		// file 是一个文件夹对象
		File file2 = new File("e:/ABIDE");
		System.out.println(file2.isDirectory());
	}

	// File中常见的方法
	/**
	 * createNewFile() 在指定位置创建一个空文件，成功就返回true，如果已存在就不创建然后返回false
	 * 
	 * mkdir() 在指定位置创建目录，这只会创建最后一级目录，如果上级目录不存在就抛异常。
	 * 
	 * mkdirs() 在指定位置创建目录，这会创建路径中所有不存在的目录。
	 * 
	 * renameTo(File dest) 重命名文件或文件夹，也可以操作非空的文件夹，文件不同时相当于文件的剪切,剪切时候
	 * 不能操作非空的文件夹。移动/重命名成功则返回true，失败则返回false。
	 */
	public static void createFile() {
		File file = new File("e:\\a.txt");
		try {
			boolean flag = file.createNewFile();
			System.out.println("创建文件结果：" + flag);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * delete() 删除文件或一个空文件夹，如果是文件夹且不为空，则不能删除，成功返回true，失败返回false。
	 * 
	 * deleteOnExit() 在虚拟机终止时，请求删除此抽象路径名表示的文件或目录，保证程序异常时创建的临时文件也可以被删除
	 */
	public static void deleteFile() {
		File file = new File("e:\\a.txt");
		boolean flag = file.delete();
		System.out.println("删除文件结果：" + flag);
	}

	/**
	 * exists() 文件或文件夹是否存在。
	 * 
	 * isFile() 是否是一个文件，如果不存在，则始终为false。
	 * 
	 * isDirectory() 是否是一个目录，如果不存在，则始终为false。
	 * 
	 * isHidden() 是否是一个隐藏的文件或是否是隐藏的目录。
	 * 
	 * isAbsolute() 测试此抽象路径名是否为绝对路径名。
	 */
	public static void checkFile() {
		File file = new File("a.txt");
		System.out.println(file.isAbsolute());
	}

	/**
	 * getName() 获取文件或文件夹的名称，不包含上级路径。
	 * 
	 * getPath() 返回绝对路径，可以是相对路径，但是目录要指定
	 * 
	 * getAbsolutePath() 获取文件的绝对路径，与文件是否存在没关系
	 * 
	 * length() 获取文件的大小（字节数），如果文件不存在则返回0L，如果是文件夹也返回0L。
	 * 
	 * getParent() 返回此抽象路径名父目录的路径名字符串；如果此路径名没有指定父目录，则返回null。
	 * 
	 * lastModified() 获取最后一次被修改的时间。
	 * 
	 * 文件夹相关：
	 * 
	 * staic File[] listRoots() 列出所有的根目录（Window中就是所有系统的盘符）
	 * 
	 * list() 返回目录下的文件或者目录名，包含隐藏文件。对于文件这样操作会返回null。
	 * 
	 * list(FilenameFilter filter) 返回指定当前目录中符合过滤条件的子文件或子目录。对于文件这样操作会返回null。
	 * 
	 * listFiles() 返回目录下的文件或者目录对象（File类实例），包含隐藏文件。对于文件这样操作会返回null。
	 * 
	 * listFiles(FilenameFilter filter) 返回指定当前目录中符合过滤条件的子文件或子目录。对于文件这样操作会返回null。
	 */
	public static void getFile() {
		File file = new File("a.txt");
		System.out.println(file.getPath());
	}

}
