package cn.com.li1.actuator;

import org.jsoup.Jsoup;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class MonitorMetricsController {
	
	private static final Log logger = LogFactory.getLog(MonitorMetricsController.class);
	
	@Value("${management.server.port}")
	private String port;
	
	@RequestMapping(value = "getMetricsInfo")
	public String getMetricsInfo() {
		
		try {
			Response httpResponse = Jsoup.connect("http://127.0.1.1:" + port + "/actuator/info")
					.ignoreContentType(true).method(Method.GET).execute();
			String body = httpResponse.body();
			return body.substring(1, body.length()-1).replace("\"","")
					.replaceAll(":", " ").replaceAll(",", "\n");
		} catch (IOException e) 
		{
			logger.error("获取监控信息出错!", e);
			return "http://127.0.1.1:" + port + "/actuator/info  请求出现异常，请检查服务端口配置";
		}
	}
}
