package cn.com.li1.watchservice;

import java.io.File;
import java.util.Set;

public interface IWatchServiceListener {
	public void handle(Set<File> files);
}
