package cn.com.agree.ab.transfer.afa.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Constant {

	public static String ProjectName = "projectName";
	/**
	 * 转换文件路径
	 */
	public static String TransferPath = "";
	/**
	 * request().xm和request().xm路径
	 */
	public static List<String> XmList = new ArrayList<String>();
	/**
	 * inArgs和outArgs的参数需要转换为ade的值
	 */
	public static Set<String> ArgAdeList = new HashSet<String>();
	/**
	 * 需要转换参数引用的lfc
	 */
	public static List<String> LfcList = new ArrayList<String>();
	public static Set<String> RepeatLfcList = new HashSet<String>();
}
