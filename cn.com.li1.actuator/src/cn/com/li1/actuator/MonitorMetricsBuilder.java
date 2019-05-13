package cn.com.li1.actuator;

import java.io.IOException;
import java.math.BigDecimal;

import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.web.embedded.jetty.JettyWebServer;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
@RefreshScope
public class MonitorMetricsBuilder   implements InfoContributor{

	@Value("${management.server.port}")
	private String port;
	
	@Autowired
	private ApplicationContext context;
	
	@Override
	public void contribute(Info.Builder builder) 
	{
        builder.withDetail("aase_memoryUtilization",
        		Double.toString((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())*100/
        				(double)Runtime.getRuntime().maxMemory()));
        builder.withDetail("aase_maxMemory", Runtime.getRuntime().maxMemory());
        builder.withDetail("aase_freeMemory", Runtime.getRuntime().freeMemory());
        builder.withDetail("aase_totalMemory", Runtime.getRuntime().totalMemory());
        
        ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) context;
        if (applicationContext instanceof ServletWebServerApplicationContext)
        {
            ServletWebServerApplicationContext webServerApplicationContext = (ServletWebServerApplicationContext) applicationContext;
            WebServer ws = webServerApplicationContext.getWebServer();
            if (ws instanceof JettyWebServer)
            {
            	 QueuedThreadPool executor = (QueuedThreadPool) ((JettyWebServer) ws).getServer().getThreadPool();
                 builder.withDetail("aase_queueSize", executor.getQueueSize());
                 builder.withDetail("aase_threads", executor.getThreads());
                 builder.withDetail("aase_idleThreads", executor.getIdleThreads());
                 builder.withDetail("aase_maxThreads", executor.getMaxThreads());
                 builder.withDetail("aase_minThreads", executor.getMinThreads());
            }
        }
        
        Response httpResponse = null;
		try 
		{
			httpResponse = Jsoup.connect("http://127.0.1.1:" + port + "/actuator/metrics/system.cpu.usage")
					.ignoreContentType(true).method(Method.GET).execute();
			BigDecimal bigdecimal = (BigDecimal) (((JSONArray) JSONObject.parseObject(httpResponse.body())
					.get("measurements")).getJSONObject(0).get("value"));
		    double cpuUsage = bigdecimal.doubleValue()*100;
		    builder.withDetail("aase_cpuUsage",cpuUsage);
		} catch (IOException e) {
			builder.withDetail("aase_cpuUsage","请求http://127.0.1.1:" + port + "/actuator/metrics/system.cpu.usage出现异常，请检查端口配置");
		}
	}
	
}
