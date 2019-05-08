package cn.com.li1.watchservice;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.agree.ab.a5.runtime.config.ApplicationContextProvider;
import cn.com.agree.ab.a5.runtime.utils.CommonUtils;

public class WatchServiceFactory {
	
	private static final Log logger = LogFactory.getLog(WatchServiceFactory.class);

	private static long checkPeriodLong = -1l;
	
	private static volatile AdvancedWatchService advancedWatchService = null;
	
	public static AdvancedWatchService getAdvancedWatchService() {
		if (advancedWatchService == null) {
			synchronized (AdvancedWatchService.class) {
				if (advancedWatchService == null) {
					try {
						advancedWatchService = new AdvancedWatchService(
								CommonUtils.getApplicationDirectory().toPath());
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		return advancedWatchService;
	}
	
	public static String toRelative(Path absolutePath) {
		return getAdvancedWatchService().toRelative(absolutePath);
	}
	
	public static long getCheckPeriod() {
		if (checkPeriodLong < 0l) {
			long checkPeriod = ApplicationContextProvider.getApplicationContext()
					.getEnvironment().getProperty("agree.config.monitor.checkPeriod", Long.class, 10000l); 
			if (checkPeriod < 100l) {
				checkPeriod = 1000l;
			}
			checkPeriodLong = checkPeriod;
		}
		return checkPeriodLong;
	}
	
}
