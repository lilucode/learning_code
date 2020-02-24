package cn.com.agree.ab.transfer.csd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@SuppressWarnings("rawtypes")
public class CSDRequest {

	private String contentType;

	private Map<String, String> header = new ConcurrentHashMap<String, String>();
	
	private Map bean = new ConcurrentHashMap();

	private List<String> body = new ArrayList<String>();
	
	public CSDRequest() {
		header.put("Accept-Encoding", "zh-CN,zh;q=0.8");
	}
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public List<String> getBody() {
		return body;
	}

	public void setBody(String str) {
		body.add(str);
	}

	public Map getBean() {
		return bean;
	}

	public void setBean(Map bean) {
		this.bean = bean;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}
	
}
