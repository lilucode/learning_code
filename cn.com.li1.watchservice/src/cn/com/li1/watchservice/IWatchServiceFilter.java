package cn.com.li1.watchservice;

import java.io.File;

public interface IWatchServiceFilter {
	boolean accept(File file, boolean isFile, boolean isDir);
}
