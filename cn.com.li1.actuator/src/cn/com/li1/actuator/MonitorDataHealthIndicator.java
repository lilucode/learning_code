package cn.com.li1.actuator;

import java.math.BigDecimal;

import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.web.embedded.jetty.JettyWebServer;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class MonitorDataHealthIndicator extends AbstractHealthIndicator
{
	@Value("${health.CPU}")
	private String cpu;
	
	@Value("${health.responseTime}")
	private String responseTime;
	
	@Value("${health.slowResponseRat}")
	private String slowResponseRat;
	
	@Value("${health.threads}")
	private String maxThreads;
	
	@Value("${health.memory}")
	private String memory;
	
	@Value("${management.server.port}")
	private String port;
	
	@Autowired
	private ApplicationContext context;
	
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception
    {
        int threads = 0;
        double  memoryUtilization = 0.0000000;
        //获取服务线程
    	ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) context;
        if (applicationContext instanceof ServletWebServerApplicationContext)
        {
            ServletWebServerApplicationContext webServerApplicationContext = (ServletWebServerApplicationContext) applicationContext;
            WebServer ws = webServerApplicationContext.getWebServer();
            if (ws instanceof JettyWebServer)
            {
                QueuedThreadPool executor = (QueuedThreadPool) ((JettyWebServer) ws).getServer().getThreadPool();
                threads= executor.getThreads();
                if (!executor.isRunning())
                    builder.down();
            }
        }
        builder.withDetail("aase_threads", threads);
        if(threads < Integer.parseInt(maxThreads)) //总线程数< Threads   我们继续判定其他条件
        {   //获取内存使用率     即[totalMemory()- freeMemory()]/maxMemory()
            memoryUtilization=((double)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())*100/
            		(double)Runtime.getRuntime().maxMemory());
            builder.withDetail("aase_memoryUtilization",memoryUtilization );
            if(memoryUtilization < Double.parseDouble(memory)) //jvm内存占用 <Memory 继续判定其他条件 
            {
                Response httpResponse = Jsoup.connect("http://127.0.1.1:" + port + "/actuator/metrics/system.cpu.usage")
                		.ignoreContentType(true).method(Method.GET).execute();
                BigDecimal bigdecimal = (BigDecimal) (((JSONArray) JSONObject.parseObject(httpResponse.body())
                		.get("measurements")).getJSONObject(0).get("value"));
                double cpuUsage = bigdecimal.doubleValue()*100;
        		if(cpuUsage < Double.parseDouble(cpu)) //判断cpu占用<CPU   继续判定其他条件
        		{
            		builder.withDetail("aase_cpuUsage",cpuUsage);
            		//判断慢响应率
                    builder.withDetail("aase_ResponseTime", responseTime);
                    builder.withDetail("aase_SlowResponseRat", slowResponseRat);
                	builder.up();
        		} else {
        			builder.down();
        		}
            } else {
            	builder.down();
            }
        } else {
        	builder.down();
        }
    }
    
}